package com.dotd.user.repository;

import com.dotd.user.entity.RewardLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardLogRepository extends JpaRepository<RewardLog, Long> {

    // 사용자 내역 전체 검색
    List<RewardLog> findByUserId(String userId);


}
