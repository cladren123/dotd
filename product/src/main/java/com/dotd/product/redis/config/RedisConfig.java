package com.dotd.product.redis.config;


import com.dotd.product.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    // product 객체를 Redis에 연결하기 위해 RedisTemplate
    @Bean
    public RedisTemplate<String, Product> productRedisTemplate() {
        RedisTemplate<String, Product> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // JSON 직렬화 설정
        Jackson2JsonRedisSerializer<Product> serializer = new Jackson2JsonRedisSerializer<>(Product.class);


        ObjectMapper mapper = new ObjectMapper();
        // Java 객체를 JSON 직렬화할 때 클래스 타입 정보를 포함시키도록 지시 -> 역직렬화시 올바른 타입 객체 생성
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // Java 8 날짜/시간 타입 지원 모듈 추가
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        serializer.setObjectMapper(mapper);

        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        return redisTemplate;
    }



}
