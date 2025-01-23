package org.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.bookstore.dto.response.ShoppingCartResponseDto;
import org.bookstore.exception.EntityNotFoundException;
import org.bookstore.mapper.ShoppingCartMapper;
import org.bookstore.model.ShoppingCart;
import org.bookstore.repository.ShoppingCartRepository;
import org.bookstore.service.ShoppingCartService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    private static String notFoundCartMessage(String name) {
        return String.format("Can't find cart for user: %s", name);
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto getCurrentWithItemsWithBookAndUser() {
        ShoppingCart shoppingCartFull = shoppingCartRepository
                .findByUserEmailWithItemsWithBookAndUser(getEmail())
                .orElseThrow(() -> new EntityNotFoundException(notFoundCartMessage(getEmail())));
        return shoppingCartMapper.toDto(shoppingCartFull);
    }

    @Override
    public ShoppingCart getCurrentCart() {
        return shoppingCartRepository.findByUserEmail(getEmail())
                .orElseThrow(() -> new EntityNotFoundException(notFoundCartMessage(getEmail())));
    }

    private String getEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
