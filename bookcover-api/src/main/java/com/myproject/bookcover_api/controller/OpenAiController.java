package com.myproject.bookcover_api.controller;

import com.myproject.bookcover_api.dto.OpenAiImageDto;
import com.myproject.bookcover_api.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/openai")
@RequiredArgsConstructor
public class OpenAiController {

    private final OpenAiService openAiService;

    @PostMapping("/generate-cover")
    public ResponseEntity<?> generateCover(@RequestBody OpenAiImageDto.OpenAiImageRequestDto dto) {
        try {
            OpenAiImageDto.OpenAiImageResponseDto result = openAiService.generateImageFromBook(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ 오류: " + e.getMessage());
        }
    }
}