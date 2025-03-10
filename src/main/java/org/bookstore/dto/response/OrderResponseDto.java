package org.bookstore.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.bookstore.model.Order;

public record OrderResponseDto(
        Long id,
        Long userId,
        List<OrderItemResponseDto> orderItems,
        LocalDateTime orderDate,
        BigDecimal total,
        Order.Status status
) {
}
