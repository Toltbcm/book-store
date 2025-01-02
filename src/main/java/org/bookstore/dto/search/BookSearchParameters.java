package org.bookstore.dto.search;

import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public record BookSearchParameters(
        String titlePart,
        String authorPart,
        String isbnPart,
        @DecimalMin(value = "0.0")
        BigDecimal minPrice,
        BigDecimal maxPrice,
        String descriptionPart) {
}
