package org.bookstore.dto.response;

public record CartItemResponseDto(
        Long id,
        Long bookId,
        String bookTitle,
        Integer quantity
) {
}
