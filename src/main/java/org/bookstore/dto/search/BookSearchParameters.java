package org.bookstore.dto.search;

import java.math.BigDecimal;

public record BookSearchParameters(
        String titlePart,
        String authorPart,
        String isbnPart,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        String descriptionPart) {
}
