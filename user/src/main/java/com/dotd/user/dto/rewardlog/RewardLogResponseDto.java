package com.dotd.user.dto.rewardlog;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardLogResponseDto {

    private Long id;
    private String userId;
    private String description; // 설명
    private String status; // 상태 : 적립 or 사용
    private Integer reward; // 적립금


    private LocalDateTime createdAt;

}
