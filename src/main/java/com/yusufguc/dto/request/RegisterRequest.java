package com.yusufguc.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty
    private String username;

    @NotBlank(message = "The email cannot be empty")
    @Email(message = "Enter a valid email address")
    private String email;

    @Size(min = 6, message = "The password must be at least 6 characters")
    private String password;

    @Pattern(
            regexp = "^(05)[0-9]{9}$",
            message = "The phone number must be in the format 05xxxxxxxxx"
    )
    private String phone;

}