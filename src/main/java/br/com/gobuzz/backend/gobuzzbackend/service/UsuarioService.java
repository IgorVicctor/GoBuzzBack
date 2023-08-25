package br.com.gobuzz.backend.gobuzzbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.gobuzz.backend.gobuzzbackend.model.Usuario;
import br.com.gobuzz.backend.gobuzzbackend.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario criarUsuario(Usuario usuario) {
        // Criptografa a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> obterTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obterUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }


    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente != null) {
            usuarioExistente.setNome(usuarioAtualizado.getNome());
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
            usuarioExistente.setSenha(usuarioAtualizado.getSenha());
            usuarioExistente.setTipo_usuario(usuarioAtualizado.getTipo_usuario());
            usuarioExistente.setFaculdade(usuarioAtualizado.getFaculdade());
            usuarioExistente.setPeriodo(usuarioAtualizado.getPeriodo());
            usuarioExistente.setCurso(usuarioAtualizado.getCurso());
            usuarioExistente.setDias_transporte(usuarioAtualizado.getDias_transporte());
            usuarioExistente.setMatricula(usuarioAtualizado.getMatricula());

            return usuarioRepository.save(usuarioExistente);
        }
        return null; // Retorne algo apropriado caso o usuário não seja encontrado
    }

    // Outros métodos de serviço...
}

