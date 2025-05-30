package com.myproject.bookcover_api.service;

import com.myproject.bookcover_api.dto.BookReqDto;
import com.myproject.bookcover_api.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    // 책 등록
    Book saveBook(Book book);

    // 전체 책 조회
    List<Book> getBooks();

    // ID로 책 조회
    Book getBookById(Long id);

    // 책 수정
    Book updateBook(Long id, BookReqDto updatedBook);

    // 책 삭제
    void deleteBook(Long id);
}
