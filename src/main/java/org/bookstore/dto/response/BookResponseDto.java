package org.bookstore.dto.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record BookResponseDto(
        Long id,
        String title,
        String author,
        String isbn,
        BigDecimal price,
        String description,
        String coverImage,
        List<Long> categoryIds
) {
    public BookResponseDto {
        if (categoryIds == null) {
            categoryIds = new ArrayList<>();
        }
    }
}
