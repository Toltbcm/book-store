package org.bookstore.service.impl;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.bookstore.dto.request.CreateOrderRequestDto;
import org.bookstore.dto.response.OrderResponseDto;
import org.bookstore.exception.ShoppingCartEmptyException;
import org.bookstore.mapper.OrderMapper;
import org.bookstore.model.Order;
import org.bookstore.model.OrderItem;
import org.bookstore.model.ShoppingCart;
import org.bookstore.repository.OrderRepository;
import org.bookstore.service.OrderItemService;
import org.bookstore.service.OrderService;
import org.bookstore.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemService orderItemService;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public OrderResponseDto createAndSave(CreateOrderRequestDto requestDto) {
        ShoppingCart cart = shoppingCartService.getCurrentCartWithItemsWithBookAndUser();
        if (cart.getCartItems().isEmpty()) {
            throw new ShoppingCartEmptyException("Can't create order. Cart is empty.");
        }
        Order order = orderRepository.save(orderMapper.toModel(requestDto, cart));
        Set<OrderItem> orderItems = cart.getCartItems().stream()
                .map(i -> orderItemService.createAndSave(i, order))
                .collect(Collectors.toSet());
        order.setOrderItems(orderItems);
        return orderMapper.toDto(order);
    }
}
