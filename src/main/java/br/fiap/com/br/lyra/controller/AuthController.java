package br.fiap.com.br.lyra.controller;

import br.fiap.com.br.lyra.model.User;
import br.fiap.com.br.lyra.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

 

@Controller
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // ===== PÁGINA INICIAL =====
    @GetMapping("/")
    public String index() {
        return "index"; // página com opção de login ou cadastro
    }

    // ===== LOGIN =====
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // ===== CADASTRO =====
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("name") String name,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           Model model,
                           HttpSession session) {

        if (userService.findByEmail(email).isPresent()) {
            model.addAttribute("error", "E-mail já cadastrado");
            return "register";
        }

        // Cria usuário apenas com nome, email e senha
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        userService.save(user);

        session.setAttribute("user", user); // salva usuário na sessão
        return "redirect:/dashboard";
    }

    // ===== LOGOUT =====
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
