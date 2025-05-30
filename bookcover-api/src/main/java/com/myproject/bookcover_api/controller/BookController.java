package com.myproject.bookcover_api.controller;

import org.springframework.http.HttpStatus; // HttpStatus 임포트
import org.springframework.http.ResponseEntity; // ResponseEntity 임포트
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // PathVariable 임포트
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional; // Optional 임포트

@RestController
@RequestMapping("/api")
public class BookController {

    // 가짜 데이터베이스 역할을 할 리스트 (getBooks 메소드에서 사용한 리스트와 동일하게 사용)
    private List<Map<String, Object>> mockBookDatabase = initializeMockBooks();

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

    @GetMapping("/books")
    public List<Map<String, Object>> getBooks() {
        System.out.println("백엔드: /api/books 호출됨, 다음 데이터를 반환합니다: " + mockBookDatabase);
        return mockBookDatabase;
    }

    // --- 👇 특정 ID의 도서 정보를 반환하는 API 추가 👇 ---
    @GetMapping("/books/{id}")
    public ResponseEntity<Map<String, Object>> getBookById(@PathVariable Long id) {
        // mockBookDatabase에서 id에 해당하는 책을 찾습니다.
        Optional<Map<String, Object>> bookOptional = mockBookDatabase.stream()
                .filter(book -> id.equals(book.get("id")))
                .findFirst();

        if (bookOptional.isPresent()) {
            System.out.println("백엔드: /api/books/" + id + " 호출됨, 다음 데이터를 반환합니다: " + bookOptional.get());
            return ResponseEntity.ok(bookOptional.get()); // 찾았으면 200 OK와 함께 도서 데이터 반환
        } else {
            System.out.println("백엔드: /api/books/" + id + " 호출됨, ID " + id + "에 해당하는 도서를 찾을 수 없음");
            // 못 찾았으면 404 Not Found 응답 (본문은 비우거나 간단한 오류 메시지 포함 가능)
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "ID " + id + "에 해당하는 도서를 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    // --- 👆 특정 ID의 도서 정보를 반환하는 API 추가 완료 👆 ---
}