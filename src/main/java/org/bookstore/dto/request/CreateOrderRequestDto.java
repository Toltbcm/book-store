package org.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrderRequestDto(

        @NotBlank
        @Size(min = 8, max = 255)
        String shippingAddress
) {
}
