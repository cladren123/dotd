package com.dotd.user.service.rewardlog;

import com.dotd.user.dto.rewardlog.RewardLogRegisterRequestDto;
import com.dotd.user.dto.rewardlog.RewardLogResponseDto;
import com.dotd.user.entity.RewardLog;
import com.dotd.user.mapper.RewardLogMapper;
import com.dotd.user.repository.RewardLogRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class RewardLogServiceImpl implements RewardLogService{

    private final RewardLogRepository rewardLogRepository;

    private final RewardLogMapper rewardLogMapper;

    // 등록
    @Override
    public RewardLogResponseDto register(RewardLogRegisterRequestDto dto) {
        RewardLog rewardLog = rewardLogMapper.rewardLogRegisterDtoToRewardLog(dto);
        RewardLog save = rewardLogRepository.save(rewardLog);
        RewardLogResponseDto result = rewardLogMapper.rewardLogToRewardLogResponseDto(save);
        return result;
    }


    // 하나 조회
    @Override
    public RewardLogResponseDto findOne(Long id) {
        RewardLog rewardLog = rewardLogRepository.findById(id).get();
        RewardLogResponseDto dto = rewardLogMapper.rewardLogToRewardLogResponseDto(rewardLog);
        return dto;
    }


    // 전체 조회
    @Override
    public List<RewardLogResponseDto> findAllByUserId(String userId) {
        List<RewardLog> byUserId = rewardLogRepository.findByUserId(userId);
        List<RewardLogResponseDto> dtoList = new ArrayList<>();
        for(RewardLog rewardLog : byUserId) {
            RewardLogResponseDto rewardLogResponseDto = rewardLogMapper.rewardLogToRewardLogResponseDto(rewardLog);
            dtoList.add(rewardLogResponseDto);
        }
        return dtoList;
    }
}
