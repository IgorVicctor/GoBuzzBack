package br.com.gobuzz.backend.gobuzzbackend.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.gobuzz.backend.gobuzzbackend.model.Usuario;
import br.com.gobuzz.backend.gobuzzbackend.service.QRCodeService;
import br.com.gobuzz.backend.gobuzzbackend.service.UsuarioService;

@RestController
@RequestMapping("/onibus")
@CrossOrigin(origins = "http://192.168.31.95:8080") // Substitua pela URL do seu aplicativo React Native
public class OnibusController {

    public static final int CAPACIDADE_MAXIMA = 50;

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private UsuarioService usuarioService; // Injete o serviço de usuário aqui

    private List<Usuario> passageiros = new ArrayList<>();
    private boolean ativo = false;

    @GetMapping("/qrcode/{usuarioId}")
public ResponseEntity<byte[]> gerarQRCodeParaUsuario(@PathVariable Long usuarioId) {
    try {
        // Lógica para buscar os dados do usuário com o ID especificado
        Usuario usuario = usuarioService.obterUsuarioPorId(usuarioId);

        int largura = 300; // Defina a largura do QR code
        int altura = 300; // Defina a altura do QR code
        
        if (usuario != null) {
            String conteudoQRCode = "Informações do usuário: " + usuario.getNome() + ", ID: " + usuario.getId();
            
            // Gere o QR code e armazene-o no usuário
            byte[] qrCode = qrCodeService.generateQRCode(conteudoQRCode, largura, altura);
            usuario.setCodigoQR(Base64.getEncoder().encodeToString(qrCode)); // Converta o QR code em uma string Base64
                
            // Atualize o usuário para armazenar o QR code
            usuarioService.atualizarUsuario(usuarioId, usuario);
                
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
                
            return new ResponseEntity<>(qrCode, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    

    @PostMapping("/iniciar")
    public ResponseEntity<String> iniciarOnibus() {
        ativo = true;
        return ResponseEntity.ok("Ônibus iniciado");
    }

    @PostMapping("/encerrar")
    public ResponseEntity<String> encerrarOnibus() {
        ativo = false;
        passageiros.clear();
        return ResponseEntity.ok("Ônibus encerrado");
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        String status = ativo ? "Ativo" : "Inativo";
        return ResponseEntity.ok("Status do ônibus: " + status);
    }

    @GetMapping("/contagem")
    public ResponseEntity<Integer> getContagemPassageiros() {
        return ResponseEntity.ok(passageiros.size());
    }

   

    public boolean isAtivo() {
        return ativo;
    }

    public void addPassageiro(Usuario usuario) {
        passageiros.add(usuario);
    }
}
