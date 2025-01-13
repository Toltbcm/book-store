package org.bookstore.service;

import java.util.List;
import org.bookstore.dto.request.CreateBookRequestDto;
import org.bookstore.dto.request.UpdateBookRequestDto;
import org.bookstore.dto.request.search.BookSearchParametersRequestDto;
import org.bookstore.dto.response.BookResponseDto;

public interface BookService {

    BookResponseDto save(CreateBookRequestDto requestDto);

    BookResponseDto findById(Long id);

    List<BookResponseDto> findAll();

    BookResponseDto update(Long id, UpdateBookRequestDto requestDto);

    void delete(Long id);

    List<BookResponseDto> search(BookSearchParametersRequestDto searchParametersDto);
}
