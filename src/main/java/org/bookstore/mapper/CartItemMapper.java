package org.bookstore.mapper;

import org.bookstore.config.MapperConfig;
import org.bookstore.dto.request.CreateCartItemRequestDto;
import org.bookstore.dto.response.CartItemResponseDto;
import org.bookstore.model.Book;
import org.bookstore.model.CartItem;
import org.bookstore.model.ShoppingCart;
import org.bookstore.service.BookService;
import org.bookstore.service.ShoppingCartService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", source = "bookId", qualifiedByName = "setBook")
    @Mapping(target = "shoppingCart", expression = "java(setShoppingCart(shoppingCartService))")
    CartItem toModel(CreateCartItemRequestDto requestDto,
                     @Context BookService bookService,
                     @Context ShoppingCartService shoppingCartService);

    @Mapping(target = "bookId", source = "cartItem.book.id")
    @Mapping(target = "bookTitle", source = "cartItem.book.title")
    CartItemResponseDto toDto(CartItem cartItem);

    @Named("setBook")
    default Book setBook(Long id, @Context BookService bookService) {
        return bookService.getBook(id);
    }

    default ShoppingCart setShoppingCart(@Context ShoppingCartService shoppingCartService) {
        return shoppingCartService.getCurrentCart();
    }
}
