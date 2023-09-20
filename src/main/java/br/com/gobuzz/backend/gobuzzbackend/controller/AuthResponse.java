package br.com.gobuzz.backend.gobuzzbackend.controller;

public class AuthResponse {
    private final String token;
    private final Long userId;

    public AuthResponse(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }
}
