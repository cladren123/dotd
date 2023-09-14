package com.dotd.user.batch;


import com.dotd.user.entity.User;
import com.dotd.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Slf4j
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public JpaPagingItemReader<User> jpaPagingItemReader() {
        JpaPagingItemReader<User> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);

        // JPQL 쿼리 -> MySQL이 아닌 Entitiy 이름이어야 한다.
        // User 의 모든 인스턴스를 선택
        // setQueryString은 해당 Reader 사용할 JPQL 쿼리를 설정
        // 데이터를 페이징 방식으로 읽어옴
        // User의 엔티티의 모든 인스턴스를 페이지 단위로 읽어옴
        reader.setQueryString("select u from User u");


        reader.setPageSize(100);
        return reader;
    }


    @Bean
    public ItemProcessor<User, User> userProcessor() {
        return user -> {

            // 유저 등급을 업데이트 하는 로직

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

    @Bean
    public JpaItemWriter<User> jpaItemWriter() {
        JpaItemWriter<User> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }


    @Bean
    public Step userStep() {
        return stepBuilderFactory.get("userStep")
                .<User, User>chunk(100)
                .reader(jpaPagingItemReader())
                .processor(userProcessor())
                .writer(jpaItemWriter())
                .build();
    }

    @Bean
    public Job userJob(Step userStep) {
        return jobBuilderFactory.get("userJob")
                .start(userStep)
                .build();
    }












}
