package com.myproject.bookcover_api.service;


// 서비스에서 추상화 시킨 기능들을 실제로 구현하는 클래스입니다.

import com.myproject.bookcover_api.dto.BookDTO;
import com.myproject.bookcover_api.entity.Book;
import com.myproject.bookcover_api.repository.BookReposittory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.DTD;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    // 실제 쿼리를 생성해줄 BookRepository를 불러옵니다.
    private final BookReposittory bookReposittory;

    @Override
    public BookDTO.getBook createBook(BookDTO.createBook book) {
        // 사용자에게 입력받은 book 객체의 정보를 반영해줍니다.

        Book book1 = new Book();
        book1.setTitle(book.getTitle());
        book1.setContent(book.getContent());
        book1.setAuthor(book.getAuthor());

        bookReposittory.save(book1);
        return getBookById(id);
    }

    @Override
    public List<Book> getBookList() {
        // db의 모든 도서 정보를 반환해줍니다.
        List<Book> BookList = bookReposittory.findAll();
        return BookList;
    }

    @Override
    public BookDTO.getBook getBookById(Long id) {

        Book book1 = new Book();

        book1 = bookReposittory.findById(id).orElseThrow(
                () -> new EntityNotFoundException("도서 정보를 찾을 수 없습니다.")
        );

        BookDTO.getBook response = new BookDTO.getBook();
        response.setId(book1.getId());
        response.setTitle(book1.getTitle());
        response.setContent(book1.getContent());
        response.setCoverImageUrl(book1.getCoverImageUrl());
        response.setCreatedAt(book1.getCreatedAt());
        response.setUpdatedAt(book1.getUpdateAt());

        return response;
    }

    @Override
    public BookDTO.getBook updateBook(Long id, BookDTO.updateBook book) {
        // 수정을 위해서는 해당 도서의 정보를 먼저 찾아야됩니다.
        Book book1 = bookReposittory.findById(id).orElseThrow(
                () -> new EntityNotFoundException("도서 정보를 찾을 수 없습니다.")
        );

        book1.setTitle(book.getTitle());
        book1.setContent(book.getContent());
        book1.setCoverImageUrl(book.getCoverImageUrl());
        book1.setUpdateAt(LocalDateTime.now());

        bookReposittory.save(book1);
        return getBookById(id);
    }

    @Override
    public void deleteBook(Long id) {
        bookReposittory.deleteById(id);
    }
}
