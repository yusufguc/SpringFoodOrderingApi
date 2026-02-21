package com.yusufguc.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String authHeader = request.getHeader("Authorization");
        String message;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            message = "Token is missing";
        } else {
            message = "Invalid or expired token";
        }

        response.getWriter().write("""
            {
              "error": "Unauthorized",
              "message": "%s"
            }
        """.formatted(message));
    }
}