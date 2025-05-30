package com.myproject.bookcover_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class BookDTO {

    //도서 등록용
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class createBook{
        private String title;
        private String author;
        private String content;
    }

    //도서 조회용
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getBook{
        private Long id;
        private String title;
        private String content;
        private String coverImageUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    //도서 정보 수정
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateBook{
        private String title;
        private String content;
        private String coverImageUrl;
        private LocalDateTime updatedAt;
    }



}
