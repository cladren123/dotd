package com.dotd.user.batch;


import com.dotd.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.persistence.EntityManagerFactory;


/*

1. 성능 개선
병렬 처리

 */

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Slf4j
public class UserBatchV2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;


    // 병렬 처리
    @Bean
    public TaskExecutor taskExecutorV2() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4); // 코어 스레드 개수 설정
        taskExecutor.setMaxPoolSize(8); // 최대 스레드 개수 설정
        taskExecutor.setQueueCapacity(10); // 큐 크기 설정
        return taskExecutor;
    }

    // 데이터 읽기
    @Bean
    public JpaPagingItemReader<User> jpaPagingItemReaderV2() {
        JpaPagingItemReader<User> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("select u from User u");
        reader.setPageSize(100);
        return reader;
    }


    // 유저 등급을 업데이트 하는 로직
    @Bean
    public ItemProcessor<User, User> userProcessorV2() {
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

            // user 데이터를 가공하는 로직
            return user;
        };
    }

    // 변경 사항을 DB에 저장하는 메소드
    @Bean
    public JpaItemWriter<User> jpaItemWriterV2() {
        JpaItemWriter<User> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }


    @Bean
    public Step userStepV2() {
        return stepBuilderFactory.get("userStepV2")
                .<User, User>chunk(100)
                .reader(jpaPagingItemReaderV2())
                .processor(userProcessorV2())
                .writer(jpaItemWriterV2())
                .taskExecutor(taskExecutorV2())
                .build();
    }

    //.taskExecutor(taskExecutor()) 설정을 사용하여 스텝 내의 아이템(chunk) 단위로 병렬 처리를 수행할 수 있습니다.


    @Bean
    public Job userJobV2(Step userStepV2) {
        return jobBuilderFactory.get("userJobV2")
                .start(userStepV2)
                .build();
    }


}
