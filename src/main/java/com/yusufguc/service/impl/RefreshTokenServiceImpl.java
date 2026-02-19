package com.yusufguc.service.impl;

import com.yusufguc.dto.request.RefreshTokenRequest;
import com.yusufguc.dto.response.AuthResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.jwt.JwtService;
import com.yusufguc.model.RefreshToken;
import com.yusufguc.model.User;
import com.yusufguc.repository.RefreshTokenRepository;
import com.yusufguc.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Override
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        refreshToken.setExpireDate(LocalDateTime.now().plusHours(4));
        return refreshToken;
    }

    private boolean isRefreshTokenExpired(LocalDateTime expireDate) {
        return LocalDateTime.now().isAfter(expireDate);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() ->
                        new BaseException(new ErrorMessage(
                                MessageType.REFRESH_TOKEN_INVALID,
                                request.getRefreshToken()
                        )));
        if (isRefreshTokenExpired(refreshToken.getExpireDate())) {
            throw new BaseException(new ErrorMessage(
                    MessageType.REFRESH_TOKEN_EXPIRED,
                    request.getRefreshToken()
            ));
        }
        String accessToken =
                jwtService.generateToken(refreshToken.getUser());
        RefreshToken newRefreshToken =
                refreshTokenRepository.save(
                        createRefreshToken(refreshToken.getUser())
                );
        return new AuthResponse(
                accessToken,
                newRefreshToken.getToken()
        );
    }


    @Override
    public void deleteExpiredTokens() {
        refreshTokenRepository.deleteByExpireDateBefore(LocalDateTime.now());
    }
}