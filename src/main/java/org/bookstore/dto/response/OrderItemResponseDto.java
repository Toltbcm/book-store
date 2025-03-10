package org.bookstore.dto.response;

public record OrderItemResponseDto(
        Long id,
        Long bookId,
        int quantity
) {
}
