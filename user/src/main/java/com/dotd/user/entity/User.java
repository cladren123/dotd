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

import org.hibernate.annotations.Index;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {



    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();


    @Column(name = "login_id")
    @Index(name = "idx_login_id")
    private String loginId;

    private String password;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    private String nickname;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    @Builder.Default
    private Integer reward = 0;

    @Column(name = "used_money")
    @Builder.Default
    private Integer usedMoney = 0;

    @Builder.Default
    private String tier = "Bronze";

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();



    // @PrePersist 는 엔티티가 DB에 처음 저장되기 전에 호출되는 콜백 메소드
    // 초기값 생성
    // 엔티티가 DB에 저장되기 전 필요한 작업을 수행할 수 있다.
    // id에 UUID 부여
    // 회원 가입 시간 부여
//    @PrePersist
//    public void initializer() {
//        this.id = UUID.randomUUID().toString();
//        this.reward = 0;
//        this.usedMoney = 0;
//        this.tier = "Bronze";
//        this.createdAt = LocalDateTime.now();
//    }


}
