package org.bookstore.mapper;

import org.bookstore.config.MapperConfig;
import org.bookstore.dto.request.CreateCartItemRequestDto;
import org.bookstore.dto.request.UpdateCartItemRequestDto;
import org.bookstore.dto.response.CartItemResponseDto;
import org.bookstore.model.Book;
import org.bookstore.model.CartItem;
import org.bookstore.model.ShoppingCart;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "shoppingCart", ignore = true)
    CartItem toModel(
            CreateCartItemRequestDto requestDto,
            @Context Book book, @Context ShoppingCart shoppingCart);

    @Mapping(target = "bookId", source = "cartItem.book.id")
    @Mapping(target = "bookTitle", source = "cartItem.book.title")
    CartItemResponseDto toDto(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "shoppingCart", ignore = true)
    @Mapping(target = "quantity", source = "quantity")
    CartItem update(@MappingTarget CartItem cartItem, UpdateCartItemRequestDto requestDto);

    @AfterMapping
    default void setBookAndCart(
            @MappingTarget CartItem cartItem,
            @Context Book book, @Context ShoppingCart shoppingCart) {
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCart);
    }
}
