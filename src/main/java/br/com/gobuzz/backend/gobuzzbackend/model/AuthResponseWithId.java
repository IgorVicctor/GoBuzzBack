package br.com.gobuzz.backend.gobuzzbackend.model;

public class AuthResponseWithId {
    private final String token;
    private final Long userId;
    private final String tipoUsuario;


    public AuthResponseWithId(String token, Long userId, String tipoUsuario) {
        this.token = token;
        this.userId = userId;
        this.tipoUsuario = tipoUsuario;

    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }
}
