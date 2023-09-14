package com.dotd.user.service.usedmoneylog;


import com.dotd.user.dto.usedmoneylog.UsedMoneyLogRegisterRequestDto;
import com.dotd.user.dto.usedmoneylog.UsedMoneyLogResponseDto;
import com.dotd.user.entity.UsedMoneyLog;
import com.dotd.user.mapper.UsedMoneyLogMapper;
import com.dotd.user.repository.UsedMoneyLogRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UsedMoneyLogServiceImpl implements UsedMoneyLogService{

    private final UsedMoneyLogRepository usedMoneyLogRepository;
    private final UsedMoneyLogMapper usedMoneyLogMapper;

    // 회원 금액 사용 내역 등록
    @Override
    public UsedMoneyLogResponseDto register(UsedMoneyLogRegisterRequestDto dto) {
        UsedMoneyLog usedMoneyLog = usedMoneyLogMapper.usedMoneyLogRegisterRequestDtoToUsedMoneyLog(dto);
        UsedMoneyLog save = usedMoneyLogRepository.save(usedMoneyLog);
        UsedMoneyLogResponseDto result = usedMoneyLogMapper.usedMoneyLogToUsedMoneyLogResponseDto(save);
        return result;
    }

    // 회원별 사용내용 금액 조회
    @Override
    public List<UsedMoneyLogResponseDto> findByUserId(String userId) {
        List<UsedMoneyLog> byUserId = usedMoneyLogRepository.findByUserId(userId);
        List<UsedMoneyLogResponseDto> result = new ArrayList<>();
        for(UsedMoneyLog data : byUserId) {
            UsedMoneyLogResponseDto usedMoneyLogResponseDto = usedMoneyLogMapper.usedMoneyLogToUsedMoneyLogResponseDto(data);
            result.add(usedMoneyLogResponseDto);
        }
        return result;
    }
}
