package com.yusufguc.service;

import com.yusufguc.dto.request.AuthenticateRequest;
import com.yusufguc.dto.request.RegisterRequest;
import com.yusufguc.dto.response.AuthResponse;

public interface AuthService {

    public AuthResponse register(RegisterRequest request);

    public AuthResponse authenticate(AuthenticateRequest request);

}
