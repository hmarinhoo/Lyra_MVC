package br.fiap.com.br.lyra.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Exceções conhecidas — exemplo: erros de regra de negócio
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        model.addAttribute("title", "Algo deu errado");
        model.addAttribute("message", ex.getMessage());
        return "error-app";
    }

    // Qualquer outra exceção inesperada
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute("title", "Erro Interno");
        model.addAttribute("message", "Um erro inesperado ocorreu. Nossa equipe já está verificando.");
        return "error-app";
    }
}
