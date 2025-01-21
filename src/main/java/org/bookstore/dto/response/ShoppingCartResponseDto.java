package org.bookstore.dto.response;

import java.util.List;

public record ShoppingCartResponseDto(Long id, Long userId, List<CartItemResponseDto> cartItems) {
}
