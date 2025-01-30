package org.bookstore.util;

import org.bookstore.dto.request.CreateCategoryRequestDto;
import org.bookstore.dto.request.UpdateCategoryRequestDto;
import org.bookstore.dto.response.CategoryResponseDto;
import org.bookstore.model.Category;

public class ModelsAndDtoMaker {

    public static Category makeCategoryWithoutId(String namePart) {
        Category category = new Category();
        category.setName("Cat " + namePart);
        category.setDescription("Description " + namePart);
        return category;
    }

    public static Category makeCategoryWithId(Long id, String namePart) {
        Category category = new Category();
        category.setId(id);
        category.setName("Cat " + namePart);
        category.setDescription("Description " + namePart);
        return category;
    }

    public static CreateCategoryRequestDto makeCategoryCreateRequestDto(String namePart) {
        return new CreateCategoryRequestDto("Cat " + namePart, "Description " + namePart);
    }

    public static UpdateCategoryRequestDto makeCategoryUpdateRequestDto(String namePart) {
        return new UpdateCategoryRequestDto("Cat " + namePart, "Description " + namePart);
    }

    public static CategoryResponseDto makeCategoryResponseDto(Long id, String namePart) {
        return new CategoryResponseDto(id, "Cat " + namePart, "Description " + namePart);
    }
}
