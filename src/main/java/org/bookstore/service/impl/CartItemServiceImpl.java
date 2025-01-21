package org.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.bookstore.dto.request.CreateCartItemRequestDto;
import org.bookstore.dto.request.UpdateCartItemRequestDto;
import org.bookstore.dto.response.CartItemResponseDto;
import org.bookstore.exception.EntityNotFoundException;
import org.bookstore.mapper.CartItemMapper;
import org.bookstore.model.CartItem;
import org.bookstore.repository.item.CartItemRepository;
import org.bookstore.service.BookService;
import org.bookstore.service.CartItemService;
import org.bookstore.service.ShoppingCartService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookService bookService;
    private final ShoppingCartService shoppingCartService;

    @Override
    public CartItemResponseDto create(CreateCartItemRequestDto requestDto) {
        return cartItemMapper.toDto(cartItemRepository.save(
                cartItemMapper.toModel(requestDto, bookService, shoppingCartService)));
    }

    @Override
    @Transactional
    public CartItemResponseDto update(Long id, UpdateCartItemRequestDto requestDto) {
        CartItem fullCartItem = getFullItemAndValidateForExistenceAndOwner(id);
        return cartItemMapper.toDto(cartItemRepository.save(
                cartItemMapper.update(fullCartItem, requestDto)));
    }

    @Override
    public void delete(Long id) {
        getFullItemAndValidateForExistenceAndOwner(id);
        cartItemRepository.deleteById(id);
    }

    private CartItem getFullItemAndValidateForExistenceAndOwner(Long id) {
        CartItem fullCartItem = getFullCartItem(id);
        validateItemOwner(id, fullCartItem);
        return fullCartItem;
    }

    private CartItem getFullCartItem(Long id) {
        return cartItemRepository.fetchFullById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find cart item by id: " + id));
    }

    private void validateItemOwner(Long itemId, CartItem cartItem) {
        String currentEmail =
                (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!cartItem.getShoppingCart().getUser().getEmail().equals(currentEmail)) {
            throw new AccessDeniedException(
                    "User " + currentEmail + " is not owner of cart item with id: " + itemId);
        }
    }
}
