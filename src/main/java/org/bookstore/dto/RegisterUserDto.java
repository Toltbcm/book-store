package org.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 8, max = 56)
    private String password;

    @NotNull
    @Size(min = 8, max = 56)
    private String confirmPassword;

    @NotNull
    @Size(min = 1, max = 35)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 35)
    private String lastName;

    @NotNull
    @Size(min = 1, max = 256)
    private String shippingAddress;

    @JsonIgnore
    @AssertTrue(message = "passwords do not match")
    public boolean doPasswordsMatch() {
        return password.equals(confirmPassword);
    }
}
