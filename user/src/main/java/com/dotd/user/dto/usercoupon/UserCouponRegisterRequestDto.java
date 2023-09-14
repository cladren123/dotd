package com.dotd.user.dto.usercoupon;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponRegisterRequestDto {

    private String userId;
    private Integer couponId;



}
