package com.dotd.user.service;


import com.dotd.user.dto.user.UserLoginRequestDto;
import com.dotd.user.dto.user.UserResponseDto;
import com.dotd.user.entity.User;
import com.dotd.user.repository.UserRepository;
import com.dotd.user.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("로그인 테스트")
    public void login() {
        // given
        String userId = "dummyUserId";
        String password = "password";
        UserLoginRequestDto dto = UserLoginRequestDto.builder()
                .loginId(userId)
                .password(password)
                .build();

        User user = User.builder()
                .loginId(userId)
                .password(password)
                .build();
        User save = userRepository.save(user);

        // when
        UserResponseDto result = userService.login(dto);

        // then
        assertEquals(result.getLoginId(), userId);
        assertEquals(result.getPassword(), password);
    }


}
