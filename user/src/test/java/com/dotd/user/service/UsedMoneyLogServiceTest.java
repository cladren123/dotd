package com.dotd.user.service;

import com.dotd.user.dto.usedmoneylog.UsedMoneyLogRegisterRequestDto;
import com.dotd.user.dto.usedmoneylog.UsedMoneyLogResponseDto;
import com.dotd.user.entity.UsedMoneyLog;
import com.dotd.user.mapper.UsedMoneyLogMapper;
import com.dotd.user.repository.UsedMoneyLogRepository;
import com.dotd.user.service.usedmoneylog.UsedMoneyLogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UsedMoneyLogServiceTest {

    @Autowired
    private UsedMoneyLogService usedMoneyLogService;

    @Autowired
    private UsedMoneyLogRepository usedMoneyLogRepository;

    @Autowired
    private UsedMoneyLogMapper usedMoneyLogMapper;


    @Test
    @DisplayName("UsedMoneyLog 등록 테스트")
    public void register() {
        // given
        String userId = "dummyId";
        String description = "물건 구매";
        Integer usedMoney = 1000;

        UsedMoneyLogRegisterRequestDto dto = UsedMoneyLogRegisterRequestDto.builder()
                .userId(userId)
                .description(description)
                .usedMoney(usedMoney)
                .build();

        // when
        UsedMoneyLogResponseDto result = usedMoneyLogService.register(dto);

        // then
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getDescription()).isEqualTo(description);
        assertThat(result.getUsedMoney()).isEqualTo(usedMoney);
    }

    @Test
    @DisplayName("UsedMoneyLog 등록 테스트")
    public void findByUserId() {
        // given
        String userId = "dummyUserId";
        List<UsedMoneyLog> usedMoneyLogList = Arrays.asList(
                new UsedMoneyLog(1L, userId, "Description", 1000, LocalDateTime.now()),
                new UsedMoneyLog(2L, userId, "Description", 1000, LocalDateTime.now())
        );
        usedMoneyLogRepository.saveAll(usedMoneyLogList);

        // when
        List<UsedMoneyLogResponseDto> result = usedMoneyLogService.findByUserId(userId);

        // then
        assertThat(result.size()).isEqualTo(usedMoneyLogList.size());

        for(int i = 0; i < result.size(); i++) {
            UsedMoneyLogResponseDto expected = usedMoneyLogMapper.usedMoneyLogToUsedMoneyLogResponseDto(usedMoneyLogList.get(i));
            UsedMoneyLogResponseDto actual = result.get(i);

            assertThat(actual.getId()).isEqualTo(expected.getId());
            assertThat(actual.getUserId()).isEqualTo(expected.getUserId());
            assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
            assertThat(actual.getUsedMoney()).isEqualTo(expected.getUsedMoney());
        }
    }





}
