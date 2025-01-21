package org.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.bookstore.dto.request.CreateBookRequestDto;
import org.bookstore.dto.request.UpdateBookRequestDto;
import org.bookstore.dto.request.search.BookSearchParametersRequestDto;
import org.bookstore.dto.response.BookResponseDto;
import org.bookstore.dto.response.BookWithoutCategoriesResponseDto;
import org.bookstore.exception.EntityNotFoundException;
import org.bookstore.mapper.BookMapper;
import org.bookstore.model.Book;
import org.bookstore.repository.book.BookRepository;
import org.bookstore.repository.book.specificaton.BookSpecificationBuilder;
import org.bookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    private static String notFoundBookMessage(Long id) {
        return String.format("Can't find book with id: %d", id);
    }

    @Override
    public BookResponseDto save(CreateBookRequestDto requestDto) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(requestDto)));
    }

    @Override
    public BookResponseDto getById(Long id) {
        return bookMapper.toDto(getBookWithCategoryById(id));
    }

    @Override
    public Page<BookWithoutCategoriesResponseDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toDtoWithoutCategories);
    }

    @Override
    public BookResponseDto update(Long id, UpdateBookRequestDto requestDto) {
        Book book = getBookById(id);
        return bookMapper.toDto(bookRepository.save(bookMapper.updateModel(book, requestDto)));
    }

    @Override
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException(notFoundBookMessage(id));
        }
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookWithoutCategoriesResponseDto> search(
            BookSearchParametersRequestDto searchParametersDto, Pageable pageable) {
        return bookRepository.findAll(bookSpecificationBuilder.build(searchParametersDto), pageable)
                .map(bookMapper::toDtoWithoutCategories);
    }

    @Override
    public Page<BookWithoutCategoriesResponseDto> getAllByCategoryId(Long id, Pageable pageable) {
        return bookRepository.getAllByCategoryId(id, pageable)
                .map(bookMapper::toDtoWithoutCategories);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(notFoundBookMessage(id)));
    }

    private Book getBookWithCategoryById(Long id) {
        return bookRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new EntityNotFoundException(notFoundBookMessage(id)));
    }
}
