package com.dotd.user.mapper;


import com.dotd.user.dto.coupon.CouponRegisterRequestDto;
import com.dotd.user.dto.coupon.CouponResponseDto;
import com.dotd.user.entity.Coupon;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {

    // 쿠폰 정보 반환
    public CouponResponseDto couponToCouponResponseDto(Coupon coupon) {
        return CouponResponseDto.builder()
                .id(coupon.getId())
                .name(coupon.getName())
                .description(coupon.getDescription())
                .discount(coupon.getDiscount())
                .duration(coupon.getDuration())
                .createdAt(coupon.getCreatedAt())
                .build();
    }

    // 쿠폰 등록 폼 데이터를 쿠폰으로 변환
    public Coupon couponRegisterRequestDtoToCoupon(CouponRegisterRequestDto dto) {
        return Coupon.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .discount(dto.getDiscount())
                .duration(dto.getDuration())
                .build();
    }

}
