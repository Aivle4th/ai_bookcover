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
    private String cover_image_url;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime update_at;

}
