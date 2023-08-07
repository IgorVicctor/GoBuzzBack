package br.com.gobuzz.backend.gobuzzbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/sucesso")
    public String paginaSucesso() {
        return "sucesso"; // O nome "sucesso" corresponde ao arquivo HTML da página de sucesso
    }
}
