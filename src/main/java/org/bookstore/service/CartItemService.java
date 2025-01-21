package org.bookstore.service;

import org.bookstore.dto.request.CreateCartItemRequestDto;
import org.bookstore.dto.response.CartItemResponseDto;

public interface CartItemService {

    CartItemResponseDto create(CreateCartItemRequestDto requestDto);
}
