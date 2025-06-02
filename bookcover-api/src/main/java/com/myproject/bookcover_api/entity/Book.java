package com.myproject.bookcover_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title",nullable = false, length = 200)
    private String title;

    @Column(name="author",nullable = false, length = 100)
    private String author;

    @Column(name="content",nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "cover_image_url",columnDefinition = "TEXT")
    private String coverImageUrl;

    @CreationTimestamp // 엔터티 생성 및 수정 시 자동으로 현재 시간 기록
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
