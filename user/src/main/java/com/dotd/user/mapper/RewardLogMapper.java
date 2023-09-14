package com.dotd.user.mapper;


import com.dotd.user.dto.rewardlog.RewardLogRegisterRequestDto;
import com.dotd.user.dto.rewardlog.RewardLogResponseDto;
import com.dotd.user.entity.RewardLog;
import org.springframework.stereotype.Component;

@Component
public class RewardLogMapper {

    public RewardLogResponseDto rewardLogToRewardLogResponseDto(RewardLog rewardLog) {

        return RewardLogResponseDto.builder()
                .id(rewardLog.getId())
                .userId(rewardLog.getUserId())
                .description(rewardLog.getDescription())
                .status(rewardLog.getStatus())
                .reward(rewardLog.getReward())
                .createdAt(rewardLog.getCreatedAt())
                .build();

    }

    public RewardLog rewardLogRegisterDtoToRewardLog(RewardLogRegisterRequestDto dto) {
        return RewardLog.builder()
                .userId(dto.getUserId())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .reward(dto.getReward())
                .build();
    }



}
