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

import javax.persistence.EntityManagerFactory;


@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Slf4j
public class UserBatchV1 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    // 데이터 읽기
    @Bean
    public JpaPagingItemReader<User> jpaPagingItemReaderV1() {
        JpaPagingItemReader<User> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);

        reader.setQueryString("select u from User u");

        reader.setPageSize(100);
        return reader;
    }


    // 유저 등급을 업데이트 하는 로직
    @Bean
    public ItemProcessor<User, User> userProcessorV1() {
        return user -> {
            if(user.getUsedMoney() <= 100) {
                user.setTier("Bronze");
            }
            else if(user.getUsedMoney() <= 500) {
                user.setTier("Silver");
            }
            else if(user.getUsedMoney() <= 1000) {
                user.setTier("Gold");
            }

            // user 데이터를 가공하는 로직
            return user;
        };
    }

    // 변경 사항을 DB에 저장하는 메소드
    @Bean
    public JpaItemWriter<User> jpaItemWriterV1() {
        JpaItemWriter<User> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }


    @Bean
    public Step userStepV1() {
        return stepBuilderFactory.get("userStepV1")
                .<User, User>chunk(100)
                .reader(jpaPagingItemReaderV1())
                .processor(userProcessorV1())
                .writer(jpaItemWriterV1())
                .build();
    }

    @Bean
    public Job userJobV1(Step userStepV1) {
        return jobBuilderFactory.get("userJobV1")
                .start(userStepV1)
                .build();
    }
}
