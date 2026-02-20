package com.yusufguc.controller.impl;

import com.yusufguc.controller.AuthController;
import com.yusufguc.dto.request.AuthenticateRequest;
import com.yusufguc.dto.request.RefreshTokenRequest;
import com.yusufguc.dto.request.RegisterRequest;
import com.yusufguc.dto.response.AuthResponse;
import com.yusufguc.service.AuthService;
import com.yusufguc.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;


    @PostMapping("/register")
    @Override
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    @PostMapping("/authenticate")
    @Override
    public ResponseEntity<AuthResponse> authenticate(
            @Valid @RequestBody AuthenticateRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    @Override
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(refreshTokenService.refreshToken(request));
    }


}
