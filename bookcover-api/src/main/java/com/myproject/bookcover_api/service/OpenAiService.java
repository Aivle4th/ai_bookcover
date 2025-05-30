package com.myproject.bookcover_api.service;

import com.myproject.bookcover_api.dto.OpenAiImageDto;
import com.myproject.bookcover_api.entity.Book;
import com.myproject.bookcover_api.repository.BookRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;

@Service
@RequiredArgsConstructor
public class OpenAiService {

    private final BookRepository bookRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String OPENAI_URL = "https://api.openai.com/v1/images/generations";

    // 고정값 설정
    private final String MODEL = "dall-e-3";
    private final String QUALITY = "standard";
    private final String STYLE = "vivid";

    public OpenAiImageDto.OpenAiImageResponseDto generateImageFromBook(OpenAiImageDto.OpenAiImageRequestDto dto) throws Exception {
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("도서를 찾을 수 없습니다: ID = " + dto.getBookId()));

        String prompt = "A book cover illustration for the book titled \"" + book.getTitle() + "\". "
                + book.getContent() + " Style: " + STYLE;

        ObjectNode body = objectMapper.createObjectNode();
        body.put("prompt", prompt);
        body.put("model", MODEL);
        body.put("n", 1);
        body.put("quality", QUALITY);
        body.put("style", STYLE);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(dto.getApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                OPENAI_URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("OpenAI API 오류: " + response.getStatusCode());
        }

        JsonNode json = objectMapper.readTree(response.getBody());
        String imageUrl = json.get("data").get(0).get("url").asText();

        return new OpenAiImageDto.OpenAiImageResponseDto(imageUrl);
    }
}
