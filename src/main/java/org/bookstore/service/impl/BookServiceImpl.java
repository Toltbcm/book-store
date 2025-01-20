package org.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.bookstore.dto.request.CreateBookRequestDto;
import org.bookstore.dto.request.UpdateBookRequestDto;
import org.bookstore.dto.request.search.BookSearchParametersRequestDto;
import org.bookstore.dto.response.BookResponseDto;
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

    @Override
    public BookResponseDto save(CreateBookRequestDto requestDto) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(requestDto)));
    }

    @Override
    public BookResponseDto findById(Long id) {
        return bookMapper.toDto(getBook(id));
    }

    @Override
    public Page<BookResponseDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toDto);
    }

    @Override
    public BookResponseDto update(Long id, UpdateBookRequestDto requestDto) {
        Book book = getBook(id);
        return bookMapper.toDto(bookRepository.save(bookMapper.updateModel(book, requestDto)));
    }

    @Override
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find book with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookResponseDto> search(
            BookSearchParametersRequestDto searchParametersDto, Pageable pageable) {
        return bookRepository.findAll(bookSpecificationBuilder.build(searchParametersDto), pageable)
                .map(bookMapper::toDto);
    }

    @Override
    public Page<BookResponseDto> getAllByCategoryId(Long id, Pageable pageable) {
        return bookRepository.findAllByCategoryId(id, pageable).map(bookMapper::toDto);
    }


    private Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book with id: " + id));
    }
}
