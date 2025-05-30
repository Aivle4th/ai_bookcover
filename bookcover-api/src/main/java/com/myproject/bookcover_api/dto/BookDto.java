package com.myproject.bookcover_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private String cover_image_url;
    private LocalDateTime created_at;
    private LocalDateTime update_at;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {
        private String title;
        private String author;
        private String content;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        private String title;
        private String author;
        private String content;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateImgUrl {
        private String cover_image_url;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String author;
        private String content;
        private String cover_image_url;
        private String created_at;
    }



}
