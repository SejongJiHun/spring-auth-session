package me.lgh.auth_module.auth.service;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.lgh.auth_module.auth.dto.LoginRequestDto;
import me.lgh.auth_module.auth.dto.SignupRequestDto;
import me.lgh.auth_module.exception.CustomException;
import me.lgh.auth_module.exception.ErrorCode;
import me.lgh.auth_module.user.domain.User;
import me.lgh.auth_module.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public static final String LOGIN_USER = "LOGIN_USER";

    // 회원가입
    public void signup(@Valid SignupRequestDto signupRequestDto){

        // 이메일을 소문자로 변환. Locale.ROOT를 지정해주면 언어 중립적이고 국제화 안전성이 보장
        String email = signupRequestDto.getEmail().toLowerCase(Locale.ROOT);

        // 이메일 중복 체크
        if (userRepository.findByEmail(email).isPresent()){
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());

        // User 객체 생성
        User user = new User(email, encodedPassword, signupRequestDto.getNickname());

        // db에 저장
        userRepository.save(user);

    }

    // 로그인
    public void login(@Valid LoginRequestDto loginRequestDto, HttpServletRequest request) {

        // 이메일을 소문자로 변환. Locale.ROOT를 지정해주면 언어 중립적이고 국제화 안전성이 보장
        String email = loginRequestDto.getEmail().toLowerCase(Locale.ROOT);

        // 이메일로 User 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 사용자가 입력한 비밀번호와 조회한 User의 비밀번호를 비교
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        request.getSession().setAttribute(LOGIN_USER, user.getId());

        log.info("[서비스 계층] 로그인 성공 - 세션 ID: {}", request.getSession().getId());
    }

    // 로그아웃
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }


}
