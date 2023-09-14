package com.dotd.user.repository;

import com.dotd.user.entity.Coupon;
import com.dotd.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
}
