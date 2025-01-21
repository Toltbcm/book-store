package org.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.bookstore.dto.response.ShoppingCartResponseDto;
import org.bookstore.exception.EntityNotFoundException;
import org.bookstore.mapper.ShoppingCartMapper;
import org.bookstore.model.ShoppingCart;
import org.bookstore.repository.cart.ShoppingCartRepository;
import org.bookstore.service.ShoppingCartService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto getCurrent() {
        ShoppingCart shoppingCartFull = shoppingCartRepository.findByUserEmailWithItemsWithBookAndUser(getEmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart for user: " + getEmail()));
        return shoppingCartMapper.toDto(shoppingCartFull);
    }

    @Override
    public ShoppingCart getCurrentCart() {
        return shoppingCartRepository.findByUserEmail(getEmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart for user: " + getEmail()));
    }

    private String getEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
