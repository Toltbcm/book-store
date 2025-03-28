package org.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bookstore.dto.request.CreateOrderRequestDto;
import org.bookstore.dto.request.UpdateOrderStatusRequestDto;
import org.bookstore.dto.response.OrderItemResponseDto;
import org.bookstore.dto.response.OrderResponseDto;
import org.bookstore.service.OrderItemService;
import org.bookstore.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order", description = "Endpoints for order management")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Place order", description = "Endpoint for creating order")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderResponseDto create(@Valid @RequestBody CreateOrderRequestDto requestDto) {
        return orderService.place(requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get order history",
            description = "Endpoint for getting order history for current user. Pageable. Sortable")
    @GetMapping
    public Page<OrderResponseDto> getAll(Pageable pageable) {
        return orderService.getAll(pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update status", description = "Endpoint for updating order status")
    @PatchMapping("{id}")
    public OrderResponseDto changeStatus(
            @PathVariable Long id, @Valid @RequestBody UpdateOrderStatusRequestDto requestDto) {
        return orderService.updateStatus(id, requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get order items",
            description = "Endpoint for getting all order items for specific order.  "
                    + "Pageable. Sortable")
    @GetMapping("/{id}/items")
    public Page<OrderItemResponseDto> getItemsForOrder(@PathVariable Long id, Pageable pageable) {
        return orderItemService.getAllByOrderId(id, pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get order item",
            description = "Endpoint for getting order items")
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemResponseDto getItemsForOrder(
            @PathVariable Long orderId, @PathVariable Long itemId) {
        return orderItemService.getByIdAndOrderId(orderId, itemId);
    }
}
