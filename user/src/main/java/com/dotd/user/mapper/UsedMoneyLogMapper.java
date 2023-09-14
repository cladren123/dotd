package com.dotd.user.mapper;

import com.dotd.user.dto.usedmoneylog.UsedMoneyLogRegisterRequestDto;
import com.dotd.user.dto.usedmoneylog.UsedMoneyLogResponseDto;
import com.dotd.user.entity.UsedMoneyLog;
import org.springframework.stereotype.Component;

@Component
public class UsedMoneyLogMapper {

    public UsedMoneyLogResponseDto usedMoneyLogToUsedMoneyLogResponseDto(UsedMoneyLog usedMoneyLog) {
        return UsedMoneyLogResponseDto.builder()
                .id(usedMoneyLog.getId())
                .userId(usedMoneyLog.getUserId())
                .description(usedMoneyLog.getDescription())
                .usedMoney(usedMoneyLog.getUsedMoney())
                .createdAt(usedMoneyLog.getCreatedAt())
                .build();
    }

    public UsedMoneyLog usedMoneyLogRegisterRequestDtoToUsedMoneyLog(UsedMoneyLogRegisterRequestDto dto) {
        return UsedMoneyLog.builder()
                .userId(dto.getUserId())
                .description(dto.getDescription())
                .usedMoney(dto.getUsedMoney())
                .build();
    }

}
