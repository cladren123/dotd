package com.dotd.user.batch.processor;

import com.dotd.user.entity.UsedMoneyLog;
import com.dotd.user.entity.User;
import com.dotd.user.repository.UsedMoneyLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class UserUpdateUsedMoneyProcessor implements ItemProcessor<User, User> {

    private final UsedMoneyLogRepository usedMoneyLogRepository;

    @Override
    public User process(User user) throws Exception {
        List<UsedMoneyLog> usedMoneyLogList = usedMoneyLogRepository.findByUserId(user.getId());
        int totalUsedMoney = usedMoneyLogList.stream().mapToInt(UsedMoneyLog::getUsedMoney).sum();
        user.setUsedMoney(totalUsedMoney);
        return user;
    }
}
