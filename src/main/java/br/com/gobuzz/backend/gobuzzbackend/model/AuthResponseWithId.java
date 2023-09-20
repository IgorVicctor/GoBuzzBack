package br.com.gobuzz.backend.gobuzzbackend.model;

public class AuthResponseWithId {
    private final String token;
    private final Long userId;

    public AuthResponseWithId(String token, Long userId) {
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
