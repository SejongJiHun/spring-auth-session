package me.lgh.auth_module.config;


import lombok.RequiredArgsConstructor;
import me.lgh.auth_module.auth.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/api/me", "/api/ping") // 인증 필요한 경로
                .excludePathPatterns("/api/login", "/api/signup", "/api/logout"); // 인증 필요 없는 경로
    }
}
