package org.bookstore.mapper;

import org.bookstore.config.MapperConfig;
import org.bookstore.dto.request.UserRegistrationRequestDto;
import org.bookstore.dto.response.UserResponseDto;
import org.bookstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    UserResponseDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    User toModel(UserRegistrationRequestDto requestDto);
}
