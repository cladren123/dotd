package com.dotd.user.repository;


import com.dotd.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // 티어로 사용자 조회
    List<User> findByTier(String Tier);


    // loginId로 사용자 조회 
    User findByLoginId(String loginId);

}
