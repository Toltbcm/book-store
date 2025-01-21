package org.bookstore.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateCartItemRequestDto(

        @NotNull
        @Positive
        Integer quantity
) {
}
