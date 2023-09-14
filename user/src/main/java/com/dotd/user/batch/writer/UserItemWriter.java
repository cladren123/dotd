package com.dotd.user.batch.writer;

import com.dotd.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

// 유저 객체 저장

@Component
@RequiredArgsConstructor
public class UserItemWriter extends JpaItemWriter<User> {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        setEntityManagerFactory(entityManagerFactory);
        super.afterPropertiesSet();
    }

}
