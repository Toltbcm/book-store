package org.bookstore.service;

import org.bookstore.dto.request.CreateCartItemRequestDto;
import org.bookstore.dto.request.UpdateCartItemRequestDto;
import org.bookstore.dto.response.CartItemResponseDto;

public interface CartItemService {

    CartItemResponseDto create(CreateCartItemRequestDto requestDto);

    CartItemResponseDto update(Long id, UpdateCartItemRequestDto requestDto);

    void delete(Long id);
}
