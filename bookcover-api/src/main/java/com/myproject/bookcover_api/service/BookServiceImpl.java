package com.myproject.bookcover_api.service;

import com.myproject.bookcover_api.dto.BookDto;
import com.myproject.bookcover_api.entity.Book;
import com.myproject.bookcover_api.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.myproject.bookcover_api.dto.BookDto.BookCreate.toBookEntity;
import static com.myproject.bookcover_api.dto.BookDto.BookResponse.toBookResponse;
import static com.myproject.bookcover_api.dto.BookDto.toBookDto;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    @Override
    public BookDto.BookResponse createBook(BookDto.BookCreate dto) {
        Book saved = bookRepository.save(toBookEntity(dto));
        return toBookResponse(saved);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return toBookDto(book);
    }

    @Override
    public List<BookDto.BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDto.BookResponse::toBookResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDto updateBook(Long id, BookDto.BookUpdate dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setContent(dto.getContent());
        return toBookDto(book);
    }

    @Override
    @Transactional
    public BookDto updateBookImg(Long id, BookDto.BookUpdateImgUrl dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        book.setCoverImageUrl(dto.getCoverImageUrl());
        return toBookDto(book);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}  // 저장저장
