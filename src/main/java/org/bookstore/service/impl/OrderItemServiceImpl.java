package org.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.bookstore.dto.response.OrderItemResponseDto;
import org.bookstore.mapper.OrderItemMapper;
import org.bookstore.model.CartItem;
import org.bookstore.model.Order;
import org.bookstore.model.OrderItem;
import org.bookstore.repository.OrderItemRepository;
import org.bookstore.service.OrderItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderItem createAndSave(CartItem cartItem, Order order) {
        return orderItemRepository.save(orderItemMapper.createModel(cartItem, order));
    }

    @Override
    public Page<OrderItemResponseDto> getAllByOrderId(Long orderId, Pageable pageable) {

        return orderItemRepository.getAllByOrderIdWithBook(orderId, pageable)
                .map(orderItemMapper::toDto);
    }
}
