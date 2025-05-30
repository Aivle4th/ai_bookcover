package com.myproject.bookcover_api.service;

import com.myproject.bookcover_api.dto.BookDTO;
import com.myproject.bookcover_api.entity.Book;

import java.util.List;

// 기능을 추상화 해놓은 서비스 인터페이스입니다. 실제 기능 구현은 아래 ServiceImpl에서 하겠습니다.

public interface BookService {

    //도서 등록하는 기능이에요
    BookDTO.getBook createBook(BookDTO.createBook book);

    //도서를 목록으로 조회하는 기능이에요, 모든 Book 객체를 list에 담에서 한번에 보내줄거에요
    List<Book> getBookList();

    //특정 도서를 조회하는 기능에요. id값을 받아서 그에 맞는 데이터를 가져올거에요.
    BookDTO.getBook getBookById(Long id);

    //도서의 정보를 수정하는 기능이에요, 수정할 도서의 id와 상세한 정보가 필요하니 매개변수로 받아야해요.
    BookDTO.getBook updateBook(Long id, BookDTO.updateBook book);

    //도서를 삭제하는 기능이에요. 삭제의 경우 그냥 서버 내에서 없애기만 하면 되니 반환값은 따로 없어요
    void deleteBook(Long id);






}
