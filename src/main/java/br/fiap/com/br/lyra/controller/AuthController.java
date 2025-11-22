package br.fiap.com.br.lyra.controller;

import br.fiap.com.br.lyra.dto.UserDTO;
import br.fiap.com.br.lyra.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    // Página de login
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Página de registro
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    // Registro + autologin
    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute UserDTO dto, Model model) {
        try {
            var user = userService.register(dto);

            // AUTENTICAÇÃO REAL NO SPRING SECURITY
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(), dto.getPassword()
                    );

            Authentication auth = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);

            return "redirect:/home";

        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "register";
        }
    }
}
