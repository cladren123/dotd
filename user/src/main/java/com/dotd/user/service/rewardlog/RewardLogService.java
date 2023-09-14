package com.dotd.user.service.rewardlog;

import com.dotd.user.dto.rewardlog.RewardLogRegisterRequestDto;
import com.dotd.user.dto.rewardlog.RewardLogResponseDto;

import java.util.List;

public interface RewardLogService {

    // Rewardlog 등록
    RewardLogResponseDto register(RewardLogRegisterRequestDto dto);

    // RewardLog 조회
    RewardLogResponseDto findOne(Long id);

    // 개인 RewardLog들 조회
    List<RewardLogResponseDto> findAllByUserId(String userId);



}
