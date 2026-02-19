package com.yusufguc.controller;

import com.yusufguc.dto.request.AuthenticateRequest;
import com.yusufguc.dto.request.RefreshTokenRequest;
import com.yusufguc.dto.request.RegisterRequest;
import com.yusufguc.dto.response.AuthResponse;
import org.springframework.http.ResponseEntity;

public interface AuthController {

    public ResponseEntity<AuthResponse> register(RegisterRequest request);

    public ResponseEntity<AuthResponse> authenticate(AuthenticateRequest request);

    public ResponseEntity<AuthResponse> refreshToken(RefreshTokenRequest request);
}
