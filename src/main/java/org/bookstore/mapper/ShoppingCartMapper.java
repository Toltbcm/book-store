package org.bookstore.mapper;

import java.util.List;
import java.util.Set;
import org.bookstore.config.MapperConfig;
import org.bookstore.dto.response.CartItemResponseDto;
import org.bookstore.dto.response.ShoppingCartResponseDto;
import org.bookstore.model.CartItem;
import org.bookstore.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "cartItems", source = "cartItems", qualifiedByName = "setItems")
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);

    @Named("setItems")
    default List<CartItemResponseDto> setItems(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(i -> new CartItemResponseDto(
                        i.getId(), i.getBook().getId(), i.getBook().getTitle(), i.getQuantity()))
                .toList();
    }
}
