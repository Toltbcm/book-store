package org.bookstore.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateCategoryRequestDto(@NotNull String name, String description) {
}
