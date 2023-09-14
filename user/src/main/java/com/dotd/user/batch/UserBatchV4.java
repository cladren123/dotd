package com.dotd.user.batch;


import com.dotd.user.batch.processor.*;
import com.dotd.user.batch.reader.UserItemReader;
import com.dotd.user.batch.writer.RewardLogItemWriter;
import com.dotd.user.batch.writer.UserCouponItemWriter;
import com.dotd.user.batch.writer.UserItemWriter;
import com.dotd.user.entity.RewardLog;
import com.dotd.user.entity.User;
import com.dotd.user.entity.UserCoupon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.persistence.EntityManagerFactory;


/*

BatchV3 시나리오 :

flow1 :
step1 : 모든 유저에 대해 유저 개인마다 사용 금액 내역을 모두 다해 총 사용 금액 내용을 저장
step2 : 총 사용 금액 내용을 토대로 Tier를 정한다

flow2 :
step1 : Tier별로 쿠폰을 제공한다.

flow3 :
step1 : Tier별로 적립금 내역을 저장한다.
step2 : 실제 user에게 적립금을 지급한다.

flow1을 실행 후
flow2, flow3 를 병렬 실행한다.

 */


@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Slf4j
public class UserBatchV4 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final TaskExecutor taskExecutor;




    // reader
    private final UserItemReader userItemReader;

    // processor

    // 유저 사용 금액 계산, 티어 업데이트
    private final UserUpdateUsedMoneyProcessor userUpdateUsedMoneyProcessor;
    private final UserUpdateTierProcessor userUpdateTierProcessor;

    // 유저 쿠폰 지급
    private final UserProvideCouponProcessor userProvideCouponProcessor;

    // 유저 적립금 지급
    private final UserRegistRewardLogProcessor userRegistRewardLogProcessor;
    private final UserProvideRewardProcessor userProvideRewardProcessor;



    // writer
    private final UserItemWriter userItemWriter;
    private final UserCouponItemWriter userCouponItemWriter;
    private final RewardLogItemWriter rewardLogItemWriter;



    // 적립금 내역을 계산해서 user의 적립금을 업데이트 하는 Step
    @Bean
    public Step userUpdateUsedMoneyStep() {
        return stepBuilderFactory.get("userUpdateUsedMoneyStep")
                .<User, User>chunk(100)
                .reader(userItemReader)
                .processor(userUpdateUsedMoneyProcessor)
                .writer(userItemWriter)
                //.taskExecutor(taskExecutor)
                .build();
    }


    // 적립금 내역에 따라 등급을 업데이트 하는 Step
    @Bean
    public Step userUpdateTierStep() {
        return stepBuilderFactory.get("userUpdateTierStep")
                .<User, User>chunk(100)
                .reader(userItemReader)
                .processor(userUpdateTierProcessor)
                .writer(userItemWriter)
                .taskExecutor(taskExecutor)
                .build();
    }

    // 유저 등급에 따라 쿠폰을 지급하는 Step
    @Bean
    public Step userProvideCouponStep() {
        return stepBuilderFactory.get("userProvideCouponStep")
                .<User, UserCoupon>chunk(100)
                .reader(userItemReader)
                .processor(userProvideCouponProcessor)
                .writer(userCouponItemWriter)
                //.taskExecutor(taskExecutor)
                .build();
    }


    // 유저 적립금 내역 저장
    public Step userRegistRewardLogStep() {
        return stepBuilderFactory.get("userRegistRewardLogStep")
                .<User, RewardLog>chunk(100)
                .reader(userItemReader)
                .processor(userRegistRewardLogProcessor)
                .writer(rewardLogItemWriter)
                //.taskExecutor(taskExecutor)
                .build();
    }

    // 유저 적립금 지급
    public Step userProvideRewardStep() {
        return stepBuilderFactory.get("userProvideRewardStep")
                .<User, User>chunk(100)
                .reader(userItemReader)
                .processor(userProvideRewardProcessor)
                .writer(userItemWriter)
                .taskExecutor(taskExecutor)
                .build();
    }



    // Flow 부분

    // 유저의 사용 금액 내역을 계산하여 총 사용 금액을 저장하고 유저 티어를 새로 세팅하는 플로우
    // step1 : 유저의 사용 금액 내역을 계산하여 총 사용 금액을 저장
    // step2 : 사용 금액에 따른 티어 설정
    @Bean
    public Flow userUpdateFlow() {
        return new FlowBuilder<SimpleFlow>("userUpdateFlow")
                .start(userUpdateUsedMoneyStep())
                .next(userUpdateTierStep())
                .build();
    }



    // 유저에게 적립금을 지급하는 플로우
    // step1 : 적립금 내역에 저장
    // step2 : 유저의 적립금 필드 업데이트
    @Bean
    public Flow userProvideRewardFlow() {
        return new FlowBuilder<SimpleFlow>("userProvideRewardFlow")
                .start(userRegistRewardLogStep())
                .next(userProvideRewardStep())
                .build();
    }

    // 유저에게 쿠폰을 지급하는 플로우
    @Bean
    public Flow userProvideCouponFlow() {
        return new FlowBuilder<SimpleFlow>("userProvideCouponFlow")
                .start(userProvideCouponStep())
                .build();
    }

    
    // Job 부분

    // flow를 실행할 때 .end가 추가된다.
    @Bean
    public Job jobV4() {
        return jobBuilderFactory.get("jobV4")
                .start(userUpdateFlow())
                .next(userProvideRewardFlow())
                .next(userProvideCouponFlow())
                .end()
                .build();
    }


    @Bean
    public Job jobV5() {
        return jobBuilderFactory.get("jobV5")
                .start(userUpdateFlow())
                .split(taskExecutor)
                .add(userProvideRewardFlow(), userProvideCouponFlow())
                .end().build();
    }



    /*
        @Bean
    public Job jobV4(Step userUpdateUsedMoneyStep) {
        return jobBuilderFactory.get("jobV4")
                .start(userProvideCouponStep())
                .build();
    }

        @Bean
    public Job jobV4(Step userUpdateUsedMoneyStep) {
        return jobBuilderFactory.get("jobV4")
                .start(userUpdateFlow())
                .end()
                .build();
    }
     */










}
