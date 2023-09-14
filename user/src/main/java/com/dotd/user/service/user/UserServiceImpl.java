package com.dotd.user.service.user;

import com.dotd.user.dto.rewardlog.RewardLogRegisterRequestDto;
import com.dotd.user.dto.usedmoneylog.UsedMoneyLogRegisterRequestDto;
import com.dotd.user.dto.user.UserLoginRequestDto;
import com.dotd.user.dto.user.UserResponseDto;
import com.dotd.user.dto.user.UserRegisterRequestDto;
import com.dotd.user.entity.User;
import com.dotd.user.exception.FieldDataException;
import com.dotd.user.mapper.UserMapper;
import com.dotd.user.repository.UsedMoneyLogRepository;
import com.dotd.user.repository.UserRepository;
import com.dotd.user.service.rewardlog.RewardLogService;
import com.dotd.user.service.usedmoneylog.UsedMoneyLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;


/*

user의 비지니스 로직을 처리하는 클래스

 */


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {


    // repository
    private final UserRepository userRepository;

    // mapper
    private final UserMapper userMapper;


    private final UsedMoneyLogService usedMoneyLogService;


    // 회원 등록
    @Override
    public UserResponseDto register(UserRegisterRequestDto dto) {

        // 예외 발생시 클래스를 만들어 예외 처리 실행
        if (dto.getLoginId() == null || dto.getLoginId().isEmpty()) {
            throw new FieldDataException("loginId", "loginId가 비어있습니다.");
        }
        else if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new FieldDataException("password", "password가 비어있습니다.");
        }

        log.info("userService의 register 실행");
        User user = userMapper.userRegisterRequestDtoToUser(dto);
        User save = userRepository.save(user);
        UserResponseDto result = userMapper.userToUserResponseDto(save);

        return result;
    }

    // 더미 회원들 등록 + 유저 당 적립 내역 10개 등록
    @Override
    public void registDummy() {
        Random random = new Random();
        for(int i = 1; i < 10001; i++) {
            User dummyUser = User.builder()
                    .loginId("dummyid" + i)
                    .password("password" + i)
                    .lastName("kim" + i)
                    .firstName("dong" + i)
                    .nickname("nick" + i)
                    .address("address" + i)
                    .phoneNumber("phone" + i)
                    .email("email" + i)
                    .build();

            User user = userRepository.save(dummyUser);

            // 더미 유저 한명단 10개의 적립금 생성
            for(int j = 0; j < 10; j++) {
                int usedMoney = random.nextInt(2000);
                UsedMoneyLogRegisterRequestDto usedMoneyLog = UsedMoneyLogRegisterRequestDto.builder()
                        .userId(user.getId())
                        .description("사용 내역")
                        .usedMoney(usedMoney)
                        .build();

                usedMoneyLogService.register(usedMoneyLog);
            }

        }
    }


    // 회원 조회
    @Override
    public UserResponseDto find(String id) {
        User user = userRepository.findById(id).get();
        UserResponseDto result = userMapper.userToUserResponseDto(user);
        return result;
    }

    // 티어 별 회원 조회
    @Override
    public void findAllByTier(String tier) {
        List<User> allByTier = userRepository.findByTier(tier);
        log.info("{}의 전체 유저 수 : {}", tier, allByTier.size());

    }

    // 로그인 로직
    @Override
    public UserResponseDto login(UserLoginRequestDto dto) {
        User user = userRepository.findByLoginId(dto.getLoginId());

        // loginId가 틀렸을 경우
        if(user == null) {
            return null;
        }

        // password가 일치하면 로그인 진행
        if (user.getPassword().equals(dto.getPassword())) {
            UserResponseDto result = userMapper.userToUserResponseDto(user);
            return result;
        }
        // password가 일차하지 않으면 null 반환
        else {
            return null;
        }


    }


}


