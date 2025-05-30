package com.myproject.bookcover_api.service;

import com.myproject.bookcover_api.dto.BookDto;
import com.myproject.bookcover_api.entity.Book;
import com.myproject.bookcover_api.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    private BookDto.Response toResponse(Book book) {
        BookDto.Response response = new BookDto.Response();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setContent(book.getContent());
        response.setCover_image_url(book.getCover_image_url());
        response.setCreated_at(book.getCreated_at().toString());
        return response;
    }
    private Book toEntity(BookDto.Create dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setContent(dto.getContent());
        return book;
    }

    private BookDto toDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setContent(book.getContent());
        dto.setCover_image_url(book.getCover_image_url());
        dto.setCreated_at(book.getCreated_at());
        dto.setUpdate_at(book.getUpdate_at());
        return dto;
    }

    @Override
    public BookDto.Response createBook(BookDto.Create dto) {
        Book saved = bookRepository.save(toEntity(dto));
        return toResponse(saved);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return toDto(book);
    }

    @Override
    public List<BookDto.Response> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDto updateBook(Long id, BookDto.Update dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setContent(dto.getContent());
//        LocalDateTime now = LocalDateTime.now();
//        book.setUpdate_at(now);
        bookRepository.save(book);
        return toDto(book);
    }

    @Override
    @Transactional
    public BookDto updateBookImg(Long id, BookDto.UpdateImgUrl dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        book.setCover_image_url(dto.getCover_image_url());
//        LocalDateTime now = LocalDateTime.now();
//        book.setUpdate_at(now);
        bookRepository.save(book);
        return toDto(book);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}
