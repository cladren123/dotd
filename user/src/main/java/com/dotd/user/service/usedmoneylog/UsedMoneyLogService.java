package com.dotd.user.service.usedmoneylog;

import com.dotd.user.dto.rewardlog.RewardLogRegisterRequestDto;
import com.dotd.user.dto.rewardlog.RewardLogResponseDto;
import com.dotd.user.dto.usedmoneylog.UsedMoneyLogRegisterRequestDto;
import com.dotd.user.dto.usedmoneylog.UsedMoneyLogResponseDto;
import com.dotd.user.entity.UsedMoneyLog;

import java.util.List;

public interface UsedMoneyLogService {
    // usedMoneyLog 등록
    UsedMoneyLogResponseDto register(UsedMoneyLogRegisterRequestDto dto);

    // 회원별 usedMoneyLog 조회
    List<UsedMoneyLogResponseDto> findByUserId(String userId);
}
