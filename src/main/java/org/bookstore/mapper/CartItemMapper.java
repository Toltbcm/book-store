package org.bookstore.mapper;

import org.bookstore.config.MapperConfig;
import org.bookstore.dto.request.CreateCartItemRequestDto;
import org.bookstore.dto.response.CartItemResponseDto;
import org.bookstore.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = MapperUtil.class)
public interface CartItemMapper {

    @Mapping(target = "id", ignore = true)
    //    @Mapping(target = "book", source = "bookId", qualifiedByName = "setBook")
    //    @Mapping(target = "shoppingCart", expression = "java(mapperUtil.setShoppingCart())")
    CartItem toModel(CreateCartItemRequestDto requestDto);

    CartItemResponseDto toDto(CartItem cartItem);
}
