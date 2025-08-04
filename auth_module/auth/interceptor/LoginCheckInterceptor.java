package me.lgh.auth_module.auth.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.lgh.auth_module.auth.service.AuthService;
import me.lgh.auth_module.exception.CustomException;
import me.lgh.auth_module.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    //특정 요청 전에 로그인 여부(세션 유무)를 검사
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Object loginUser = request.getSession().getAttribute(AuthService.LOGIN_USER);
        log.info("JSESSIONID: {}", request.getSession().getId());
        log.info("LOGIN_USER: {}", request.getSession().getAttribute("LOGIN_USER"));
        if (loginUser == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED); // 예외는 GlobalExceptionHandler에서 처리됨
        }
        return true;
    }
}
