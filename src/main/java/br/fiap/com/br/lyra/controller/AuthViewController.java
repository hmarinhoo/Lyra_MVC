package br.fiap.com.br.lyra.controller;

import br.fiap.com.br.lyra.dto.RegisterRequest;
import br.fiap.com.br.lyra.repository.UserRepository;
import br.fiap.com.br.lyra.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthViewController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    // --- LOGIN PAGE ---
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new br.fiap.com.br.lyra.dto.LoginRequest());
        return "auth/login";
    }

    // --- REGISTER PAGE ---
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }

    // --- PROCESS REGISTER FORM ---
    @PostMapping("/register")
    public String processRegister(
            @Valid @ModelAttribute("registerRequest") RegisterRequest req,
            BindingResult result,
            Model model,
            Locale locale
    ) {
        if (result.hasErrors()) {
            return "auth/register";
        }

        if (userRepository.existsByEmail(req.getEmail())) {
            model.addAttribute("emailError",
                    messageSource.getMessage("user.email.exists", null, locale));
            return "auth/register";
        }

        userService.register(req);

        // depois de registrar → leva para o login
        return "redirect:/auth/login?success";
    }
}
