package com.dotd.user.dto.usedmoneylog;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsedMoneyLogResponseDto {
    private Long id;
    private String userId;
    private String description; // 설명
    private Integer usedMoney; // 사용금액
    private LocalDateTime createdAt;
}
