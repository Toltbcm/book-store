package org.bookstore.exception;

public class ShoppingCartEmptyException extends RuntimeException {

    public ShoppingCartEmptyException(String message) {
        super(message);
    }
}
