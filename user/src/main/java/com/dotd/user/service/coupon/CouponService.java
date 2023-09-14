package com.dotd.user.service.coupon;

import com.dotd.user.dto.coupon.CouponRegisterRequestDto;
import com.dotd.user.dto.coupon.CouponResponseDto;
import org.springframework.stereotype.Service;


public interface CouponService {

    // 더미 쿠폰 등록
    public void registDummy();

    // 쿠폰 등록
    public CouponResponseDto register(CouponRegisterRequestDto dto);


    // 쿠폰 조회
    public CouponResponseDto find(Integer id);

}
