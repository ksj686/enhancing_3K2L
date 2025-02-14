package app.labs.register.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType; // 한글일 경우 꼭 필요함
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Controller
public class AiController {

	@Autowired
	private WebClient webClient;
	
	public String serviceRequest(String message) {
		// 메시지를 전송하기 위한 요청 본문 구성
		MultiValueMap<String, String> bodyBuilder = new LinkedMultiValueMap<>();
		bodyBuilder.add("message", message); // 메시지 폼 데이터 추가

		String result = webClient.post()
				.uri("/detect/filter") // FastAPI의 엔드포인트
				.contentType(MediaType.APPLICATION_FORM_URLENCODED) // 폼 데이터 전송
				.body(BodyInserters.fromFormData(bodyBuilder)) // 폼 데이터를 요청 본문으로 설정
				.retrieve() // 요청을 실행하고 응답을 받음
				.bodyToMono(String.class) // 본문을 String 타입으로 변환
				.block(); // 비동기처리를 동기적으로 블록해서 결과를 반환

		return result; // FastAPI에서 반환한 결과
	}
}
