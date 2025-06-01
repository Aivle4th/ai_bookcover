package com.myproject.bookcover_api.controller;

import com.myproject.bookcover_api.dto.BookDto;
import com.myproject.bookcover_api.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;// (선택: 새 책 ID 생성을 위해 간단히 사용)


@RestController
@RequestMapping("/api/books") // 모든 도서 관련 API는 /api/books 로 시작
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 새 도서 등록 (POST /api/books)
    @PostMapping
    public ResponseEntity<BookDto.BookResponse> createBook(@RequestBody BookDto.BookCreate request) {
        BookDto.BookResponse response = bookService.createBook(request);
        return ResponseEntity.ok(response); // 또는 ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 특정 도서 상세 조회 (GET /api/books/{id})
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // 전체 도서 목록 조회 (GET /api/books)
    @GetMapping
    public ResponseEntity<List<BookDto.BookResponse>> getAllBooks(
            // 검색 기능을 위해 @RequestParam 추가 (예시, 실제 서비스 로직에 맞게 구현 필요)
            @RequestParam(required = false) String title
    ) {
        List<BookDto.BookResponse> books;
        if (title != null && !title.isEmpty()) {
            // TODO: bookService에 제목으로 검색하는 메소드 추가 필요 (예: bookService.searchBooksByTitle(title))
            // 여기서는 임시로 전체 목록을 반환하거나, 서비스에서 처리하도록 위임합니다.
            // books = bookService.searchBooksByTitle(title);
            books = bookService.getAllBooks(); // 우선 전체 목록 반환으로 가정
        } else {
            books = bookService.getAllBooks();
        }
        return ResponseEntity.ok(books);
    }

    // 특정 도서 정보 전체 수정 (PUT /api/books/{id})
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto.BookUpdate request) {
        BookDto updated = bookService.updateBook(id, request);
        return ResponseEntity.ok(updated);
    }

    // --- 👇 AI 표지 생성 후 이미지 URL 업데이트 API 수정 👇 ---
    // 프론트엔드에서 PUT /api/books/{id}/cover-url 로 호출하는 것을 처리합니다.
    @PutMapping("/{id}/cover-url")
    public ResponseEntity<BookDto> updateBookCoverUrl(@PathVariable Long id, @RequestBody BookDto.BookUpdateImgUrl request) {
        System.out.println("백엔드: /api/books/" + id + "/cover-url (PUT) 호출됨. 요청 본문: " + request); // 요청 DTO 로그 추가
        // coverImageUrl 필드가 있는지 확인하려면 request.getCoverImageUrl() 등을 로깅
        System.out.println("백엔드: 전달받은 coverImageUrl: " + (request != null ? request.getCoverImageUrl() : "null"));

        BookDto updated = bookService.updateBookImg(id, request); // 또는 updateBookCoverUrl
        System.out.println("백엔드: 서비스에서 반환된 업데이트된 BookDto: " + updated); // 서비스 반환값 로그 추가
        return ResponseEntity.ok(updated);
    }
    // --- 👆 AI 표지 생성 후 이미지 URL 업데이트 API 수정 완료 👆 ---

    // 특정 도서 삭제 (DELETE /api/books/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("도서(ID: " + id + ")가 성공적으로 삭제되었습니다.");
    }
}