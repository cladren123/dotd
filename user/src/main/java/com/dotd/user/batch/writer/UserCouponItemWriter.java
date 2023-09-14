package com.dotd.user.batch.writer;


import com.dotd.user.entity.UserCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

/*
userCoupon을 저장하는 writer
 */

@Component
@RequiredArgsConstructor
public class UserCouponItemWriter extends JpaItemWriter<UserCoupon> {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        setEntityManagerFactory(entityManagerFactory);
        super.afterPropertiesSet();
    }
}
