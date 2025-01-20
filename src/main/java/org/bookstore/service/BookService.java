package org.bookstore.service;

import org.bookstore.dto.request.CreateBookRequestDto;
import org.bookstore.dto.request.UpdateBookRequestDto;
import org.bookstore.dto.request.search.BookSearchParametersRequestDto;
import org.bookstore.dto.response.BookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookResponseDto save(CreateBookRequestDto requestDto);

    BookResponseDto findById(Long id);

    Page<BookResponseDto> findAll(Pageable pageable);

    BookResponseDto update(Long id, UpdateBookRequestDto requestDto);

    void delete(Long id);

    Page<BookResponseDto> search(
            BookSearchParametersRequestDto searchParametersDto, Pageable pageable);

    Page<BookResponseDto> getAllByCategoryId(Long id, Pageable pageable);
}
