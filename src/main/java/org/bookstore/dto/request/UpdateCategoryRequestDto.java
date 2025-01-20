package org.bookstore.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequestDto(

        @NotNull
        @Size(max = 20)
        String name,

        @Size(max = 1000)
        String description
) {
}
