package org.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.bookstore.dto.request.CreateCategoryRequestDto;
import org.bookstore.dto.request.UpdateCategoryRequestDto;
import org.bookstore.dto.response.CategoryResponseDto;
import org.bookstore.exception.EntityNotFoundException;
import org.bookstore.mapper.CategoryMapper;
import org.bookstore.model.Category;
import org.bookstore.repository.category.CategoryRepository;
import org.bookstore.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDto save(CreateCategoryRequestDto requestDto) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toModel(requestDto)));
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        return categoryMapper.toDto(getCategory(id));
    }

    @Override
    public Page<CategoryResponseDto> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryMapper::toDto);
    }

    @Override
    public CategoryResponseDto update(Long id, UpdateCategoryRequestDto requestDto) {
        Category category = getCategory(id);
        return categoryMapper.toDto(categoryRepository.save(
                categoryMapper.updateModel(category, requestDto)));
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find category with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category with id: " + id));
    }
}
