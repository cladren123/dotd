package com.dotd.user.mapper;


import com.dotd.user.dto.usercoupon.UserCouponRegisterRequestDto;
import com.dotd.user.dto.usercoupon.UserCouponResponseDto;
import com.dotd.user.entity.UserCoupon;
import org.springframework.stereotype.Component;

@Component
public class UserCouponMapper {

    public UserCouponResponseDto userToUserCouponResponseDto(UserCoupon userCoupon) {
        return UserCouponResponseDto.builder()
                .id(userCoupon.getId())
                .userId(userCoupon.getUserId())
                .couponId(userCoupon.getCouponId())
                .createdAt(userCoupon.getCreatedAt())
                .build();
    }

    public UserCoupon userCouponRegisterRequestDto(UserCouponRegisterRequestDto dto) {
        return UserCoupon.builder()
                .userId(dto.getUserId())
                .couponId(dto.getCouponId())
                .build();
    }

}
