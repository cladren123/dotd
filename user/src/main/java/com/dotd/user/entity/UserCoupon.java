package com.dotd.user.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/*

회원이 가지고 있는 쿠폰을 나타내는 엔티티

 */


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_coupons")
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // user - coupon 연관관계 ID


    @Column(name = "user_id")
    private String userId;


    @Column(name = "coupond_id")
    private Integer couponId;


    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt; // 지급일


    // 초기값 세팅
    @PrePersist
    public void initializer() {
        this.createdAt = LocalDateTime.now();
    }


}
