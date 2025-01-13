package org.bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.URL;

public record UpdateBookDto(

        @NotBlank
        String title,

        @NotBlank
        String author,

        @NotBlank
        String isbn,

        @NotNull
        @Min(0)
        BigDecimal price,

        String description,

        @URL
        String coverImage
) {
}
