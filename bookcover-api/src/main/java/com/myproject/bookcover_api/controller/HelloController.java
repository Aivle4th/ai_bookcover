package com.myproject.bookcover_api.controller; // 본인 패키지명으로 수정

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String sayHello() {
        return "안녕하세요! Spring Boot 백엔드에서 보내는 메시지입니다! 🎉";
    }
}