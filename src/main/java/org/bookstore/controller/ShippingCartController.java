package org.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bookstore.dto.request.CreateCartItemRequestDto;
import org.bookstore.dto.request.UpdateCartItemRequestDto;
import org.bookstore.dto.response.CartItemResponseDto;
import org.bookstore.dto.response.ShippingCartResponseDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shipping cart", description = "Endpoints for shipping cart management")
@RequiredArgsConstructor
@RestController
@RequestMapping("cart")
public class ShippingCartController {

    @Operation(summary = "Get shipping cart",
            description = "Endpoint for getting shipping cart for current user")
    @GetMapping
    public ShippingCartResponseDto get() {
        return null;
    }

    @Operation(summary = "Create cart item", description = "Endpoint for add book(s) to cart")
    @PostMapping("/items")
    public CartItemResponseDto create(@RequestBody CreateCartItemRequestDto requestDto) {
        return null;
    }

    @Operation(summary = "Update cart item", description = "Endpoint for updating books quantity")
    @PostMapping("/items/{id}")
    public CartItemResponseDto update(
            @PathVariable Long id, @RequestBody UpdateCartItemRequestDto requestDto) {
        return null;
    }

    @Operation(summary = "Delete cart item",
            description = "Endpoint for deleting book(s) from cart")
    @DeleteMapping("/items/{id}")
    public CartItemResponseDto delete(@PathVariable Long id) {
        return null;
    }
}
