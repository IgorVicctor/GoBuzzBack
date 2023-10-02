package br.com.gobuzz.backend.gobuzzbackend.model;

public class LoginRequest {
    private String email;
    private String senha;
    private String tipo_usuario;

    public LoginRequest(String email, String senha, String tipo_usuario) {
        this.email = email;
        this.senha = senha;
        this.tipo_usuario = tipo_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUsuario() {
        return tipo_usuario;
    }

    public void setTipoUsuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
}
