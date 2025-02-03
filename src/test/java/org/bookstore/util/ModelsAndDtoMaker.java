package org.bookstore.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.bookstore.dto.request.CreateBookRequestDto;
import org.bookstore.dto.request.CreateCategoryRequestDto;
import org.bookstore.dto.request.UpdateBookRequestDto;
import org.bookstore.dto.request.UpdateCategoryRequestDto;
import org.bookstore.dto.response.BookResponseDto;
import org.bookstore.dto.response.BookWithoutCategoriesResponseDto;
import org.bookstore.dto.response.CategoryResponseDto;
import org.bookstore.model.Book;
import org.bookstore.model.Category;

public class ModelsAndDtoMaker {

    public static Category makeCategoryWithoutId(String namePart) {
        Category category = new Category();
        category.setName("Cat " + namePart);
        category.setDescription("Description " + namePart);
        return category;
    }

    public static Category makeCategoryWithId(Long id, String namePart) {
        Category category = makeCategoryWithoutId(namePart);
        category.setId(id);
        return category;
    }

    public static CreateCategoryRequestDto makeCreateCategoryRequestDto(String namePart) {
        return new CreateCategoryRequestDto("Cat " + namePart, "Description " + namePart);
    }

    public static UpdateCategoryRequestDto makeUpdateCategoryRequestDto(String namePart) {
        return new UpdateCategoryRequestDto("Cat " + namePart, "Description " + namePart);
    }

    public static CategoryResponseDto makeCategoryResponseDto(Long id, String namePart) {
        return new CategoryResponseDto(id, "Cat " + namePart, "Description " + namePart);
    }

    public static Book makeBookWithoutId(
            String namePart, BigDecimal price, Set<Category> categories) {
        Book book = new Book();
        book.setTitle("Book title " + namePart);
        book.setAuthor("Author " + namePart);
        book.setIsbn((namePart + "000-000-000-00000").substring(0, 17));
        book.setDescription("Book description " + namePart);
        book.setPrice(price);
        book.setCoverImage("https://cover_image_" + namePart + ".jpg");
        book.setCategories(categories);
        return book;
    }

    public static Book makeBookWithId(
            Long id, String namePart, BigDecimal price, Set<Category> categories) {
        Book book = makeBookWithoutId(namePart, price, categories);
        book.setId(id);
        return book;
    }

    public static CreateBookRequestDto makeCreateBookRequestDto(
            String namePart, BigDecimal price, List<Long> categoryIds) {
        return new CreateBookRequestDto(
                "Book title " + namePart,
                "Author " + namePart,
                (namePart + "000-000-000-00000").substring(0, 17),
                price,
                "Book description " + namePart,
                "https://cover_image_" + namePart + ".jpg",
                categoryIds);
    }

    public static UpdateBookRequestDto makeUpdateBookRequestDto(
            String namePart, BigDecimal price, List<Long> categoryIds) {
        return new UpdateBookRequestDto(
                "Book title " + namePart,
                "Author " + namePart,
                (namePart + "000-000-000-00000").substring(0, 17),
                price,
                "Book description " + namePart,
                "https://cover_image_" + namePart + ".jpg",
                categoryIds);
    }

    public static BookResponseDto makeBookResponseDto(
            Long id, String namePart, BigDecimal price, List<Long> categoryIds) {
        return new BookResponseDto(
                id,
                "Book title " + namePart,
                "Author " + namePart,
                (namePart + "000-000-000-00000").substring(0, 17),
                price,
                "Book description " + namePart,
                "https://cover_image_" + namePart + ".jpg",
                categoryIds);
    }

    public static BookWithoutCategoriesResponseDto makeBookWithoutCategoriesResponseDto(
            Long id, String namePart, BigDecimal price) {
        return new BookWithoutCategoriesResponseDto(
                id,
                "Book title " + namePart,
                "Author " + namePart,
                (namePart + "000-000-000-00000").substring(0, 17),
                price,
                "Book description " + namePart,
                "https://cover_image_" + namePart + ".jpg"
        );
    }
}
