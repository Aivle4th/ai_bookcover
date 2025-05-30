package com.myproject.bookcover_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookResDto {

    private Long id;

    private String title;

    private String author;

    private String content;

    private String coverImageUrl;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
