package org.bookstore.service.impl;

import static org.bookstore.util.Constant.CORRECT_ID;
import static org.bookstore.util.Constant.WRONG_ID;
import static org.bookstore.util.ModelsAndDtoMaker.makeCategoryResponseDto;
import static org.bookstore.util.ModelsAndDtoMaker.makeCategoryWithId;
import static org.bookstore.util.ModelsAndDtoMaker.makeCategoryWithoutId;
import static org.bookstore.util.ModelsAndDtoMaker.makeCreateCategoryRequestDto;
import static org.bookstore.util.ModelsAndDtoMaker.makeUpdateCategoryRequestDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.bookstore.dto.request.CreateCategoryRequestDto;
import org.bookstore.dto.request.UpdateCategoryRequestDto;
import org.bookstore.dto.response.CategoryResponseDto;
import org.bookstore.exception.EntityNotFoundException;
import org.bookstore.mapper.CategoryMapper;
import org.bookstore.model.Category;
import org.bookstore.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTests {

    @Spy
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Nested
    @DisplayName("Tests for `save`:")
    class SaveTests {

        @Test
        @DisplayName("should save category and return CategoryResponseDto")
        void createCategoryRequestDto_SavesCategoryAndReturnsCategoryRequestDto() {
            String namePart = "1";
            CreateCategoryRequestDto createCategoryRequestDto =
                    makeCreateCategoryRequestDto(namePart);
            Category categoryWithoutId = makeCategoryWithoutId(namePart);
            Category categoryWithId = makeCategoryWithId(CORRECT_ID, namePart);
            CategoryResponseDto categoryResponseDto = makeCategoryResponseDto(CORRECT_ID, namePart);

            when(categoryMapper.toModel(createCategoryRequestDto)).thenReturn(categoryWithoutId);
            when(categoryRepository.save(categoryWithoutId)).thenReturn(categoryWithId);
            when(categoryMapper.toDto(categoryWithId)).thenReturn(categoryResponseDto);

            assertEquals(categoryResponseDto, categoryService.save(createCategoryRequestDto));
            verify(categoryMapper).toModel(createCategoryRequestDto);
            verify(categoryRepository).save(categoryWithoutId);
            verify(categoryMapper).toDto(categoryWithId);
        }
    }

    @Nested
    @DisplayName("Tests for `getById`:")
    class GetByIdTests {

        @Test
        @DisplayName("should find and return CategoryResponseDto by category id=1")
        void categoryIdIsCorrect_ReturnsCategoryResponseDto() {
            String namePart = "1";
            Category categoryWithId = makeCategoryWithId(CORRECT_ID, namePart);
            CategoryResponseDto categoryResponseDto = makeCategoryResponseDto(CORRECT_ID, namePart);

            doReturn(categoryWithId).when(categoryService).getCategoryById(CORRECT_ID);
            when(categoryMapper.toDto(categoryWithId)).thenReturn(categoryResponseDto);

            assertEquals(categoryResponseDto, categoryService.getById(CORRECT_ID));
            verify(categoryService).getCategoryById(CORRECT_ID);
            verify(categoryMapper).toDto(categoryWithId);
        }

        @Test
        @DisplayName("should throw EntityNotFoundException for non-existent category")
        void categoryIdIsWrong_ThrowsEntityNotFoundException() {
            doThrow(EntityNotFoundException.class).when(categoryService)
                    .getCategoryById(WRONG_ID);

            assertThrows(EntityNotFoundException.class,
                    () -> categoryService.getById(WRONG_ID));
            verify(categoryService).getCategoryById(WRONG_ID);
            verify(categoryMapper, never()).toDto(any());
        }
    }

    @Nested
    @DisplayName("Tests fo `getAll`:")
    class GetAllTests {

        @Test
        @DisplayName("should return page with three CategoryResponseDtos")
        void pageable_returnsPageWithThreeCategoryResponseDtos() {
            Category category1 = makeCategoryWithId(1L, "1");
            Category category2 = makeCategoryWithId(2L, "2");
            Category category3 = makeCategoryWithId(3L, "3");
            CategoryResponseDto categoryResponseDto1 = makeCategoryResponseDto(1L, "1");
            CategoryResponseDto categoryResponseDto2 = makeCategoryResponseDto(2L, "2");
            CategoryResponseDto categoryResponseDto3 = makeCategoryResponseDto(3L, "3");
            Pageable pageable = PageRequest.of(0, 20);

            Page<Category> categories = new PageImpl<>(
                    List.of(category1, category2, category3), pageable, 3);

            when(categoryRepository.findAll(pageable)).thenReturn(categories);
            when(categoryMapper.toDto(category1)).thenReturn(categoryResponseDto1);
            when(categoryMapper.toDto(category2)).thenReturn(categoryResponseDto2);
            when(categoryMapper.toDto(category3)).thenReturn(categoryResponseDto3);

            Page<CategoryResponseDto> expected = new PageImpl<>(
                    List.of(categoryResponseDto1, categoryResponseDto2, categoryResponseDto3),
                    pageable, 3);

            assertEquals(expected, categoryService.getAll(pageable));
            verify(categoryRepository).findAll(pageable);
            verify(categoryMapper, times(3)).toDto(any());
        }
    }

    @Nested
    @DisplayName("Tests for `update`:")
    class UpdateTests {

        @Test
        @DisplayName("should update category with id=1 and return CategoryResponseDto")
        void categoryIdIsCorrect_UpdatesCategoryAndReturnsCategoryRequestDto() {
            String namePart = "1";
            String updatedNamePart = "1 updated";
            Category category = makeCategoryWithId(CORRECT_ID, namePart);
            Category categoryUpdated = makeCategoryWithId(CORRECT_ID, updatedNamePart);
            UpdateCategoryRequestDto updateCategoryRequestDto =
                    makeUpdateCategoryRequestDto(updatedNamePart);
            CategoryResponseDto categoryResponseDto = makeCategoryResponseDto(CORRECT_ID, updatedNamePart);

            doReturn(category).when(categoryService).getCategoryById(CORRECT_ID);
            when(categoryMapper.updateModel(category, updateCategoryRequestDto))
                    .thenReturn(categoryUpdated);
            when(categoryRepository.save(categoryUpdated)).thenReturn(categoryUpdated);
            when(categoryMapper.toDto(categoryUpdated)).thenReturn(categoryResponseDto);

            assertEquals(categoryResponseDto, categoryService.update(CORRECT_ID, updateCategoryRequestDto));
            verify(categoryService).getCategoryById(CORRECT_ID);
            verify(categoryMapper).updateModel(category, updateCategoryRequestDto);
            verify(categoryRepository).save(categoryUpdated);
            verify(categoryMapper).toDto(categoryUpdated);
        }

        @Test
        @DisplayName("should throw EntityNotFoundException for non-existent category")
        void categoryIdIsWrong_ThrowsEntityNotFoundException() {
            Long nonExistentCategoryId = 77L;
            String namePart = "1";
            UpdateCategoryRequestDto updateCategoryRequestDto =
                    makeUpdateCategoryRequestDto(namePart);

            doThrow(EntityNotFoundException.class).when(categoryService)
                    .getCategoryById(nonExistentCategoryId);

            assertThrows(EntityNotFoundException.class,
                    () -> categoryService.update(nonExistentCategoryId, updateCategoryRequestDto));
            verify(categoryService).getCategoryById(nonExistentCategoryId);
            verify(categoryRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Tests for `delete`:")
    class DeleteTests {

        @Test
        @DisplayName("should delete category by id=1")
        void categoryIdIsCorrect_RemovesCategory() {
            Long id = 1L;

            when(categoryRepository.existsById(id)).thenReturn(true);

            categoryService.delete(id);

            verify(categoryRepository).existsById(id);
            verify(categoryRepository).deleteById(id);
        }

        @Test
        @DisplayName("should throw EntityNotFoundException for non-existent category")
        void categoryIdIsWrong_ThrowsEntityNotFoundException() {
            Long nonExistentCategoryId = 77L;

            when(categoryRepository.existsById(nonExistentCategoryId)).thenReturn(false);

            assertThrows(EntityNotFoundException.class,
                    () -> categoryService.delete(nonExistentCategoryId));
            verify(categoryRepository).existsById(nonExistentCategoryId);
            verify(categoryRepository, never()).deleteById(any());
        }
    }

    @Nested
    @DisplayName("Tests for `getCategoryById`")
    class GetCategoryByIdTests {

        @Test
        @DisplayName("should find and return category by id=1")
        void categoryIdIsCorrect_ReturnsCategory() {
            Long id = 1L;
            String namePart = "1";
            Category category = makeCategoryWithId(id, namePart);

            when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

            assertEquals(category, categoryService.getCategoryById(id));
            verify(categoryRepository).findById(id);
        }

        @Test
        @DisplayName("should throw EntityNotFoundException for non-existent category")
        void categoryIdIsWrong_ThrowsEntityNotFoundException() {
            Long nonExistentCategoryId = 77L;

            when(categoryRepository.findById(nonExistentCategoryId))
                    .thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class,
                    () -> categoryService.getCategoryById(nonExistentCategoryId));
            verify(categoryRepository).findById(nonExistentCategoryId);
        }
    }
}
