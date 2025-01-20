package org.bookstore.mapper;

import org.bookstore.config.MapperConfig;
import org.bookstore.dto.request.CreateBookRequestDto;
import org.bookstore.dto.request.UpdateBookRequestDto;
import org.bookstore.dto.response.BookResponseDto;
import org.bookstore.dto.response.BookWithoutCategoriesResponseDto;
import org.bookstore.model.Book;
import org.bookstore.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class, uses = MapperUtil.class,
        collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface BookMapper {

    @Mapping(target = "categoryIds", ignore = true)
    BookResponseDto toDto(Book book);

    BookWithoutCategoriesResponseDto toDtoWithoutCategories(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "categories", source = "categoryIds", qualifiedByName = "setCategories")
    Book toModel(CreateBookRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "categories", source = "categoryIds", qualifiedByName = "setCategories")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Book updateModel(@MappingTarget Book book, UpdateBookRequestDto requestDto);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookResponseDto responseDto, Book book) {
        responseDto.categoryIds().addAll(book.getCategories().stream()
                .map(Category::getId).toList());
    }
}
