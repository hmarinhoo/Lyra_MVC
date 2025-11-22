package br.fiap.com.br.lyra.controller;

import br.fiap.com.br.lyra.model.User;
import br.fiap.com.br.lyra.service.UserService;
import br.fiap.com.br.lyra.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final UserRepository userRepository;

    // Exibe a página de perfil
    @GetMapping
    public String profilePage(Model model) {
        User user = userService.getCurrentUser();
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "profile";
    }

    // Atualiza informações do usuário
    @PostMapping("/update")
    public String updateProfile(@RequestParam String name,
                                @RequestParam String experienceLevel,
                                @RequestParam String interests,
                                @RequestParam String locale) {

        User user = userService.getCurrentUser();
        if (user == null) {
            return "redirect:/login";
        }

        user.setName(name);
        user.setExperienceLevel(experienceLevel);
        user.setInterests(interests);
        user.setLocale(locale);

        userRepository.save(user);

        return "redirect:/profile?success";
    }

    // Excluir conta
    @PostMapping("/delete")
    public String deleteAccount(HttpSession session) {
        User user = userService.getCurrentUser();
        if (user != null) {
            userRepository.delete(user);
            session.invalidate(); // desloga o usuário
        }

        return "redirect:/?accountDeleted";
    }
}
