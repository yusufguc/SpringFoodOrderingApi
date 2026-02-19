package com.yusufguc.service.impl;

import com.yusufguc.dto.request.AuthenticateRequest;
import com.yusufguc.dto.request.RegisterRequest;
import com.yusufguc.dto.response.AuthResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.jwt.JwtService;
import com.yusufguc.mapper.UserMapper;
import com.yusufguc.model.RefreshToken;
import com.yusufguc.model.User;
import com.yusufguc.repository.RefreshTokenRepository;
import com.yusufguc.repository.UserRepository;
import com.yusufguc.service.AuthService;
import com.yusufguc.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;



    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())){
            throw new BaseException(
                    new ErrorMessage(MessageType.USERNAME_ALREADY_EXISTS, request.getUsername())
            );
        }

        User user=userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        refreshTokenRepository.save(refreshToken);

        return  new AuthResponse(token,refreshToken.getToken());
    }

    @Override
    public AuthResponse authenticate(AuthenticateRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        refreshTokenRepository.save(refreshToken);

        return  new AuthResponse(token,refreshToken.getToken());
    }


}
