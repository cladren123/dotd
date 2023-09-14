package com.dotd.user.service.usercoupon;


import com.dotd.user.dto.usercoupon.UserCouponRegisterRequestDto;
import com.dotd.user.dto.usercoupon.UserCouponResponseDto;

import java.util.List;

public interface UserCouponService {

    // 회원 쿠폰 등록
    public UserCouponResponseDto register(UserCouponRegisterRequestDto dto);

    // 회원이 가진 쿠폰 조회
    public List<UserCouponResponseDto> findAllByUserId(String userId);
}
