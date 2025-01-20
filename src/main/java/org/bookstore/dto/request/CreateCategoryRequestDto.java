package org.bookstore.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateCategoryRequestDto(@NotNull String name, String description) {
}
