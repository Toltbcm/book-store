package org.bookstore.mapper;

import java.math.BigDecimal;
import org.bookstore.config.MapperConfig;
import org.bookstore.dto.response.OrderItemResponseDto;
import org.bookstore.model.CartItem;
import org.bookstore.model.Order;
import org.bookstore.model.OrderItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "order", expression = "java(order)")
    OrderItem createModel(CartItem cartItem, @Context Order order);

    @Mapping(target = "bookId", source = "book.id")
    OrderItemResponseDto toDto(OrderItem orderItem);

    @AfterMapping
    default void setPrice(@MappingTarget OrderItem orderItem, CartItem cartItem) {
        orderItem.setPrice(
                cartItem.getBook().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
    }
}
