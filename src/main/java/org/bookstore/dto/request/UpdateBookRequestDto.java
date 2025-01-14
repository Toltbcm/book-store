package org.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record UpdateBookRequestDto(

        @NotBlank
        String title,

        @NotBlank
        String author,

        @NotBlank
        String isbn,

        @NotNull
        @PositiveOrZero
        BigDecimal price,

        String description,

        String coverImage
) {
}
