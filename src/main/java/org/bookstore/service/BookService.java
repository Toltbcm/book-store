package org.bookstore.service;

import java.util.List;
import org.bookstore.dto.BookDto;
import org.bookstore.dto.CreateBookDto;

public interface BookService {

    BookDto save(CreateBookDto requestDto);

    BookDto findById(Long id);

    List<BookDto> findAll();
}
