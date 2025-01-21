package org.bookstore.dto.response;

import java.util.List;

public record ShippingCartResponseDto(Long id, Long userId, List<CartItemResponseDto> cartItems) {
}
