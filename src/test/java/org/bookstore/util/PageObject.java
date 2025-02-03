package org.bookstore.util;

import java.util.List;

public record PageObject(
        List<Object> content,
        int size,
        int number,
        int totalElements,
        int totalPages
) {
}
