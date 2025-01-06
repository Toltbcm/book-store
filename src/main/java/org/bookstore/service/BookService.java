package org.bookstore.service;

import java.util.List;
import org.bookstore.dto.BookDto;
import org.bookstore.dto.CreateBookDto;
import org.bookstore.dto.UpdateBookDto;
import org.bookstore.dto.search.BookSearchParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookDto save(CreateBookDto requestDto);

    BookDto findById(Long id);

    Page<BookDto> findAll(Pageable pageable);

    BookDto update(Long id, UpdateBookDto requestDto);

    void delete(Long id);

    List<BookDto> search(BookSearchParameters searchParameters);
}
