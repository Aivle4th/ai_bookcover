package com.myproject.bookcover_api.controller;

import com.myproject.bookcover_api.dto.BookDTO;
import com.myproject.bookcover_api.entity.Book;
import com.myproject.bookcover_api.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // 전체 조회
    @GetMapping
    public List<Book> getBookList() {
        return bookService.getBookList();
    }

    // 특정 도서 조회
    @GetMapping("/{id}")
    public BookDTO.getBook getBook(@PathVariable Long id){
        return bookService.getBookById(id);
    }

    // 도서 등록
    @PostMapping
    public BookDTO.getBook createBook(@RequestBody BookDTO.createBook createBook){
        return bookService.createBook(createBook);
    }

    @PatchMapping("/{id}")
    public BookDTO.getBook update(@PathVariable Long id, BookDTO.updateBook book){
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        bookService.deleteBook(id);
    }
}