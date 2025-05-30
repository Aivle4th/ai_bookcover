package com.myproject.bookcover_api.controller;

import com.myproject.bookcover_api.dto.BookReqDto;
import com.myproject.bookcover_api.dto.BookResDto;
import com.myproject.bookcover_api.entity.Book;
import com.myproject.bookcover_api.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping; // (나중에 삭제 API 만들 때 필요할 수 있어서 미리 추가)
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;  // (나중에 생성 API 만들 때 필요할 수 있어서 미리 추가)
import org.springframework.web.bind.annotation.PutMapping;    // PutMapping 임포트
import org.springframework.web.bind.annotation.RequestBody;  // RequestBody 임포트
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // GET /api/books (도서 목록 조회)
    @GetMapping("/books")
    public List<BookResDto> getBooks() {
        List<Book> books = bookService.getBooks(); // 엔티티 리스트를 받는다.

        List<BookResDto> bookResDtoList = new ArrayList<>(); // Dto 리스트 생성

        for (Book book : books) {
            BookResDto bookResDto = toBookResDto(book); // 엔티티를 Dto로 변환
            bookResDtoList.add(bookResDto); // 변환한 Dto 리스트에 추가
        }

        return bookResDtoList; // Dto 리스트 반환
    }

    // GET /api/books/{id} (특정 도서 조회)
    @GetMapping("/books/{id}")
    public ResponseEntity<BookResDto> getBookById(@PathVariable Long id) {
        try {
            Book book = bookService.getBookById(id); // 엔티티 받기
            BookResDto dto = toBookResDto(book); // 엔티티 Dto로 변환
            return ResponseEntity.ok(dto); // Dto 반환
        } catch (EntityNotFoundException e) { // 엔티티가 없는 예외 처리
            System.out.println("도서를 찾을 수 없음: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BookResDto()); // 빈 Dto 404로 반환
        }
    }

    // 엔티티를 Dto로 변환해주는 메서드
    private BookResDto toBookResDto(Book book) {
        BookResDto bookResDto = new BookResDto();
        bookResDto.setId(book.getId());
        bookResDto.setTitle(book.getTitle());
        bookResDto.setAuthor(book.getAuthor());
        bookResDto.setContent(book.getContent());
        bookResDto.setCoverImageUrl(book.getCover_image_url());
        bookResDto.setCreateAt(book.getCreated_at());
        bookResDto.setUpdateAt(book.getUpdate_at());
        return  bookResDto;
    }

    // POST /api/books (새 도서 등록 - 프론트엔드 테스트를 위해 간단히 추가)
    // 실제로는 BookCreateRequestDto 같은 DTO를 사용하는 것이 좋습니다.
    @PostMapping("/books")
    public ResponseEntity<BookResDto> createBook(@RequestBody BookReqDto bookReqDto) {
        Book book = new Book(); // 엔티티 객체 생성
        // Dto 데이터를 엔티티로 매핑
        book.setTitle(bookReqDto.getTitle());
        book.setAuthor(bookReqDto.getAuthor());
        book.setContent(bookReqDto.getContent());
        book.setCreated_at(LocalDateTime.now());
        book.setUpdate_at(LocalDateTime.now());

        System.out.println("백엔드: /api/books (POST) 호출됨, 다음 도서가 생성되었습니다: " + book);
        Book savedBook = bookService.saveBook(book); // 엔티티 저장
        BookResDto bookResDto = toBookResDto(savedBook); // 엔티티 Dto로 변환

        return ResponseEntity.status(HttpStatus.CREATED).body(bookResDto);
    }


    // --- 👇 특정 ID의 도서 정보를 수정하는 API 추가 👇 ---
    @PutMapping("/books/{id}")
    public ResponseEntity<BookResDto> updateBook(@PathVariable Long id, @RequestBody BookReqDto updatedBook) {
        try {
            Book book = bookService.updateBook(id, updatedBook); // 엔티티 받기
            BookResDto bookResDto = toBookResDto(book); // 엔티티 Dto로 변환
            return ResponseEntity.ok().body(bookResDto); // Dto 반환
        } catch (EntityNotFoundException e) { // 엔티티가 없는 예외 처리
            System.out.println("도서를 찾을 수 없음: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BookResDto()); // 빈 Dto 404로 반환
        }
    }
    // --- 👆 특정 ID의 도서 정보를 수정하는 API 추가 완료 👆 ---

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    // (향후 AI 표지 생성, 도서 삭제 API 등을 여기에 추가할 수 있습니다.)
}