package org.bookstore.service;

import org.bookstore.dto.request.CreateOrderRequestDto;
import org.bookstore.dto.request.UpdateOrderStatusRequestDto;
import org.bookstore.dto.response.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderResponseDto place(CreateOrderRequestDto requestDto);

    Page<OrderResponseDto> getAll(Pageable pageable);

    OrderResponseDto updateStatus(Long id, UpdateOrderStatusRequestDto requestDto);
}
