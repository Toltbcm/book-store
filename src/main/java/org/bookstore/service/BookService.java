package org.bookstore.service;

import java.util.List;
import org.bookstore.dto.BookDto;
import org.bookstore.dto.CreateBookDto;

public interface BookService {

    BookDto save(CreateBookDto dto);

    BookDto findById(Long id);

    List<BookDto> findAll();
}
