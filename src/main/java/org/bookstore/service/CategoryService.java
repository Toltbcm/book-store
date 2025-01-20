package org.bookstore.service;

import org.bookstore.dto.request.CreateCategoryRequestDto;
import org.bookstore.dto.request.UpdateCategoryRequestDto;
import org.bookstore.dto.response.CategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryResponseDto save(CreateCategoryRequestDto requestDto);

    CategoryResponseDto getById(Long id);

    Page<CategoryResponseDto> getAll(Pageable pageable);

    CategoryResponseDto update(Long id, UpdateCategoryRequestDto requestDto);

    void delete(Long id);
}
