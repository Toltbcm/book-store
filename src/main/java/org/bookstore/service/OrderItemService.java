package org.bookstore.service;

import org.bookstore.dto.response.OrderItemResponseDto;
import org.bookstore.model.CartItem;
import org.bookstore.model.Order;
import org.bookstore.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderItemService {

    OrderItem createAndSave(CartItem cartItem, Order order);

    Page<OrderItemResponseDto> getAllByOrderId(Long orderId, Pageable pageable);

    OrderItemResponseDto getByIdAndOrderId(Long orderId, Long itemId);
}
