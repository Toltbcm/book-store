package org.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.bookstore.dto.request.CreateCartItemRequestDto;
import org.bookstore.dto.response.CartItemResponseDto;
import org.bookstore.mapper.CartItemMapper;
import org.bookstore.repository.item.CartItemRepository;
import org.bookstore.service.CartItemService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItemResponseDto create(CreateCartItemRequestDto requestDto) {
        return cartItemMapper.toDto(cartItemRepository.save(cartItemMapper.toModel(requestDto)));
    }
}
