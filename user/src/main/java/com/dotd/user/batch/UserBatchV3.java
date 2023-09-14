package com.dotd.user.batch;


import com.dotd.user.batch.reader.UserItemReader;
import com.dotd.user.entity.RewardLog;
import com.dotd.user.entity.UsedMoneyLog;
import com.dotd.user.entity.User;
import com.dotd.user.entity.UserCoupon;
import com.dotd.user.repository.RewardLogRepository;
import com.dotd.user.repository.UsedMoneyLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.BeanMapping;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.batch.api.chunk.ItemWriter;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
public class UserBatchV3 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final UsedMoneyLogRepository usedMoneyLogRepository;




    // 병렬 처리
    @Bean
    public TaskExecutor taskExecutorV3() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4); // 코어 스레드 개수 설정
        taskExecutor.setMaxPoolSize(8); // 최대 스레드 개수 설정
        taskExecutor.setQueueCapacity(10); // 큐 크기 설정
        return taskExecutor;
    }
    
    /**
    Reader 부분
     */

    // 데이터 읽기
    @Bean
    public JpaPagingItemReader<User> userItemReaderV3() {
        JpaPagingItemReader<User> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("select u from User u");
        reader.setPageSize(100);
        return reader;
    }

    /**
     Processor 부분
     */

    // 사용 금액을 모두 더함 Processor
    @Bean
    public ItemProcessor<User, User> userItemProcessorV3() {
        return user -> {
            List<UsedMoneyLog> usedMoneyLogs = usedMoneyLogRepository.findByUserId(user.getId());

            int totalUsedMoney = usedMoneyLogs.stream()
                    .mapToInt(UsedMoneyLog::getUsedMoney)
                    .sum();

            user.setUsedMoney(totalUsedMoney);
            return user;
        };
    }

    // 유저 등급 업데이트 Processor
    @Bean
    public ItemProcessor<User, User> userTierUpdateProcessorV3() {
        return user -> {
            if(user.getUsedMoney() <= 1000) {
                user.setTier("Bronze");
            }
            else if(user.getUsedMoney() <= 5000) {
                user.setTier("Silver");
            }
            else if(user.getUsedMoney() <= 10000) {
                user.setTier("Gold");
            }
            return user;
        };
    }

    // 유저 등급에 따른 쿠폰 발행 Processor
    public ItemProcessor<User, UserCoupon> userCouponItemProcessorV3() {
        return user -> {
            UserCoupon userCoupon = new UserCoupon();
            userCoupon.setUserId(user.getId());

            String tier = user.getTier();
            if("Bronze".equals(tier)) {
                userCoupon.setCouponId(1);
            }
            else if("Silver".equals(tier)) {
                userCoupon.setCouponId(2);
            }
            else if ("Gold".equals(tier)) {
                userCoupon.setCouponId(3);
            }

            return userCoupon;
        };
    }

    // 유저 등급에 따른 적립금 지급 내역 저장
    public ItemProcessor<User, RewardLog> registRewardLogV3() {
        return user -> {

            String tier = user.getTier();
            Integer reward = user.getReward();
            int provideReward = 0;
            if("Bronze".equals(tier)) {
                provideReward = 1000;
            }
            else if("Silver".equals(tier)) {
                provideReward = 2000;
            }
            else if ("Gold".equals(tier)) {
                provideReward = 3000;
            }
            return RewardLog.builder()
                    .userId(user.getId())
                    .description("등급 별 적립금 지급")
                    .status("적립")
                    .reward(provideReward)
                    .build();
        };
    }

    // 유저 등급에 따른 적립금 지금
    public ItemProcessor<User, User> userProvideRewardV3() {
        return user -> {

            String tier = user.getTier();
            Integer reward = user.getReward();
            int provideReward = 0;
            if("Bronze".equals(tier)) {
                provideReward = 1000;
            }
            else if("Silver".equals(tier)) {
                provideReward = 2000;
            }
            else if ("Gold".equals(tier)) {
                provideReward = 3000;
            }
            user.setReward(user.getReward() + provideReward);

            return user;
        };

    }



    
    /**
    Writer 부분
    **/

    // 사용자 저장
    @Bean
    public JpaItemWriter<User> userItemWriterV3() {
        JpaItemWriter<User> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }


    // userCoupon 저장
    @Bean
    public JpaItemWriter<UserCoupon> userCouponJpaItemWriterV3() {
        JpaItemWriter<UserCoupon> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    // RewardLog 저장
    @Bean
    public JpaItemWriter<RewardLog> rewardLogJpaItemWriterV3() {
        JpaItemWriter<RewardLog> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }



    /**
    Step 부분
     */

    // 사용 금액 업데이트 Step
    @Bean
    public Step updateUsedMoneyStepV3() {
        return stepBuilderFactory.get("updateUsedMoneyStepV3")
                .<User, User>chunk(100)
                .reader(userItemReaderV3())
                .processor(userItemProcessorV3())
                .writer(userItemWriterV3())
                .taskExecutor(taskExecutorV3())
                .build();
    }


    // 사용 금액에 따라 유저의 티어 업데이트 step
    @Bean
    public Step userTierUpdateStepV3() {
        return stepBuilderFactory.get("userTierUpdateStepV3")
                .<User, User>chunk(100)
                .reader(userItemReaderV3())
                .processor(userTierUpdateProcessorV3())
                .writer(userItemWriterV3())
                .taskExecutor(taskExecutorV3())
                .build();
    }

    // 티어에 따라 쿠폰 지급하는 step
    @Bean
    public Step provideCouponStepV3() {
        return stepBuilderFactory.get("provideCouponStepV3")
                .<User, UserCoupon>chunk(100)
                .reader(userItemReaderV3())
                .processor(userCouponItemProcessorV3())
                .writer(userCouponJpaItemWriterV3())
                .taskExecutor(taskExecutorV3())
                .build();
    }

    // 티어에 따라 적립금 내역을 저장하는 step
    @Bean
    public Step registRewardLogStepV3() {
        return stepBuilderFactory.get("provideRewardLogStepV3")
                .<User, RewardLog>chunk(100)
                .reader(userItemReaderV3())
                .processor(registRewardLogV3())
                .writer(rewardLogJpaItemWriterV3())
                .taskExecutor(taskExecutorV3())
                .build();
    }


    // 티어에 따라 적립금 금액을 저장하는 step
    @Bean
    public Step provideUserRewardSteV3() {
        return stepBuilderFactory.get("provideUserRewardV3")
                .<User, User>chunk(100)
                .reader(userItemReaderV3())
                .processor(userProvideRewardV3())
                .writer(userItemWriterV3())
                .taskExecutor(taskExecutorV3())
                .build();
    }



    /**
    Flow 부분
     */

    // Flow1 : 사용 금액, 티어 갱신 플로우
    // updateAndTierUpdateFlowV3
    @Bean
    public Flow flow1() {
        return new FlowBuilder<SimpleFlow>("flow1")
                .start(updateUsedMoneyStepV3())
                .next(userTierUpdateStepV3())
                .build();
    }

    // Flow2 : 티어에 따른 쿠폰 지급
    // provideCouponFlowV3
    @Bean
    public Flow flow2() {
        return new FlowBuilder<SimpleFlow>("flow2")
                .start(provideCouponStepV3())
                .build();
    }

    // Flow3 : 티어에 따라 적립금 지급
    // provideCouponFlowV3
    @Bean
    public Flow flow3() {
        return new FlowBuilder<SimpleFlow>("flow3")
                .start(registRewardLogStepV3())
                .next(provideUserRewardSteV3())
                .build();
    }



    /**
    Job 부분
     */
    // 티어에 따라 쿠폰 생성
    @Bean
    public Job updateUserUsedMoneyJobV3() {
        return jobBuilderFactory.get("updateUserUsedMoneyJobV3")
                .start(flow1())
                .split(new SimpleAsyncTaskExecutor())
                .add(flow2(), flow3())
                .end()
                .build();
    }


}
