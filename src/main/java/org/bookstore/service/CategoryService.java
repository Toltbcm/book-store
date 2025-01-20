package org.bookstore.service;

import java.util.List;
import org.bookstore.dto.request.CreateCategoryRequestDto;
import org.bookstore.dto.request.UpdateCategoryRequestDto;
import org.bookstore.dto.response.CategoryResponseDto;

public interface CategoryService {

    CategoryResponseDto save(CreateCategoryRequestDto requestDto);

    CategoryResponseDto getById(Long id);

    List<CategoryResponseDto> getAll();

    CategoryResponseDto update(Long id, UpdateCategoryRequestDto requestDto);

    void delete(Long id);
}
