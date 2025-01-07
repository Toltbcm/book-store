package org.bookstore.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bookstore.dto.BookDto;
import org.bookstore.dto.CreateBookDto;
import org.bookstore.dto.UpdateBookDto;
import org.bookstore.dto.UpdateBookDto;
import org.bookstore.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public BookDto getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookDto create(@Valid @RequestBody CreateBookDto requestDto) {
        return bookService.save(requestDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> update(
            @PathVariable Long id, @RequestBody @Valid UpdateBookDto requestDto) {
        return ResponseEntity.ok(bookService.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
