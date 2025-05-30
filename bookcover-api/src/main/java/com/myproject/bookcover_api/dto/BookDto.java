package com.myproject.bookcover_api.dto;

import com.myproject.bookcover_api.entity.Book;
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
    private String coverImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BookDto toBookDto(Book book){
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setContent(book.getContent());
        dto.setCoverImageUrl(book.getCoverImageUrl());
        dto.setCreatedAt(book.getCreatedAt());
        dto.setUpdatedAt(book.getUpdatedAt());
        return dto;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookCreate {
        private String title;
        private String author;
        private String content;

        public static Book toBookEntity(BookCreate dto) {
            Book book = new Book();
            book.setTitle(dto.getTitle());
            book.setAuthor(dto.getAuthor());
            book.setContent(dto.getContent());
            return book;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookUpdate {
        private String title;
        private String author;
        private String content;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookUpdateImgUrl {
        private String cover_image_url;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String content;
        private String coverImageUrl;
        private LocalDateTime createdAt;

        public static BookResponse toBookResponse(Book book) {
            BookResponse response = new BookResponse();
            response.setId(book.getId());
            response.setTitle(book.getTitle());
            response.setAuthor(book.getAuthor());
            response.setContent(book.getContent());
            response.setCoverImageUrl(book.getCoverImageUrl());
            response.setCreatedAt(book.getCreatedAt());
            return response;
        }
    }



}
