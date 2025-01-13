package org.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bookstore.dto.BookDto;
import org.bookstore.dto.CreateBookDto;
import org.bookstore.dto.UpdateBookDto;
import org.bookstore.dto.search.BookSearchParameters;
import org.bookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@Tag(name = "Book", description = "Endpoints for book management")
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Get book by ID", description = "Endpoint for getting book by id")
    @GetMapping("/{id}")
    public BookDto getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @Operation(summary = "Get all books",
            description = "Endpoint for getting all books. Pageable, sortable")
    @GetMapping
    public Page<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @Operation(summary = "Create book", description = "Endpoint for creating book")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookDto create(@Valid @RequestBody CreateBookDto requestDto) {
        return bookService.save(requestDto);
    }

    @Operation(summary = "Update book", description = "Endpoint for updating book")
    @PutMapping("/{id}")
    public BookDto update(@PathVariable Long id, @RequestBody @Valid UpdateBookDto requestDto) {
        return bookService.update(id, requestDto);
    }

    @Operation(summary = "Delete book", description = "Endpoint for deleting book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @Operation(summary = "Search books", description = "Endpoint for searching books by parameters")
    @GetMapping("/search")
    public List<BookDto> search(BookSearchParameters searchParameters) {
        return bookService.search(searchParameters);
    }
}
