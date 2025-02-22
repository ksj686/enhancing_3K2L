package app.labs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public String feedbackService(String message) {
        try {
            // FastAPI의 엔드포인트에 POST 요청
            Mono<String> resultMono = webClient.post()
                    .uri("/detect/feedback") // FastAPI의 엔드포인트
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 폼 데이터 전송
                    .body(BodyInserters.fromFormData("message", message)) // 폼 데이터를 요청 본문으로 설정
                    .retrieve() // 요청을 실행하고 응답을 받음
                    .bodyToMono(String.class); // 본문을 String 타입으로 변환

            // JSON 응답을 처리
            String result = resultMono.block(); // 비동기처리를 동기적으로 블록해서 결과를 반환
            System.out.println(result);
            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(result);
            System.out.println(jsonNode);
            return jsonNode.get("result").asText(); // "result" 필드의 값을 반환

        } catch (WebClientResponseException e) {
            // 오류 처리
            System.err.println("Error occurred: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
            return "Error occurred while processing the request.";
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            return "Error occurred while processing the request.";
        }
    }
}
