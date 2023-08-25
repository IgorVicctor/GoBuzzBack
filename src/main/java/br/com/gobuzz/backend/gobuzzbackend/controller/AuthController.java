package br.com.gobuzz.backend.gobuzzbackend.controller;

import br.com.gobuzz.backend.gobuzzbackend.model.LoginRequest;
import br.com.gobuzz.backend.gobuzzbackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://192.168.31.95:8080") // Substitua pela URL do seu aplicativo React Native
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> autenticarUsuario(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String senha = loginRequest.getSenha();

        // Verifica se a senha é criptografada (começa com "$2a$") ou não
        if (senha.startsWith("$2a$")) {
            // Senha já está criptografada, realiza autenticação normalmente
            return authService.authenticateAndGenerateToken(email, senha);
        } else {
            // Senha não criptografada, verifica se é válida diretamente do banco de dados
            if (authService.authenticateWithPlainPassword(email, senha)) {
                // Autenticação bem-sucedida, gera token
                return authService.generateTokenResponse(email);
            } else {
                // Autenticação falhou
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
            }
        }
    }

    // Restante do código
}



