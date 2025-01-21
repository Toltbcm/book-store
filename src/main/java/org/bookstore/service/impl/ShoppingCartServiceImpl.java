package org.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.bookstore.model.ShoppingCart;
import org.bookstore.repository.cart.ShoppingCartRepository;
import org.bookstore.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCart create(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }
}
