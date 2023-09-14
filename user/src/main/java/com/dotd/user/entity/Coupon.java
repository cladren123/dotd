package com.dotd.user.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


/*

쿠폰 엔티티

 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "coupons")
public class Coupon {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 쿠폰 아이디

    private String name; // 쿠폰 이름
    private String description; // 쿠폰 설명
    private Integer discount; // 쿠폰 할인율


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime duration; // 쿠폰 생성일

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;


    // 초기값 세팅
    @PrePersist
    public void initializer() {
        this.createdAt = LocalDateTime.now();
    }



}
