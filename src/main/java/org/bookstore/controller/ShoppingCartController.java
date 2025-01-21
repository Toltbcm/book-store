package org.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bookstore.dto.request.CreateCartItemRequestDto;
import org.bookstore.dto.request.UpdateCartItemRequestDto;
import org.bookstore.dto.response.CartItemResponseDto;
import org.bookstore.dto.response.ShoppingCartResponseDto;
import org.bookstore.service.CartItemService;
import org.bookstore.service.ShoppingCartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shipping cart", description = "Endpoints for shipping cart management")
@RequiredArgsConstructor
@RestController
@RequestMapping("cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get shipping cart",
            description = "Endpoint for getting shipping cart for current user")
    @GetMapping
    public ShoppingCartResponseDto get() {
        return shoppingCartService.getCurrent();
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Create cart item", description = "Endpoint for add book(s) to cart")
    @PostMapping("/items")
    public CartItemResponseDto create(@Valid @RequestBody CreateCartItemRequestDto requestDto) {
        return cartItemService.create(requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update cart item", description = "Endpoint for updating books quantity")
    @PutMapping("/items/{id}")
    public CartItemResponseDto update(
            @PathVariable Long id, @Valid @RequestBody UpdateCartItemRequestDto requestDto) {
        return cartItemService.update(id, requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Delete cart item",
            description = "Endpoint for deleting book(s) from cart")
    @DeleteMapping("/items/{id}")
    public CartItemResponseDto delete(@PathVariable Long id) {
        return null;
    }
}
