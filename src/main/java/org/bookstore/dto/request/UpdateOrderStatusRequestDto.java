package org.bookstore.dto.request;

import org.bookstore.model.Order;

public record UpdateOrderStatusRequestDto(
        Order.Status status
) {
}
