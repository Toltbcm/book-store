package org.bookstore.service;

import java.util.List;
import org.bookstore.dto.request.CreateCategoryRequestDto;
import org.bookstore.dto.request.UpdateCategoryRequestDto;
import org.bookstore.dto.response.CategoryResponseDto;

public interface CategoryService {

    List<CategoryResponseDto> findAll();

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CreateCategoryRequestDto requestDto);

    CategoryResponseDto update(Long id, UpdateCategoryRequestDto requestDto);

    void deleteById(Long id);
}
