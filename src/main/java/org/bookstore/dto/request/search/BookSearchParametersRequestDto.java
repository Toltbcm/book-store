package org.bookstore.dto.request.search;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record BookSearchParametersRequestDto(

        String titlePart,

        String authorPart,

        String isbnPart,

        @Min(0)
        BigDecimal minPrice,

        @Positive
        BigDecimal maxPrice,

        String descriptionPart
) {
}
