package app.labs.register.config;

import org.springframework.context.annotation.Bean; // Bean 어노테이션을 위한 임포트
import org.springframework.context.annotation.Configuration; // Configuration 어노테이션을 위한 임포트
import org.springframework.web.reactive.function.client.ExchangeStrategies; // ExchangeStrategies를 위한 임포트
import org.springframework.web.reactive.function.client.WebClient; // WebClient를 위한 임포트

@Configuration // 이 클래스가 Spring의 설정 클래스임을 나타냄
public class WebClientConfig {

    @Bean // 이 메서드가 Spring의 Bean으로 등록됨을 나타냄
    WebClient webClient() {   // Spring 애플리케이션에서 HTTP요청할 때 사용 (과거의 RestTemplate)
        return WebClient.builder() // WebClient의 빌더를 생성
                .exchangeStrategies(ExchangeStrategies.builder() // ExchangeStrategies 설정
                        .codecs(configurer -> configurer.defaultCodecs() // 기본 코덱 설정
                                .maxInMemorySize(-1)) // 메모리 크기를 무제한으로 설정
                        .build()) // ExchangeStrategies 빌드
                .baseUrl("http://localhost:8000") // 기본 URL 설정
                .build(); // WebClient 빌드
    }
}