package org.bookstore.mapper;

import org.bookstore.config.MapperConfig;
import org.bookstore.dto.request.UserRegistrationRequestDto;
import org.bookstore.dto.response.UserResponseDto;
import org.bookstore.model.Role;
import org.bookstore.model.User;
import org.bookstore.service.RoleService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = MapperUtil.class)
public interface UserMapper {

    UserResponseDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    User toModelWithUserRole(UserRegistrationRequestDto requestDto,
                             @Context RoleService roleService);

    @AfterMapping
    default void serUserRole(@MappingTarget User user,
                             @Context RoleService roleService) {
        user.getRoles().add(roleService.geRoletByName(Role.RoleName.USER));
    }
}
