package com.nbc.trello.entity.user;

import com.nbc.trello.global.dto.request.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j(topic = "UserServiceImpl")
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "f679d89c320cc4adb72b7647a64ccbe520406dc3ee4578b44bcffbfa7ebbb85e30b964306b6398d3a2d7098ecd1bc203551e356ac5ec4a5ee0c7dc899fb704c5";

    @Override
    public Long signup(SignupRequestDto requestDto){
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword()); // 비밀번호 암호화
        String nickname = requestDto.getUsername();

        // DB에 User 가 존재하는지 확인
        // isPresent() : Optional 객체에 값이 존재 여부 확인
        log.info("회원 존재 확인");
        if(userRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 새로운 객체 생성
        User user = new User(email, password, nickname, UserRoleEnum.USER);   // role 추가

        // JPA : DB에 새로운 객체 저장
        log.info("회원가입 성공");
        userRepository.save(user);

        return user.getId();
    }
}
