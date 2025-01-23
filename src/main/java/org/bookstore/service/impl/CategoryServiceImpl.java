package org.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.bookstore.dto.request.CreateCategoryRequestDto;
import org.bookstore.dto.request.UpdateCategoryRequestDto;
import org.bookstore.dto.response.CategoryResponseDto;
import org.bookstore.exception.EntityNotFoundException;
import org.bookstore.mapper.CategoryMapper;
import org.bookstore.model.Category;
import org.bookstore.repository.CategoryRepository;
import org.bookstore.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private static EntityNotFoundException newEntityNotFoundForCategory(Long id) {
        return new EntityNotFoundException("Can't find category by id: " + id);
    }

    @Override
    public CategoryResponseDto save(CreateCategoryRequestDto requestDto) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toModel(requestDto)));
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        return categoryMapper.toDto(getCategoryById(id));
    }

    @Override
    public Page<CategoryResponseDto> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryMapper::toDto);
    }

    @Override
    public CategoryResponseDto update(Long id, UpdateCategoryRequestDto requestDto) {
        Category category = getCategoryById(id);
        return categoryMapper.toDto(categoryRepository.save(
                categoryMapper.updateModel(category, requestDto)));
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw newEntityNotFoundForCategory(id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> newEntityNotFoundForCategory(id));
    }
}
