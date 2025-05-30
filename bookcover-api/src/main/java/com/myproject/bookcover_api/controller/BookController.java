package com.myproject.bookcover_api.controller;

import com.myproject.bookcover_api.dto.BookDto;
import com.myproject.bookcover_api.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;// (선택: 새 책 ID 생성을 위해 간단히 사용)


@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookDto.BookResponse> createBook(@RequestBody BookDto.BookCreate request) {
        BookDto.BookResponse response = bookService.createBook(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping
    public ResponseEntity<List<BookDto.BookResponse>> getAllBooks() {
        List<BookDto.BookResponse> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto.BookUpdate request) {
        BookDto updated = bookService.updateBook(id, request);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/generate-cover")
    public ResponseEntity<BookDto> updateBookImage(@PathVariable Long id, @RequestBody BookDto.BookUpdateImgUrl request) {
        BookDto updated = bookService.updateBookImg(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("도서(ID: " + id + ")가 성공적으로 삭제되었습니다.");
    }
}