package br.com.gobuzz.backend.gobuzzbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import br.com.gobuzz.backend.gobuzzbackend.model.Usuario;
import br.com.gobuzz.backend.gobuzzbackend.repository.UsuarioRepository;

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
        // Verifique se o e-mail já existe
        if (checkIfEmailExists(usuario.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
        
        // Criptografa a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> obterTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obterUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioExistente = usuarioOptional.get();

            // Atualize os campos relevantes do usuarioExistente com os dados do usuarioAtualizado.
            usuarioExistente.setNome(usuarioAtualizado.getNome());
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
            usuarioExistente.setSenha(usuarioAtualizado.getSenha());
            usuarioExistente.setTipo_usuario(usuarioAtualizado.getTipo_usuario());
            usuarioExistente.setFaculdade(usuarioAtualizado.getFaculdade());
            usuarioExistente.setPeriodo(usuarioAtualizado.getPeriodo());
            usuarioExistente.setCurso(usuarioAtualizado.getCurso());
            usuarioExistente.setSelecionaDias(usuarioAtualizado.getSelecionaDias());
            usuarioExistente.setMatricula(usuarioAtualizado.getMatricula());
            usuarioExistente.setAluno(usuarioAtualizado.isAluno());
            usuarioExistente.setVeiculo(usuarioAtualizado.getVeiculo());
            usuarioExistente.setCnh(usuarioAtualizado.getCnh());
            usuarioExistente.setValidade(usuarioAtualizado.getValidade());
            usuarioExistente.setCidade(usuarioAtualizado.getCidade());

            return usuarioRepository.save(usuarioExistente);
        }

        throw new IllegalArgumentException("Usuário não encontrado");
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public boolean adicionarAlunoAoOnibus(Long motoristaId, Long alunoId) {
        Optional<Usuario> motoristaOptional = usuarioRepository.findById(motoristaId);
        Optional<Usuario> alunoOptional = usuarioRepository.findById(alunoId);

        if (motoristaOptional.isPresent() && alunoOptional.isPresent()) {
            Usuario motorista = motoristaOptional.get();
            Usuario aluno = alunoOptional.get();

            motorista.adicionarAluno(aluno);
            usuarioRepository.save(motorista);

            return true; // Retorna true se o aluno for adicionado com sucesso ao ônibus do motorista
        }

        return false; // Retorna false se o motorista ou aluno não forem encontrados
    }

    public boolean checkIfEmailExists(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    // Outros métodos de serviço...
}
