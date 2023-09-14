package com.dotd.user.service.user;


import com.dotd.user.dto.user.UserLoginRequestDto;
import com.dotd.user.dto.user.UserResponseDto;
import com.dotd.user.dto.user.UserRegisterRequestDto;
import com.dotd.user.entity.User;

import java.util.List;

public interface UserService {

    // 회원 가입
    public UserResponseDto register(UserRegisterRequestDto dto);

    // 더미 회원들 등록
    public void registDummy();

    // 회원 조회
    public UserResponseDto find(String id);


    // Tier 별 회원 조회
    public void findAllByTier(String tier);

    // 로그인 로직
    public UserResponseDto login(UserLoginRequestDto dto);






}
