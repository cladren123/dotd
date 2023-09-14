package com.dotd.user.dto.coupon;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponRegisterRequestDto {

    private String name;
    private String description;
    private Integer discount;
    private LocalDateTime duration;




}
