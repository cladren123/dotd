package com.dotd.user.batch.processor;


import com.dotd.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


/*
User에게 적립금을 지급하는 Processor
 */

@Component
@RequiredArgsConstructor
public class UserProvideRewardProcessor implements ItemProcessor<User, User> {


    @Override
    public User process(User user) throws Exception {
        String tier = user.getTier();
        Integer reward = user.getReward();
        if (tier.equals("Bronze")) {
            user.setReward(reward + 1000);
        } else if (tier.equals("Silver")) {
            user.setReward(reward + 2000);
        } else if (tier.equals("Gold")) {
            user.setReward(reward + 3000);
        }

        return user;
    }
}
