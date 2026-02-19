package com.yusufguc.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenRequest {

    @NotBlank(message = "RefreshToken cannot be empty")
    private  String refreshToken;
}
