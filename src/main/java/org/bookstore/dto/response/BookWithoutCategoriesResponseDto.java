package org.bookstore.dto.response;

import java.math.BigDecimal;

public record BookWithoutCategoriesResponseDto(
        Long id,
        String title,
        String author,
        String isbn,
        BigDecimal price,
        String description,
        String coverImage
) {
}
