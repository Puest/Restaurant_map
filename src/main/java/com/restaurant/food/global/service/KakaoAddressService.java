package com.restaurant.food.global.service;

import com.restaurant.food.global.dto.KakaoApiResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class KakaoAddressService {
    @Value("${kakao.key}")
    private String apiKey;

    // 카카오 주소 검색 API 엔드포인트
    private final String API_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    /**
     * * 주소 문자열을 받아 위도/경도를 반환하는 메서드
     * *
     * * @param address 검색할 주소 (예: "서울 강남구 테헤란로")
     * * @return Coordinate (위도, 경도)
     */
    public Coordinate getCoordinate(String address) {
        // RestTemplate 생성
        RestTemplate restTemplate = new RestTemplate();
        // HTTP 헤더 객체 생성
        // 요청 헤더 설정 (예: Authorization: KakaoAK {REST_API_KEY})
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);

        // HTTP 요청 본문(body) + 헤더를 함께 묶는 객체
        // GET 요청이므로 body는 필요 없고 헤더만 사용
        HttpEntity<String> entity = new HttpEntity<>(headers);
        // URI 생성 (쿼리 파라미터 자동 인코딩)
        UriComponents uri = UriComponentsBuilder.fromUriString(API_URL)
                .queryParam("query", address)
                .build();

        // 실제 HTTP GET 요청 실행
        // exchange(요청 주소, Method 방식, 엔티티, 응답 JSON)
        ResponseEntity<KakaoApiResponseDto> response = restTemplate.exchange(
                uri.toUriString(), HttpMethod.GET, entity, KakaoApiResponseDto.class);

        // 응답 바디가 있고, 주소 검색 결과가 있으면
        if (response.getBody() != null && !response.getBody().getDocuments().isEmpty()) {
            // 첫 번째 검색 결과 사용
            var doc = response.getBody().getDocuments().get(0);

            //
            return new Coordinate(
                    Double.parseDouble(doc.getLatitude()),
                    Double.parseDouble(doc.getLongitude())
            );
        }

        throw new RuntimeException("주소를 찾을 수 없습니다.");
    }

    @AllArgsConstructor
    @Getter
    public static class Coordinate {
        private double lat;
        private double lng;
    }
}
