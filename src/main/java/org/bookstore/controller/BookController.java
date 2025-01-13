package org.bookstore.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bookstore.dto.request.CreateBookRequestDto;
import org.bookstore.dto.request.UpdateBookRequestDto;
import org.bookstore.dto.request.search.BookSearchParametersRequestDto;
import org.bookstore.dto.response.BookResponseDto;
import org.bookstore.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public BookResponseDto getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping
    public List<BookResponseDto> getAll() {
        return bookService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookResponseDto create(@Valid @RequestBody CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @PutMapping("/{id}")
    public BookResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateBookRequestDto requestDto) {
        return bookService.update(id, requestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping("/search")
    public List<BookResponseDto> search(BookSearchParametersRequestDto searchParameters) {
        return bookService.search(searchParameters);
    }
}
