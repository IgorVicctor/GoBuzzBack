package br.com.gobuzz.backend.gobuzzbackend.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.gobuzz.backend.gobuzzbackend.model.Usuario;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private Usuario usuario;

    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String tipoUsuario = usuario.getTipo_usuario();
        if (tipoUsuario == null || tipoUsuario.trim().isEmpty()) {
            // Caso o campo tipo_usuario seja nulo ou vazio, definimos um valor padrão (ROLE_USER)
            tipoUsuario = "ROLE_USER";
        } else {
            tipoUsuario = "ROLE_" + tipoUsuario; // Adicionar o prefixo "ROLE_" para as permissões
        }
        return Collections.singletonList(new SimpleGrantedAuthority(tipoUsuario));
    }



    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Defina a lógica correta de expiração da conta do usuário
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Defina a lógica correta para verificar se a conta do usuário está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Defina a lógica correta para verificar se as credenciais do usuário estão expiradas
    }

    @Override
    public boolean isEnabled() {
        return true; // Defina a lógica correta para verificar se a conta do usuário está habilitada
    }

    // Implemente os métodos restantes da interface UserDetails
    // ...
}
