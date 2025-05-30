package com.myproject.bookcover_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class BookController {

    private List<Map<String, Object>> mockBookDatabase = initializeMockBooks();
    private AtomicLong idCounter = new AtomicLong(mockBookDatabase.size());

    private List<Map<String, Object>> initializeMockBooks() {
        List<Map<String, Object>> bookList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        Map<String, Object> book1 = new HashMap<>();
        book1.put("id", 1L);
        book1.put("title", "AI가 만든 첫 번째 이야기");
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
        book3.put("title", "코딩은 즐거워 (AI 이야기)");
        book3.put("author", "AI 도우미");
        book3.put("content", "이 메시지가 보인다면, 당신은 성공적으로 데이터를 받아오고 있습니다!\n즐거운 코딩 생활 되세요!");
        book3.put("coverImageUrl", null);
        book3.put("createdAt", LocalDateTime.now().minusMinutes(30).format(formatter) + "Z");
        book3.put("updatedAt", LocalDateTime.now().format(formatter) + "Z");
        bookList.add(book3);
        return bookList;
    }

    @GetMapping("/books")
    public List<Map<String, Object>> getBooks(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        System.out.println("백엔드: /api/books (GET) 호출됨. 검색어(title): " + title + ", page: " + page + ", size: " + size);
        List<Map<String, Object>> filteredBooks = new ArrayList<>(mockBookDatabase);
        if (title != null && !title.isEmpty()) {
            String lowerCaseSearchTerm = title.toLowerCase();
            filteredBooks = mockBookDatabase.stream()
                    .filter(book -> {
                        String bookTitle = (String) book.get("title");
                        return bookTitle != null && bookTitle.toLowerCase().contains(lowerCaseSearchTerm);
                    })
                    .collect(Collectors.toList());
            System.out.println("백엔드: 제목 검색 결과 " + filteredBooks.size() + "건");
        }
        System.out.println("백엔드: 최종 반환 데이터: " + filteredBooks);
        return filteredBooks;
    }

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

    @PostMapping("/books")
    public ResponseEntity<Map<String, Object>> createBook(@RequestBody Map<String, Object> newBookData) {
        long newId = idCounter.incrementAndGet();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        Map<String, Object> bookToCreate = new HashMap<>();
        bookToCreate.put("id", newId);
        bookToCreate.put("title", newBookData.getOrDefault("title", "제목 없음"));
        bookToCreate.put("author", newBookData.getOrDefault("author", "작가 미상"));
        bookToCreate.put("content", newBookData.getOrDefault("content", "내용 없음"));
        bookToCreate.put("coverImageUrl", newBookData.get("coverImageUrl"));
        bookToCreate.put("createdAt", LocalDateTime.now().format(formatter) + "Z");
        bookToCreate.put("updatedAt", LocalDateTime.now().format(formatter) + "Z");
        mockBookDatabase.add(bookToCreate);
        System.out.println("백엔드: /api/books (POST) 호출됨, 다음 도서가 생성되었습니다: " + bookToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookToCreate);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Map<String, Object>> updateBook(@PathVariable Long id, @RequestBody Map<String, Object> updatedBookData) {
        Optional<Map<String, Object>> bookOptional = mockBookDatabase.stream()
                .filter(book -> id.equals(book.get("id")))
                .findFirst();
        if (bookOptional.isPresent()) {
            Map<String, Object> existingBook = bookOptional.get();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            if (updatedBookData.containsKey("title")) existingBook.put("title", updatedBookData.get("title"));
            if (updatedBookData.containsKey("author")) existingBook.put("author", updatedBookData.get("author"));
            if (updatedBookData.containsKey("content")) existingBook.put("content", updatedBookData.get("content"));
            if (updatedBookData.containsKey("coverImageUrl")) existingBook.put("coverImageUrl", updatedBookData.get("coverImageUrl"));
            existingBook.put("updatedAt", LocalDateTime.now().format(formatter) + "Z");
            System.out.println("백엔드: /api/books/" + id + " (PUT) 호출됨, 다음 데이터로 수정 완료: " + existingBook);
            return ResponseEntity.ok(existingBook);
        } else {
            System.out.println("백엔드: /api/books/" + id + " (PUT) 호출됨, ID " + id + "에 해당하는 도서를 찾을 수 없어 수정 실패");
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "ID " + id + "에 해당하는 도서를 찾을 수 없어 수정할 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Map<String, Object>> deleteBook(@PathVariable Long id) {
        boolean removed = mockBookDatabase.removeIf(book -> id.equals(book.get("id")));
        if (removed) {
            System.out.println("백엔드: /api/books/" + id + " (DELETE) 호출됨, 도서 삭제 완료");
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("message", "도서(ID: " + id + ")가 성공적으로 삭제되었습니다.");
            return ResponseEntity.ok(successResponse);
        } else {
            System.out.println("백엔드: /api/books/" + id + " (DELETE) 호출됨, ID " + id + "에 해당하는 도서를 찾을 수 없어 삭제 실패");
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "ID " + id + "에 해당하는 도서를 찾을 수 없어 삭제할 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // AI 표지 이미지 생성을 위한 목업 API
    @PostMapping("/books/{id}/generate-cover")
    public ResponseEntity<Map<String, Object>> generateCoverForBook(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> payload
    ) {
        Optional<Map<String, Object>> bookOptional = mockBookDatabase.stream()
                .filter(book -> id.equals(book.get("id")))
                .findFirst();

        if (bookOptional.isPresent()) {
            Map<String, Object> existingBook = bookOptional.get();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            String promptUsed = "기본 프롬프트 (책 제목: " + existingBook.get("title") + ")";
            if (payload != null && payload.containsKey("prompt") && !payload.get("prompt").isEmpty()) {
                promptUsed = payload.get("prompt");
            }
            String newCoverImageUrl = "https://via.placeholder.com/300x450/0000FF/FFFFFF?Text=AI+Cover+" + id + "+(P:" + promptUsed.substring(0, Math.min(promptUsed.length(), 10)) +")";
            existingBook.put("coverImageUrl", newCoverImageUrl);
            existingBook.put("updatedAt", LocalDateTime.now().format(formatter) + "Z");
            System.out.println("백엔드: /api/books/" + id + "/generate-cover (POST) 호출됨. 사용된 프롬프트: " + promptUsed);
            System.out.println("백엔드: 표지 이미지 URL이 다음으로 업데이트됨: " + newCoverImageUrl);
            return ResponseEntity.ok(existingBook);
        } else {
            System.out.println("백엔드: /api/books/" + id + "/generate-cover (POST) 호출됨, ID " + id + "에 해당하는 도서를 찾을 수 없음");
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "ID " + id + "에 해당하는 도서를 찾을 수 없어 표지를 생성할 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}