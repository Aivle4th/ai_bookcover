package com.myproject.bookcover_api.service;


import com.myproject.bookcover_api.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto.BookResponse createBook(BookDto.BookCreate dto);
    BookDto getBookById(Long id);
    List<BookDto.BookResponse> getAllBooks();
    BookDto updateBook(Long id, BookDto.BookUpdate dto);
    void deleteBook(Long id);
    BookDto updateBookImg(Long id, BookDto.BookUpdateImgUrl dto);
}
