package com.myproject.bookcover_api.dto;

import lombok.Data;

@Data
public class BookReqDto {

    private String title;

    private String author;

    private String content;
}
