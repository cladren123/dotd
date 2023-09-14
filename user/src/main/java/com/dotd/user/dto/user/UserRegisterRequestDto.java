package com.dotd.user.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;


/*
회원 가입 시 입력 받는 데이터

로그인ID
비밀번호
비밀번호 체크
성
이름
닉네임
주소
휴대전화
이메일
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDto {

    private String loginId;
    private String password;
    private String lastName;
    private String firstName;
    private String nickname;
    private String address;
    private String phoneNumber;
    private String email;

}
