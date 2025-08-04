package me.lgh.auth_module.auth.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.lgh.auth_module.auth.dto.LoginRequestDto;
import me.lgh.auth_module.auth.dto.SignupRequestDto;
import me.lgh.auth_module.auth.service.AuthService;

import me.lgh.auth_module.common.dto.ApiResponse;
import me.lgh.auth_module.exception.CustomException;
import me.lgh.auth_module.exception.ErrorCode;
import me.lgh.auth_module.user.domain.User;
import me.lgh.auth_module.user.dto.UserResponseDto;
import me.lgh.auth_module.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        log.info("회원가입 시작");
        authService.signup(signupRequestDto);
        log.info("회원가입 종료");

        return ResponseEntity.ok(ApiResponse.success("회원가입 성공"));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequestDto dto, HttpServletRequest request, HttpServletResponse response) {
        log.info("로그인 시작");
        authService.login(dto, request);

        // 테스트용: 세션 ID 로그
        String sessionId = request.getSession().getId();
        log.info("[컨트롤러 계층] 세션 ID 확인용: {}", sessionId);
        // 강제로 Set-Cookie 삽입 (디버깅용)
        //response.addHeader("Set-Cookie", "debug-session=" + sessionId + "; Path=/; HttpOnly");

        log.info("로그인 종료");
        return ResponseEntity.ok(ApiResponse.success("로그인 성공"));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletRequest request) {
        log.info("로그아웃 시작");
        authService.logout(request);
        log.info("로그아웃 종료");
        return ResponseEntity.ok(ApiResponse.success("로그아웃 성공"));
    }

    // 세션체크. 세션객체에 접근만 해도 세션만료시간은 자동 연장됨
    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getMyInfo(HttpServletRequest request) {
        log.info("세션체크 시작");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(AuthService.LOGIN_USER) == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        Long userId = (Long) session.getAttribute(AuthService.LOGIN_USER);
        User user = userService.findById(userId);

        log.info("세션체크 종료");
        return ResponseEntity.ok(ApiResponse.success(new UserResponseDto(user)));
    }

    // 세션만료시간 연장. 세션객체에 접근만 해도 세션만료시간은 자동 연장됨
    @GetMapping("/ping")
    public ResponseEntity<ApiResponse> pingSession(HttpServletRequest request) {
        log.info("세션연장 시작");

        HttpSession session = request.getSession(false); // 세션이 없으면 null을 반환
        if (session == null || session.getAttribute(AuthService.LOGIN_USER) == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        log.info("세션연장 종료");

        return ResponseEntity.ok(ApiResponse.success("OK"));
    }





}
