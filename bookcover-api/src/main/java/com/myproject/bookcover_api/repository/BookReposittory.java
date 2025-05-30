package com.myproject.bookcover_api.repository;

import com.myproject.bookcover_api.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReposittory extends JpaRepository<Book, Long> {
}
