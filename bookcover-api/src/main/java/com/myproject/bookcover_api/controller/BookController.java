package com.myproject.bookcover_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api") // 이 컨트롤러의 모든 API는 /api 로 시작합니다.
public class BookController {

    // GET 요청이 /api/books 로 오면 이 메소드가 실행됩니다.
    @GetMapping("/books")
    public List<Map<String, Object>> getBooks() {
        List<Map<String, Object>> bookList = new ArrayList<>();

        // 날짜 포맷터 (YYYY-MM-DDTHH:mm:ssZ 형식과 유사하게 만듭니다)
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        // 가짜 도서 데이터 1
        Map<String, Object> book1 = new HashMap<>();
        book1.put("id", 1L); // Long 타입이므로 L 접미사
        book1.put("title", "AI가 만든 첫 번째 이야기 (백엔드 테스트용)");
        book1.put("author", "백엔드 봇");
        book1.put("content", "이것은 백엔드에서 보내주는 첫 번째 테스트용 도서 내용입니다. 프론트엔드에서 잘 보이나요?");
        book1.put("coverImageUrl", "https://via.placeholder.com/150/FF0000/FFFFFF?Text=Book1"); // 임시 이미지 URL
        book1.put("createdAt", LocalDateTime.now().minusDays(2).format(formatter) + "Z");
        book1.put("updatedAt", LocalDateTime.now().minusDays(1).format(formatter) + "Z");
        bookList.add(book1);

        // 가짜 도서 데이터 2
        Map<String, Object> book2 = new HashMap<>();
        book2.put("id", 2L);
        book2.put("title", "React와 Spring Boot의 만남");
        book2.put("author", "개발자 K");
        book2.put("content", "프론트엔드와 백엔드가 드디어 만났습니다! 신나는 테스트 시간!");
        book2.put("coverImageUrl", "https://via.placeholder.com/150/00FF00/FFFFFF?Text=Book2");
        book2.put("createdAt", LocalDateTime.now().minusHours(5).format(formatter) + "Z");
        book2.put("updatedAt", LocalDateTime.now().minusHours(2).format(formatter) + "Z");
        bookList.add(book2);

        // 가짜 도서 데이터 3
        Map<String, Object> book3 = new HashMap<>();
        book3.put("id", 3L);
        book3.put("title", "코딩은 즐거워");
        book3.put("author", "AI 도우미");
        book3.put("content", "이 메시지가 보인다면, 당신은 성공적으로 데이터를 받아오고 있습니다!");
        book3.put("coverImageUrl", null); // 표지 이미지가 없는 경우
        book3.put("createdAt", LocalDateTime.now().minusMinutes(30).format(formatter) + "Z");
        book3.put("updatedAt", LocalDateTime.now().format(formatter) + "Z");
        bookList.add(book3);

        System.out.println("백엔드: /api/books 호출됨, 다음 데이터를 반환합니다: " + bookList); // 서버 콘솔에 로그 출력

        return bookList; // 생성된 가짜 도서 목록을 반환
    }
}