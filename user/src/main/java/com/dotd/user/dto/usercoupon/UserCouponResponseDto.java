package com.dotd.user.dto.usercoupon;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponResponseDto {

    private Long id; // user - coupon 연관관계 ID
    private String userId;
    private Integer couponId;
    private LocalDateTime createdAt; // 지급일


}
