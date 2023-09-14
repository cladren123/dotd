package com.dotd.user.service.usercoupon;


import com.dotd.user.dto.user.UserResponseDto;
import com.dotd.user.dto.usercoupon.UserCouponRegisterRequestDto;
import com.dotd.user.dto.usercoupon.UserCouponResponseDto;
import com.dotd.user.entity.UserCoupon;
import com.dotd.user.mapper.UserCouponMapper;
import com.dotd.user.repository.UserCouponRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserCouponServiceImpl implements UserCouponService{

    private final UserCouponRepository userCouponRepository;

    private final UserCouponMapper userCouponMapper;


    // 유저 쿠폰 지급
    public UserCouponResponseDto register(UserCouponRegisterRequestDto dto) {
        UserCoupon userCoupon = userCouponMapper.userCouponRegisterRequestDto(dto);
        UserCoupon save = userCouponRepository.save(userCoupon);
        UserCouponResponseDto result = userCouponMapper.userToUserCouponResponseDto(save);
        return result;
    }

    // 유저가 가진 쿠폰 확인
    public List<UserCouponResponseDto> findAllByUserId(String userId) {
        List<UserCoupon> allByUserId = userCouponRepository.findAllByUserId(userId);
        List<UserCouponResponseDto> result = new ArrayList<>();
        for (UserCoupon data : allByUserId) {
            UserCouponResponseDto userCouponResponseDto = userCouponMapper.userToUserCouponResponseDto(data);
            result.add(userCouponResponseDto);
        }
        return result;
    }



}
