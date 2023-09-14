package com.dotd.user.repository;

import com.dotd.user.entity.RewardLog;
import com.dotd.user.entity.UsedMoneyLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsedMoneyLogRepository extends JpaRepository<UsedMoneyLog, Long> {

    // 사용자 내역 전체 검색
    List<UsedMoneyLog> findByUserId(String userId);
}
