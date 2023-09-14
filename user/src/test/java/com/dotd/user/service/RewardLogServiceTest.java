package com.dotd.user.service;


import com.dotd.user.dto.rewardlog.RewardLogRegisterRequestDto;
import com.dotd.user.dto.rewardlog.RewardLogResponseDto;
import com.dotd.user.entity.RewardLog;
import com.dotd.user.mapper.RewardLogMapper;
import com.dotd.user.repository.RewardLogRepository;
import com.dotd.user.service.rewardlog.RewardLogService;
import org.assertj.core.api.Assertions;
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
public class RewardLogServiceTest {

    @Autowired
    private RewardLogService rewardLogService;
    @Autowired
    private RewardLogRepository rewardLogRepository;
    @Autowired
    private RewardLogMapper rewardLogMapper;

    @Test
    @DisplayName("RewardLog 등록 테스트")
    public void register() {
        // given
        RewardLogRegisterRequestDto dto = RewardLogRegisterRequestDto.builder()
                .userId("dummy")
                .description("물건 구매")
                .status("사용")
                .reward(100)
                .build();

        // when
        RewardLogResponseDto result = rewardLogService.register(dto);

        // then
        assertThat(result.getUserId()).isEqualTo(dto.getUserId());
        assertThat(result.getDescription()).isEqualTo(dto.getDescription());
        assertThat(result.getStatus()).isEqualTo(dto.getStatus());
        assertThat(result.getReward()).isEqualTo(dto.getReward());
    }

    @Test
    @DisplayName("RewardLog 하나 조회 테스트")
    public void findOne() {
        // given
        RewardLogRegisterRequestDto dto = RewardLogRegisterRequestDto.builder()
                .userId("dummy")
                .description("물건 구매")
                .status("사용")
                .reward(100)
                .build();
        RewardLogResponseDto data = rewardLogService.register(dto);

        // when
        RewardLogResponseDto result = rewardLogService.findOne(data.getId());

        System.out.println(data.getCreatedAt());
        System.out.println(result.getCreatedAt());

        // then
        assertThat(result.getId()).isEqualTo(data.getId());
        assertThat(result.getUserId()).isEqualTo(data.getUserId());
        assertThat(result.getDescription()).isEqualTo(data.getDescription());
        assertThat(result.getStatus()).isEqualTo(data.getStatus());
        assertThat(result.getReward()).isEqualTo(data.getReward());
        assertThat(result.getCreatedAt()).isBefore(data.getCreatedAt());
    }

    @Test
    @DisplayName("RewardLog 유저 전체 조회")
    public void findAllByUserId() {

        // Given
        String userId = "sampleUserId";
        List<RewardLog> rewardLogList = Arrays.asList(
                new RewardLog(1L, userId, "Description 1", "Status 1", 100, LocalDateTime.now()),
                new RewardLog(2L, userId, "Description 2", "Status 2", 200, LocalDateTime.now())
        );
        rewardLogRepository.saveAll(rewardLogList);

        // When
        List<RewardLogResponseDto> result = rewardLogService.findAllByUserId(userId);

        // Then
        assertEquals(rewardLogList.size(), result.size());

        for (int i = 0; i < rewardLogList.size(); i++) {
            RewardLogResponseDto expected = rewardLogMapper.rewardLogToRewardLogResponseDto(rewardLogList.get(i));
            RewardLogResponseDto actual = result.get(i);

            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getUserId(), actual.getUserId());
            assertEquals(expected.getDescription(), actual.getDescription());
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getReward(), actual.getReward());
        }


    }

}
