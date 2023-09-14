package com.dotd.user.service;


import com.dotd.user.dto.usercoupon.UserCouponRegisterRequestDto;
import com.dotd.user.dto.usercoupon.UserCouponResponseDto;
import com.dotd.user.entity.UserCoupon;
import com.dotd.user.mapper.UserCouponMapper;
import com.dotd.user.repository.UserCouponRepository;
import com.dotd.user.service.usercoupon.UserCouponService;
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
public class UserCouponServiceTest {

    @Autowired
    private UserCouponService userCouponService;
    @Autowired
    private UserCouponRepository userCouponRepository;
    @Autowired
    private UserCouponMapper userCouponMapper;


    @Test
    @DisplayName("회원 쿠폰 지급")
    public void register() {
        // given
        UserCouponRegisterRequestDto dto = UserCouponRegisterRequestDto.builder()
                .userId("dummy")
                .couponId(1)
                .build();

        // when
        UserCouponResponseDto result = userCouponService.register(dto);

        // then
        assertThat(result.getUserId()).isEqualTo(dto.getUserId());
        assertThat(result.getCouponId()).isEqualTo(dto.getCouponId());
    }

    @Test
    @DisplayName("유저가 가진 쿠폰 조회")
    public void findAllByUserId() {
        // given
        String userId = "sampleUserId";
        List<UserCoupon> userCouponList = Arrays.asList(
                new UserCoupon(1L, userId, 1, LocalDateTime.now()),
                new UserCoupon(2L, userId, 2, LocalDateTime.now())
        );
        userCouponRepository.saveAll(userCouponList);

        // when
        List<UserCouponResponseDto> result = userCouponService.findAllByUserId(userId);

        // then
        assertEquals(result.size(), userCouponList.size());

        for(int i = 0; i < result.size(); i++) {
            UserCouponResponseDto expected = userCouponMapper.userToUserCouponResponseDto(userCouponList.get(i));
            UserCouponResponseDto actual = result.get(i);

            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getUserId(), actual.getUserId());
            assertEquals(expected.getCouponId(), actual.getCouponId());

        }
    }


}
