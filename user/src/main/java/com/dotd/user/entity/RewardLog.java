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

회원이 얻은 적립금의 로그를 나타내는 엔티티

 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reward_logs")
public class RewardLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    private String description; // 설명
    private String status; // 상태 : 적립 or 사용
    private Integer reward; // 적립금


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
