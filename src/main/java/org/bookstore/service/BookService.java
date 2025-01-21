package org.bookstore.service;

import org.bookstore.dto.request.CreateBookRequestDto;
import org.bookstore.dto.request.UpdateBookRequestDto;
import org.bookstore.dto.request.search.BookSearchParametersRequestDto;
import org.bookstore.dto.response.BookResponseDto;
import org.bookstore.dto.response.BookWithoutCategoriesResponseDto;
import org.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookResponseDto save(CreateBookRequestDto requestDto);

    BookResponseDto getById(Long id);

    Page<BookWithoutCategoriesResponseDto> getAll(Pageable pageable);

    BookResponseDto update(Long id, UpdateBookRequestDto requestDto);

    void delete(Long id);

    Page<BookWithoutCategoriesResponseDto> search(
            BookSearchParametersRequestDto searchParametersDto, Pageable pageable);

    Page<BookWithoutCategoriesResponseDto> getAllByCategoryId(Long id, Pageable pageable);

    Book getBook(Long id);
}
