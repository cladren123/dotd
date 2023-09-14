package com.dotd.user.batch.processor;


import com.dotd.user.entity.UsedMoneyLog;
import com.dotd.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;


/*

User의 Tier를 업데이트하는 Processor

 */

@Component
@RequiredArgsConstructor
public class UserUpdateTierProcessor implements ItemProcessor<User, User> {


    @Override
    public User process(User user) throws Exception {
        Integer usedMoney = user.getUsedMoney();
        if(usedMoney >= 0 && usedMoney < 5000) {
            user.setTier("Bronze");
        }
        else if(usedMoney >= 5000 && usedMoney < 10000) {
            user.setTier("Silver");
        }
        else if(usedMoney >= 10000) {
            user.setTier("Gold");
        }
        return user;
    }

}
