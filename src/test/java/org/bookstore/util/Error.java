package org.bookstore.util;

public record Error(
        String timeStamp,
        String status,
        String message
) {
}
