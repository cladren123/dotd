package com.dotd.user.repository;

import com.dotd.user.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Integer> {

    // 회원이 가진 모든 쿠포 조회
    List<UserCoupon> findAllByUserId(String userId);

}
