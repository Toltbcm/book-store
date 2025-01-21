package org.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(

        @NotBlank
        @Size(max = 100)
        String email,

        @NotBlank
        @Size(min = 8, max = 36)
        String password
) {
}
