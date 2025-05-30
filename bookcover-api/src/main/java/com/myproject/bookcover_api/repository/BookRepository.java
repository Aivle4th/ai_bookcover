package com.myproject.bookcover_api.repository;

import com.myproject.bookcover_api.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends  JpaRepository<Book, Long> {

}
