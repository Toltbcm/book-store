package org.bookstore.dto.request.search;

import java.math.BigDecimal;

public record BookSearchParametersRequestDto(
        String titlePart,
        String authorPart,
        String isbnPart,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        String descriptionPart) {
}
