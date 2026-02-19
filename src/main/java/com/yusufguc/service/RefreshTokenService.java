package com.yusufguc.service;

import com.yusufguc.dto.request.RefreshTokenRequest;
import com.yusufguc.dto.response.AuthResponse;
import com.yusufguc.model.RefreshToken;
import com.yusufguc.model.User;

public interface RefreshTokenService {

    public RefreshToken createRefreshToken(User user);

    public AuthResponse refreshToken(RefreshTokenRequest request);

    public void deleteExpiredTokens() ;
}
