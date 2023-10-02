package br.com.gobuzz.backend.gobuzzbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gobuzz.backend.gobuzzbackend.model.Usuario;
import br.com.gobuzz.backend.gobuzzbackend.repository.UsuarioRepository;
import br.com.gobuzz.backend.gobuzzbackend.service.QRCodeService;

import java.util.Optional;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private OnibusController onibusController;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/ler-qrcode")
    public ResponseEntity<String> lerQRCode(@RequestParam String codigoQR) {
        // Obtenha o valor inteiro da contagem de passageiros
        int contagemPassageiros = onibusController.getContagemPassageiros().getBody();

        // Verifique se o ônibus está ativo e se há espaço disponível
        if (onibusController.isAtivo() && contagemPassageiros < OnibusController.CAPACIDADE_MAXIMA) {
            // Aqui você precisa carregar o usuário do banco de dados com base no códigoQR
            Optional<Usuario> usuarioOptional = usuarioRepository.findByCodigoQR(codigoQR);

            if (usuarioOptional.isPresent()) {
                // O usuário foi encontrado, então você pode obtê-lo do Optional
                Usuario usuario = usuarioOptional.get();
                // Adicione o usuário como passageiro no ônibus
                onibusController.addPassageiro(usuario);
                return ResponseEntity.ok("Aluno adicionado ao ônibus");
            } else {
                return ResponseEntity.badRequest().body("Código QR inválido");
            }
        } else {
            return ResponseEntity.badRequest().body("Não é possível entrar no ônibus no momento");
        }
    }
}
