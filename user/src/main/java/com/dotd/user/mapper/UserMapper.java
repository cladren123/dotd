package com.dotd.user.mapper;


import com.dotd.user.dto.user.UserRegisterRequestDto;
import com.dotd.user.dto.user.UserResponseDto;
import com.dotd.user.entity.User;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    // UserMapper instance = Mappers.getMapper(UserMapper.class);

    // 유저 정보를 반환
    public UserResponseDto userToUserResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .nickname(user.getNickname())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .reward(user.getReward())
                .usedMoney(user.getUsedMoney())
                .tier(user.getTier())
                .createdAt(user.getCreatedAt())
                .build();
    }


    // 회원 가입 폼을 유저로 반환
    public User userRegisterRequestDtoToUser(UserRegisterRequestDto dto) {
        return User.builder()
                .loginId(dto.getLoginId())
                .password(dto.getPassword())
                .lastName(dto.getLastName())
                .firstName(dto.getFirstName())
                .nickname(dto.getNickname())
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .build();
    }


}
