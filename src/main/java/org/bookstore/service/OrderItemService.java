package org.bookstore.service;

import org.bookstore.model.CartItem;
import org.bookstore.model.Order;
import org.bookstore.model.OrderItem;

public interface OrderItemService {

    OrderItem createAndSave(CartItem cartItem, Order order);
}
