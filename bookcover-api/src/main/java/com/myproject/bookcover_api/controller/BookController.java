package com.myproject.bookcover_api.controller;

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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong; // (선택: 새 책 ID 생성을 위해 간단히 사용)

@RestController
@RequestMapping("/api")
public class BookController {

    // 가짜 데이터베이스 역할을 할 리스트
    // 클래스 멤버로 선언하여 여러 메소드에서 공유하도록 변경
    private List<Map<String, Object>> mockBookDatabase = initializeMockBooks();
    private AtomicLong idCounter = new AtomicLong(3); // 초기 책 개수에 맞춰 ID 카운터 설정

    // 가짜 도서 데이터 초기화 메소드
    private List<Map<String, Object>> initializeMockBooks() {
        List<Map<String, Object>> bookList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        Map<String, Object> book1 = new HashMap<>();
        book1.put("id", 1L);
        book1.put("title", "AI가 만든 첫 번째 이야기 (백엔드 테스트용)");
        book1.put("author", "백엔드 봇");
        book1.put("content", "이것은 백엔드에서 보내주는 첫 번째 테스트용 도서 내용입니다.\n프론트엔드에서 잘 보이나요?\n\n여러 줄 테스트도 해봅니다.");
        book1.put("coverImageUrl", "https://via.placeholder.com/300x450/FF0000/FFFFFF?Text=Book1+Detail");
        book1.put("createdAt", LocalDateTime.now().minusDays(2).format(formatter) + "Z");
        book1.put("updatedAt", LocalDateTime.now().minusDays(1).format(formatter) + "Z");
        bookList.add(book1);

        Map<String, Object> book2 = new HashMap<>();
        book2.put("id", 2L);
        book2.put("title", "React와 Spring Boot의 만남");
        book2.put("author", "개발자 K");
        book2.put("content", "프론트엔드와 백엔드가 드디어 만났습니다!\n신나는 테스트 시간!\n이 책은 아주 재미있습니다.");
        book2.put("coverImageUrl", "https://via.placeholder.com/300x450/00FF00/FFFFFF?Text=Book2+Detail");
        book2.put("createdAt", LocalDateTime.now().minusHours(5).format(formatter) + "Z");
        book2.put("updatedAt", LocalDateTime.now().minusHours(2).format(formatter) + "Z");
        bookList.add(book2);

        Map<String, Object> book3 = new HashMap<>();
        book3.put("id", 3L);
        book3.put("title", "코딩은 즐거워");
        book3.put("author", "AI 도우미");
        book3.put("content", "이 메시지가 보인다면, 당신은 성공적으로 데이터를 받아오고 있습니다!\n즐거운 코딩 생활 되세요!");
        book3.put("coverImageUrl", null);
        book3.put("createdAt", LocalDateTime.now().minusMinutes(30).format(formatter) + "Z");
        book3.put("updatedAt", LocalDateTime.now().format(formatter) + "Z");
        bookList.add(book3);

        return bookList;
    }

    // GET /api/books (도서 목록 조회)
    @GetMapping("/books")
    public List<Map<String, Object>> getBooks() {
        System.out.println("백엔드: /api/books (GET) 호출됨, 다음 데이터를 반환합니다: " + mockBookDatabase);
        return mockBookDatabase;
    }

    // GET /api/books/{id} (특정 도서 조회)
    @GetMapping("/books/{id}")
    public ResponseEntity<Map<String, Object>> getBookById(@PathVariable Long id) {
        Optional<Map<String, Object>> bookOptional = mockBookDatabase.stream()
                .filter(book -> id.equals(book.get("id")))
                .findFirst();

        if (bookOptional.isPresent()) {
            System.out.println("백엔드: /api/books/" + id + " (GET) 호출됨, 다음 데이터를 반환합니다: " + bookOptional.get());
            return ResponseEntity.ok(bookOptional.get());
        } else {
            System.out.println("백엔드: /api/books/" + id + " (GET) 호출됨, ID " + id + "에 해당하는 도서를 찾을 수 없음");
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "ID " + id + "에 해당하는 도서를 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // POST /api/books (새 도서 등록 - 프론트엔드 테스트를 위해 간단히 추가)
    // 실제로는 BookCreateRequestDto 같은 DTO를 사용하는 것이 좋습니다.
    @PostMapping("/books")
    public ResponseEntity<Map<String, Object>> createBook(@RequestBody Map<String, Object> newBookData) {
        long newId = idCounter.incrementAndGet(); // 새 ID 생성
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        Map<String, Object> bookToCreate = new HashMap<>();
        bookToCreate.put("id", newId);
        bookToCreate.put("title", newBookData.getOrDefault("title", "제목 없음"));
        bookToCreate.put("author", newBookData.getOrDefault("author", "작가 미상"));
        bookToCreate.put("content", newBookData.getOrDefault("content", "내용 없음"));
        bookToCreate.put("coverImageUrl", newBookData.get("coverImageUrl")); // null일 수 있음
        bookToCreate.put("createdAt", LocalDateTime.now().format(formatter) + "Z");
        bookToCreate.put("updatedAt", LocalDateTime.now().format(formatter) + "Z");

        mockBookDatabase.add(bookToCreate); // 가짜 데이터베이스에 추가

        System.out.println("백엔드: /api/books (POST) 호출됨, 다음 도서가 생성되었습니다: " + bookToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookToCreate);
    }


    // --- 👇 특정 ID의 도서 정보를 수정하는 API 추가 👇 ---
    @PutMapping("/books/{id}")
    public ResponseEntity<Map<String, Object>> updateBook(@PathVariable Long id, @RequestBody Map<String, Object> updatedBookData) {
        Optional<Map<String, Object>> bookOptional = mockBookDatabase.stream()
                .filter(book -> id.equals(book.get("id")))
                .findFirst();

        if (bookOptional.isPresent()) {
            Map<String, Object> existingBook = bookOptional.get();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

            // 요청 본문에 있는 필드들로 기존 도서 정보를 업데이트합니다.
            if (updatedBookData.containsKey("title")) {
                existingBook.put("title", updatedBookData.get("title"));
            }
            if (updatedBookData.containsKey("author")) {
                existingBook.put("author", updatedBookData.get("author"));
            }
            if (updatedBookData.containsKey("content")) {
                existingBook.put("content", updatedBookData.get("content"));
            }
            // coverImageUrl도 업데이트 가능하도록 추가 (선택 사항)
            if (updatedBookData.containsKey("coverImageUrl")) {
                existingBook.put("coverImageUrl", updatedBookData.get("coverImageUrl"));
            }

            existingBook.put("updatedAt", LocalDateTime.now().format(formatter) + "Z"); // 수정 시각 업데이트

            System.out.println("백엔드: /api/books/" + id + " (PUT) 호출됨, 다음 데이터로 수정 완료: " + existingBook);
            return ResponseEntity.ok(existingBook); // 수정된 도서 정보와 함께 200 OK 응답
        } else {
            System.out.println("백엔드: /api/books/" + id + " (PUT) 호출됨, ID " + id + "에 해당하는 도서를 찾을 수 없어 수정 실패");
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "ID " + id + "에 해당하는 도서를 찾을 수 없어 수정할 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    // --- 👆 특정 ID의 도서 정보를 수정하는 API 추가 완료 👆 ---

    // (향후 AI 표지 생성, 도서 삭제 API 등을 여기에 추가할 수 있습니다.)
}