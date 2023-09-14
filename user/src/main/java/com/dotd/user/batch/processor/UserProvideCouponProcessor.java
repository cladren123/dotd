package com.dotd.user.batch.processor;

import com.dotd.user.entity.User;
import com.dotd.user.entity.UserCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/*
User에게 Coupon을 지급하는 Processor
 */

@Component
@RequiredArgsConstructor
public class UserProvideCouponProcessor implements ItemProcessor<User, UserCoupon> {

    @Override
    public UserCoupon process(User user) throws Exception {

        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(user.getId());
        String tier = user.getTier();

        if (tier.equals("Bronze")) {
            userCoupon.setCouponId(1);
        } else if (tier.equals("Silver")) {
            userCoupon.setCouponId(2);
        } else if (tier.equals("Gold")) {
            userCoupon.setCouponId(3);
        }

        return userCoupon;
    }

}
