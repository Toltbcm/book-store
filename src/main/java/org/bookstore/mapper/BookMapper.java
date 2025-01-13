package org.bookstore.mapper;

import org.bookstore.config.MapperConfig;
import org.bookstore.dto.request.CreateBookRequestDto;
import org.bookstore.dto.response.BookResponseDto;
import org.bookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {

    BookResponseDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
