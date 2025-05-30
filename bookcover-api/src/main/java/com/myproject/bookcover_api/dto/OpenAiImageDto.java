package com.myproject.bookcover_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


public class OpenAiImageDto {

    @Data
    public static class OpenAiImageRequestDto {
        private String apiKey;
        private Long bookId;  // (dall-e-3 전용)
    }

    @Data
    @AllArgsConstructor
    public static class OpenAiImageResponseDto {
        private String imageUrl;
    }
}
