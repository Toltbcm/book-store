package org.bookstore.service;

import org.bookstore.dto.response.ShoppingCartResponseDto;
import org.bookstore.model.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCart save(ShoppingCart shoppingCart);

    ShoppingCartResponseDto getCurrent();

    ShoppingCart getCurrentCart();
}
