package com.dotd.user.dto.usedmoneylog;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsedMoneyLogRegisterRequestDto {
    private String userId;
    private String description; // 설명
    private Integer usedMoney; // 사용 금액

}
