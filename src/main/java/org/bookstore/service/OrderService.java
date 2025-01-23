package org.bookstore.service;

import org.bookstore.dto.request.CreateOrderRequestDto;
import org.bookstore.dto.response.OrderResponseDto;

public interface OrderService {

    OrderResponseDto createAndSave(CreateOrderRequestDto requestDto);
}
