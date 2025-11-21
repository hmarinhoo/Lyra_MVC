package br.fiap.com.br.lyra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    // Método 'home' removido para evitar conflito com DashboardController#home
    // Página pública 'about'
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}