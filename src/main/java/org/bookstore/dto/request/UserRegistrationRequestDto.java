package org.bookstore.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequestDto(

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 8, max = 56)
        String password,

        @NotBlank
        @Size(min = 8, max = 56)
        String confirmPassword,

        @NotBlank
        @Size(max = 35)
        String firstName,

        @NotBlank
        @Size(max = 35)
        String lastName,

        @Size(max = 255)
        String shippingAddress
) {
    @AssertTrue(message = "passwords do not match")
    public boolean isPasswordConfirmed() {
        return password.equals(confirmPassword);
    }
}
