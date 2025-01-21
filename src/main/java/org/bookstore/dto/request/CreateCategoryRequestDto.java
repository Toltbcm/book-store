package org.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequestDto(

        @NotBlank
        @Size(max = 20)
        String name,

        @Size(max = 1000)
        String description
) {
}
