package org.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bookstore.dto.request.CreateCategoryRequestDto;
import org.bookstore.dto.request.UpdateCategoryRequestDto;
import org.bookstore.dto.response.BookWithoutCategoriesResponseDto;
import org.bookstore.dto.response.CategoryResponseDto;
import org.bookstore.service.BookService;
import org.bookstore.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category", description = "Endpoints for category management")
@RequiredArgsConstructor
@RestController
@RequestMapping("categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final BookService bookService;

    @Operation(summary = "Get category by ID", description = "Endpoint for getting category by ID")
    @GetMapping("/{id}")
    public CategoryResponseDto getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @Operation(summary = "Get all categories",
            description = "Endpoint for getting all categories. Pageable, sortable")
    @GetMapping
    public Page<CategoryResponseDto> getAll(@RequestParam Pageable pageable) {
        return categoryService.getAll(pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create category", description = "Endpoint for creating category")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponseDto create(@Valid @RequestBody CreateCategoryRequestDto requestDto) {
        return categoryService.save(requestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update category", description = "Endpoint for updating category")
    @PutMapping("/{id}")
    public CategoryResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCategoryRequestDto requestDto) {
        return categoryService.update(id, requestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete category by ID",
            description = "Endpoint for deleting category by ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @Operation(summary = "Get books by category ID",
            description = "Endpoint for getting books by category ID")
    @GetMapping("/{id}/books")
    public Page<BookWithoutCategoriesResponseDto> getBooksByCategory(
            @PathVariable Long id, @RequestParam Pageable pageable) {
        return bookService.getAllByCategoryId(id, pageable);
    }
}
