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
import org.bookstore.model.User;
import org.bookstore.repository.OrderRepository;
import org.bookstore.service.CartItemService;
import org.bookstore.service.OrderItemService;
import org.bookstore.service.OrderService;
import org.bookstore.service.ShoppingCartService;
import org.bookstore.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemService orderItemService;
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;
    private final UserService userService;

    @Override
    @Transactional
    public OrderResponseDto place(CreateOrderRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartService.getCurrentCartWithItemsWithBookAndUser();
        if (shoppingCart.getCartItems().isEmpty()) {
            throw new ShoppingCartEmptyException("Can't create order. Cart is empty.");
        }
        Order order = orderRepository.save(orderMapper.toModel(requestDto, shoppingCart));
        Set<OrderItem> orderItems = shoppingCart.getCartItems().stream()
                .map(i -> orderItemService.createAndSave(i, order))
                .collect(Collectors.toSet());
        shoppingCart.getCartItems().forEach(i -> cartItemService.delete(i.getId()));
        order.setOrderItems(orderItems);
        return orderMapper.toDto(order);
    }

    @Override
    public Page<OrderResponseDto> getAll(Pageable pageable) {
        User currentUser = userService.getCurrentUser();
        return orderRepository.getAllByUserIdWithItems(currentUser.getId(), pageable)
                .map(orderMapper::toDto);
    }
}
