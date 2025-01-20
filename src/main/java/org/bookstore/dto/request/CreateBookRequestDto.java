package org.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreateBookRequestDto(

        @NotBlank
        String title,

        @NotBlank
        String author,

        @NotBlank
        @Size(min = 17, max = 17)
        String isbn,

        @NotNull
        @Positive
        BigDecimal price,

        String description,

        String coverImage
) {
}
