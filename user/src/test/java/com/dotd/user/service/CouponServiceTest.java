package com.dotd.user.service;


import com.dotd.user.dto.coupon.CouponRegisterRequestDto;
import com.dotd.user.dto.coupon.CouponResponseDto;
import com.dotd.user.service.coupon.CouponService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    @DisplayName("쿠폰 등록 테스트")
    public void register() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sixDaysLater = now.plusDays(6).with(LocalTime.of(23, 59));

        CouponRegisterRequestDto dto = CouponRegisterRequestDto.builder()
                .name("브론즈 쿠폰")
                .description("브론즈 회원들을 위한 쿠폰")
                .discount(10)
                .duration(sixDaysLater)
                .build();

        // when
        CouponResponseDto result = couponService.register(dto);

        // then
        // 실제 값 - 기대 값 을 비교한다.
        assertThat(result.getName()).isEqualTo(dto.getName());
        assertThat(result.getDescription()).isEqualTo(dto.getDescription());
        assertThat(result.getDiscount()).isEqualTo(dto.getDiscount());
        assertThat(result.getDuration()).isEqualTo(dto.getDuration());
    }

    @Test
    @DisplayName("쿠폰 하나 조회 테스트")
    public void find() {
        // Given : 쿠폰 등록
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sixDaysLater = now.plusDays(6).with(LocalTime.of(23, 59));

        CouponRegisterRequestDto dto = CouponRegisterRequestDto.builder()
                .name("브론즈 쿠폰")
                .description("브론즈 회원들을 위한 쿠폰")
                .discount(10)
                .duration(sixDaysLater)
                .build();

        CouponResponseDto data = couponService.register(dto);



        Integer id = data.getId();

        // When : 쿠폰 ID로 조회
        CouponResponseDto result = couponService.find(id);

        // Then : 조회 결과 검증
        System.out.println(result.getCreatedAt());
        System.out.println(data.getCreatedAt());

        assertThat(result.getId()).isEqualTo(data.getId());
        assertThat(result.getName()).isEqualTo(data.getName());
        assertThat(result.getDescription()).isEqualTo(data.getDescription());
        assertThat(result.getDiscount()).isEqualTo(data.getDiscount());
        assertThat(result.getDuration()).isEqualTo(data.getDuration());
        assertThat(result.getCreatedAt()) .isEqualTo(data.getCreatedAt());

    }



}
