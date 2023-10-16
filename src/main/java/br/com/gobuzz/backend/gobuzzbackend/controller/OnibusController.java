package br.com.gobuzz.backend.gobuzzbackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.gobuzz.backend.gobuzzbackend.model.Usuario;
import br.com.gobuzz.backend.gobuzzbackend.repository.UsuarioRepository;

@RestController
@RequestMapping("/onibus")
@CrossOrigin(origins = "http://192.168.31.95:8080") // Substitua pela URL do seu aplicativo React Native
public class OnibusController {

    public static final int CAPACIDADE_MAXIMA = 50;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Ônibus está sempre ativo");
    }

    @GetMapping("/contagem/{motoristaId}")
    public ResponseEntity<Integer> getContagemPassageiros(@PathVariable Long motoristaId) {
        Optional<Usuario> motoristaOptional = usuarioRepository.findById(motoristaId);

        if (motoristaOptional.isPresent()) {
            Usuario motorista = motoristaOptional.get();
            List<Usuario> alunos = motorista.getAlunos();
            
            // Verifique se a lista de alunos não é nula
            if (alunos != null) {
                return ResponseEntity.ok(alunos.size());
            } else {
                return ResponseEntity.ok(0); // Não há alunos associados a esse motorista
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/adicionar-aluno/{motoristaId}/{alunoId}")
    public ResponseEntity<String> adicionarAlunoAoOnibus(@PathVariable Long motoristaId, @PathVariable Long alunoId) {
        if (passageiros.size() < CAPACIDADE_MAXIMA) {
            Optional<Usuario> motoristaOptional = usuarioRepository.findById(motoristaId);
            Optional<Usuario> alunoOptional = usuarioRepository.findById(alunoId);

            if (motoristaOptional.isPresent() && alunoOptional.isPresent()) {
                Usuario motorista = motoristaOptional.get();
                Usuario aluno = alunoOptional.get();

                // Certifique-se de que o usuário fornecido é um aluno, ignorando maiúsculas e minúsculas
                if (aluno.getTipo_usuario() != null && aluno.getTipo_usuario().equalsIgnoreCase("ALUNO")) {
                    // Verifique se o aluno já está na lista do motorista
                    List<Usuario> alunosDoMotorista = motorista.getAlunos();
                    if (alunosDoMotorista != null && alunosDoMotorista.contains(aluno)) {
                        // Se o aluno já está na lista, remova-o
                        alunosDoMotorista.remove(aluno);
                        aluno.setMotorista(null); // Remova a referência ao motorista
                        usuarioRepository.save(motorista); // Salve as alterações no banco de dados

                        return ResponseEntity.ok("Aluno removido do ônibus!");
                    } else {
                        // Adicione o aluno à lista de alunos do motorista
                        if (alunosDoMotorista == null) {
                            alunosDoMotorista = new ArrayList<>();
                        }
                        alunosDoMotorista.add(aluno);

                        // Atualize o campo motorista do aluno com o objeto do motorista
                        aluno.setMotorista(motorista);

                        // Salve as alterações no banco de dados
                        usuarioRepository.save(motorista);

                        return ResponseEntity.ok("Aluno adicionado ao ônibus!");
                    }
                } else {
                    return ResponseEntity.badRequest().body("O usuário não é um aluno");
                }
            } else {
                return ResponseEntity.badRequest().body("Motorista ou aluno não encontrado");
            }
        } else {
            return ResponseEntity.badRequest().body("Não é possível adicionar aluno ao ônibus, a capacidade máxima foi atingida");
        }
    }



    @GetMapping("/motorista/{motoristaId}/alunos")
    public ResponseEntity<List<Usuario>> getAlunosDoOnibus(@PathVariable Long motoristaId) {
        // Verifique se o motorista com o ID fornecido existe
        Optional<Usuario> motoristaOptional = usuarioRepository.findById(motoristaId);

        if (motoristaOptional.isPresent()) {
            Usuario motorista = motoristaOptional.get();
            List<Usuario> alunos = motorista.getAlunos();
            return ResponseEntity.ok(alunos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public boolean isAtivo() {
        return true; // Ônibus sempre ativo
    }

    private List<Usuario> passageiros = new ArrayList<>();
}
