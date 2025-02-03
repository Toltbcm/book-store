package org.bookstore.util;

import java.util.List;

public record Page<T>(
        List<T> content,
        int size,
        int number,
        int totalElements,
        int totalPages
) {
}
