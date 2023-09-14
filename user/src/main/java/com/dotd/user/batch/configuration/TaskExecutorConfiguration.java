package com.dotd.user.batch.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TaskExecutorConfiguration {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4); // 코어 스레드 개수 설정
        taskExecutor.setMaxPoolSize(8); // 최대 스레드 개수 설정
        taskExecutor.setQueueCapacity(10); // 큐 크기 설정
        return taskExecutor;
    }
}
