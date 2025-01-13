package org.bookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bookstore.dto.BookDto;
import org.bookstore.dto.CreateBookDto;
import org.bookstore.dto.UpdateBookDto;
import org.bookstore.dto.search.BookSearchParameters;
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
    public BookDto save(CreateBookDto requestDto) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(requestDto)));
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(getBook(id));
    }

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toDto);
    }

    @Override
    public BookDto update(Long id, UpdateBookDto requestDto) {
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
    public List<BookDto> search(BookSearchParameters searchParameters) {
        return bookRepository.findAll(bookSpecificationBuilder.build(searchParameters))
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    private Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book with id: " + id));
    }
}
