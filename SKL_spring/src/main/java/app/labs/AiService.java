package app.labs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType; // 한글일 경우 꼭 필요함
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiService {

	@Autowired
	private WebClient webClient;
	
	public String filterService(String message) {
        try {
            // FastAPI의 엔드포인트에 POST 요청
            Mono<String> resultMono = webClient.post()
                    .uri("/detect/filter") // FastAPI의 엔드포인트
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 폼 데이터 전송
                    .body(BodyInserters.fromFormData("message", message)) // 폼 데이터를 요청 본문으로 설정
                    .retrieve() // 요청을 실행하고 응답을 받음
                    .bodyToMono(String.class); // 본문을 String 타입으로 변환

            // JSON 응답을 처리
            String result = resultMono.block(); // 비동기처리를 동기적으로 블록해서 결과를 반환
            return result; // FastAPI에서 반환한 결과

        } catch (WebClientResponseException e) {
            // 오류 처리
            System.err.println("Error occurred: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
            return "Error occurred while processing the request.";
        }
	}
	
	public Map<String, String> feedbackService(String message) {
		// 메시지를 전송하기 위한 요청 본문 구성
		MultiValueMap<String, String> bodyBuilder = new LinkedMultiValueMap<>();
		bodyBuilder.add("message", message); // 메시지 폼 데이터 추가

        try {
            Mono<Map<String, String>> resultMono = webClient.post()
                    .uri("/detect/feedback")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(bodyBuilder))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {}); // 응답을 Map으로 변환
            
                    // JSON 응답을 처리
            Map<String, String> responseBody = resultMono.block(); // 비동기 처리를 동기적으로 블록해서 결과를 반환
            // String classify = responseBody.get("classify");
            // String feedback = responseBody.get("feedback");
            
            // 결과를 문자열로 반환
            return responseBody;


        } catch (WebClientResponseException e) {
            System.err.println("Error occurred: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
            // 오류 발생 시, Map 형태로 반환
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error occurred while processing the request.");
            errorResponse.put("status", e.getStatusCode().toString());
            return errorResponse; // Map 형태로 반환
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            // 일반적인 예외 처리
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Unexpected error occurred.");
            return errorResponse; // Map 형태로 반환
        }
	}
}
