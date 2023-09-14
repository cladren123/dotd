package com.dotd.user.controller;


import com.dotd.user.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
@Slf4j
public class CouponController {

    private final CouponService couponService;

    // 쿠폰 더미 등록
    @PostMapping("regist-dummy")
    public ResponseEntity<?> registDummy() {
        couponService.registDummy();
        return ResponseEntity.ok("ok");
    }


}
