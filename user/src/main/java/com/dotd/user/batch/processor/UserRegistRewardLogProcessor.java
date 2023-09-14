package com.dotd.user.batch.processor;


import com.dotd.user.entity.RewardLog;
import com.dotd.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/*
User의 등급에 따라 지급된 적립금의 내역을 저장하는 Processor
 */

@Component
@RequiredArgsConstructor
public class UserRegistRewardLogProcessor implements ItemProcessor<User, RewardLog> {

    @Override
    public RewardLog process(User user) throws Exception {
        String tier = user.getTier();
        int provideReward = 0;
        if (tier.equals("Bronze")) {
            provideReward = 1000;
        }
        else if(tier.equals("Silver")) {
            provideReward = 2000;
        }
        else if(tier.equals("Gold")) {
            provideReward = 3000;
        }

        return RewardLog.builder()
                .userId(user.getId())
                .description("등급 별 적립금 지급")
                .status("적립")
                .reward(provideReward)
                .build();
    }
}
