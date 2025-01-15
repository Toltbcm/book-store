package org.bookstore.mapper;

import org.bookstore.config.MapperConfig;
import org.bookstore.dto.request.UserRegistrationRequestDto;
import org.bookstore.dto.response.UserResponseDto;
import org.bookstore.model.Role;
import org.bookstore.model.User;
import org.bookstore.repository.role.RoleRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = MapperUtil.class)
public interface UserMapper {

    UserResponseDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    User toModelWithUserRole(UserRegistrationRequestDto requestDto,
                             @Context RoleRepository roleRepository);

    @AfterMapping
    default void serUserRole(@MappingTarget User user,
                             @Context RoleRepository roleRepository) {
        user.getRoles().add(roleRepository.getByName(Role.RoleName.USER));
    }
}
