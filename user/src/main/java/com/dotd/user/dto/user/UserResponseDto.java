package com.dotd.user.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/*

회원 데이터를 반환하는 DTO

성
이름
닉네임
주소
전화번호
이메일
적립금
사용 금액
티어

 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {


    private String id;
    private String loginId;
    private String password;
    private String lastName;
    private String firstName;
    private String nickname;
    private String address;
    private String phoneNumber;
    private String email;
    private Integer reward;
    private Integer usedMoney;
    private String tier;
    private LocalDateTime createdAt;



}
