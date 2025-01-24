package app.labs.register.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import app.labs.register.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/members/**")
                .excludePathPatterns("/login", "/logout", "/members/insert", "/members/check-memberid", "/members/find-username", "/members/find-password"); // 인증 예외 경로 추가
    }
}
