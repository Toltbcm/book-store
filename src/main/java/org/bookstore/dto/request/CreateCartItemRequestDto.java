package org.bookstore.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCartItemRequestDto(

        @NotNull Long bookId,

        @NotNull @Positive Integer quantity
) {
}
