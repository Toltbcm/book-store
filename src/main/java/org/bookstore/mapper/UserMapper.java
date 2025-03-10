package org.bookstore.mapper;

import java.util.List;
import org.bookstore.config.MapperConfig;
import org.bookstore.dto.request.UserRegistrationRequestDto;
import org.bookstore.dto.response.UserResponseDto;
import org.bookstore.model.Role;
import org.bookstore.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    UserResponseDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    User toModelWithRoles(UserRegistrationRequestDto requestDto,
                          @Context List<Role> roles,
                          @Context PasswordEncoder passwordEncoder);

    @AfterMapping
    default void setRoles(@MappingTarget User user, @Context List<Role> roles) {
        user.getRoles().addAll(roles);
    }

    @Named("encodePassword")
    default String encodePassword(String password, @Context PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(password);
    }
}
