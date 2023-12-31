package br.com.gobuzz.backend.gobuzzbackend.service;

import br.com.gobuzz.backend.gobuzzbackend.config.JwtTokenUtil;
import br.com.gobuzz.backend.gobuzzbackend.controller.AuthResponse;
import br.com.gobuzz.backend.gobuzzbackend.model.Usuario;
import br.com.gobuzz.backend.gobuzzbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(
            UsuarioRepository usuarioRepository,
            JwtTokenUtil jwtTokenUtil,
            CustomUserDetailsService userDetailsService,
            AuthenticationManager authenticationManager
    ) {
        this.usuarioRepository = usuarioRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    public boolean authenticateWithPlainPassword(String email, String plainPassword) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean passwordsMatch = passwordEncoder.matches(plainPassword, usuario.getSenha());

        return passwordsMatch;
    }

    public ResponseEntity<?> authenticateAndGenerateTokenWithUserId(String email, String senha) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
    
            // Autentica usando a senha criptografada
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, senha));
    
            // Obtém o ID do usuário
            Long userId = usuarioRepository.findIdByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("ID do usuário não encontrado"));
    
            // Obtém o tipoUsuario do usuário (assumindo que esteja disponível em Usuario)
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
            String tipoUsuario = usuario.getTipo_usuario(); // Substitua pelo método correto
    
            // Gera o token e responde com a resposta contendo o token, o ID do usuário e o tipoUsuario
            String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(token, userId, tipoUsuario));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }
    

    public ResponseEntity<?> generateTokenResponse(String email) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Recupere o campo tipo_usuario do seu modelo de usuário (por exemplo, usuário.getTipoUsuario())
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
            String tipoUsuario = usuario.getTipo_usuario();

            String token = jwtTokenUtil.generateToken(userDetails);

            // Inclua o campo tipo_usuario na resposta
            AuthResponse response = new AuthResponse(token, null, tipoUsuario);

            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
        }
    }
}
