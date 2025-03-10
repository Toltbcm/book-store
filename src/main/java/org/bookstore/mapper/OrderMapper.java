package org.bookstore.mapper;

import java.math.BigDecimal;
import org.bookstore.config.MapperConfig;
import org.bookstore.dto.request.CreateOrderRequestDto;
import org.bookstore.dto.response.OrderResponseDto;
import org.bookstore.model.Order;
import org.bookstore.model.ShoppingCart;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "user", expression = "java(shoppingCart.getUser())")
    @Mapping(target = "status", expression = "java(Order.Status.NEW)")
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    Order toModel(CreateOrderRequestDto requestDto, @Context ShoppingCart shoppingCart);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "orderItems", source = "orderItems")
    OrderResponseDto toDto(Order order);

    @AfterMapping
    default void setTotal(@MappingTarget Order order, @Context ShoppingCart shoppingCart) {
        order.setTotal(shoppingCart.getCartItems().stream()
                .map(i -> i.getBook().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
