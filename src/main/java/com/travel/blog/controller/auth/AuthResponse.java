package com.travel.blog.controller.auth;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        long expiresAt
) {

    public static AuthResponse of(String token, long expiresIn, long expiry) {
        return new AuthResponse(
                token,
                "Bearer",
                expiresIn,
                expiry
        );
    }
}