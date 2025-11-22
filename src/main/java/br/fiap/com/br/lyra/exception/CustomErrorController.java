package br.fiap.com.br.lyra.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest req, Model model) {

        Object status = req.getAttribute("jakarta.servlet.error.status_code");

        String title = "Erro inesperado";
        String message = "Algo deu errado.";

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            switch (statusCode) {
                case 404 -> {
                    title = "Página não encontrada";
                    message = "Oops! Não encontramos o que você está buscando.";
                }
                case 403 -> {
                    title = "Acesso negado";
                    message = "Você não tem permissão para acessar esta página.";
                }
                case 500 -> {
                    title = "Erro interno no servidor";
                    message = "Estamos trabalhando para corrigir isso.";
                }
            }
        }

        model.addAttribute("title", title);
        model.addAttribute("message", message);

        return "error";
    }
}
