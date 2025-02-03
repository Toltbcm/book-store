package org.bookstore.util;

public record ErrorObject(
        String timeStamp,
        String status,
        String message
) {
}
