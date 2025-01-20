package org.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public record UpdateBookRequestDto(

        @NotBlank
        @Size(max = 255)
        String title,

        @NotBlank
        @Size(max = 100)
        String author,

        @NotBlank
        @Size(min = 17, max = 17)
        String isbn,

        @NotNull
        @Positive
        BigDecimal price,

        @Size(max = 1000)
        String description,

        @Size(max = 255)
        String coverImage,

        List<Long> categoryIds
) {
}
