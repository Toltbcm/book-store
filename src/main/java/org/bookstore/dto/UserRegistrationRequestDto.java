package org.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequestDto(

        @NotNull
        @Email
        String email,

        @NotNull
        @Size(min = 8, max = 56)
        String password,

        @NotNull
        @Size(min = 8, max = 56)
        String confirmPassword,

        @NotNull
        @Size(min = 1, max = 35)
        String firstName,

        @NotNull
        @Size(min = 1, max = 35)
        String lastName,

        @NotNull
        @Size(min = 1, max = 256)
        String shippingAddress
) {
    @JsonIgnore
    @AssertTrue(message = "passwords do not match")
    public boolean doPasswordsMatch() {
        return password.equals(confirmPassword);
    }
}
