package com.myproject.bookcover_api.service;


import com.myproject.bookcover_api.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto.Response createBook(BookDto.Create dto);
    BookDto getBookById(Long id);
    List<BookDto.Response> getAllBooks();
    BookDto updateBook(Long id, BookDto.Update dto);
    void deleteBook(Long id);
    BookDto updateBookImg(Long id, BookDto.UpdateImgUrl dto);
}
