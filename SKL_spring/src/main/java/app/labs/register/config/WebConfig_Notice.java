package app.labs.register.config;

import app.labs.notice.interceptor.NoticeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor // final 필드를 매개변수로 받는 생성자 자동 생성해줌
public class WebConfig_Notice implements WebMvcConfigurer {

    private final NoticeInterceptor noticeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> staticResourcesPath = Arrays.stream(StaticResourceLocation.values())
                .flatMap(StaticResourceLocation::getPatterns)
                .collect(Collectors.toList());
        staticResourcesPath.add("");

        registry.addInterceptor(noticeInterceptor)
                .excludePathPatterns(staticResourcesPath);
    }
}

