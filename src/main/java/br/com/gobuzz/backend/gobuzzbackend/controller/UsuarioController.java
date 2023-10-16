package br.com.gobuzz.backend.gobuzzbackend.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import br.com.gobuzz.backend.gobuzzbackend.model.Usuario;
import br.com.gobuzz.backend.gobuzzbackend.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastro")
        public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
            Usuario novoUsuario = usuarioService.criarUsuario(usuario);
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        }


    @GetMapping
    public ResponseEntity<List<Usuario>> obterTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.obterTodosUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.obterUsuarioPorId(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
public ResponseEntity<Usuario> atualizarUsuario(
    @PathVariable Long id,
    @RequestPart(value = "imagem", required = false) String imagemBase64,
    @ModelAttribute Usuario usuarioAtualizado
) {
    Optional<Usuario> usuarioOptional = usuarioService.obterUsuarioPorId(id);
    if (usuarioOptional.isPresent()) {
        Usuario usuarioExistente = usuarioOptional.get();

        // Atualize os campos relevantes do usuarioExistente com os dados do usuarioAtualizado.
        if (usuarioAtualizado.getNome() != null) {
            usuarioExistente.setNome(usuarioAtualizado.getNome());
        }
        if (usuarioAtualizado.getCidade() != null) {
            usuarioExistente.setCidade(usuarioAtualizado.getCidade());
        }
        if (usuarioAtualizado.getFaculdade() != null) {
            usuarioExistente.setFaculdade(usuarioAtualizado.getFaculdade());
        }
        if (usuarioAtualizado.getCurso() != null) {
            usuarioExistente.setCurso(usuarioAtualizado.getCurso());
        }
        if (usuarioAtualizado.getPeriodo() != null) {
            usuarioExistente.setPeriodo(usuarioAtualizado.getPeriodo());
        }
        if (usuarioAtualizado.getMatricula() != null) {
            usuarioExistente.setMatricula(usuarioAtualizado.getMatricula());
        }

        try {
            if (imagemBase64 != null && !imagemBase64.isEmpty()) {
                // Decodifique a imagem base64 em bytes
                byte[] imagemBytes = Base64.getDecoder().decode(imagemBase64);
                usuarioExistente.setImagem(imagemBytes);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Salve o usuário atualizado
        usuarioExistente = usuarioService.atualizarUsuario(id, usuarioExistente);

        return new ResponseEntity<>(usuarioExistente, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


    // Outros métodos do controller...
}
