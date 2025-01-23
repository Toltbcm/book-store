package org.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.bookstore.model.Order;

public record UpdateOrderStatusRequestDto(

        @NotBlank
        @Size(max = 20)
        Order.Status status
) {
}
