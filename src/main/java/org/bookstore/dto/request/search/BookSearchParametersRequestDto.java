package org.bookstore.dto.request.search;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record BookSearchParametersRequestDto(

        String titlePart,

        String authorPart,

        String isbnPart,

        @PositiveOrZero
        BigDecimal minPrice,

        @Positive
        BigDecimal maxPrice,

        String descriptionPart
) {
    @AssertTrue
    public boolean isMaxPriceMoreThanMinPrice() {
        if (minPrice == null || maxPrice == null) {
            return true;
        }
        return maxPrice().compareTo(minPrice()) > 0;
    }
}
