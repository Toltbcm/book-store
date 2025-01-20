package org.bookstore.service;

import java.util.List;
import org.bookstore.dto.request.CreateBookRequestDto;
import org.bookstore.dto.request.UpdateBookRequestDto;
import org.bookstore.dto.request.search.BookSearchParametersRequestDto;
import org.bookstore.dto.response.BookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookResponseDto save(CreateBookRequestDto requestDto);

    BookResponseDto getById(Long id);

    Page<BookResponseDto> getAll(Pageable pageable);

    BookResponseDto update(Long id, UpdateBookRequestDto requestDto);

    void delete(Long id);

    List<BookResponseDto> search(BookSearchParametersRequestDto searchParametersDto);
}
