package br.fiap.com.br.lyra.controller;

import br.fiap.com.br.lyra.model.CareerTrail;
import br.fiap.com.br.lyra.model.User;
import br.fiap.com.br.lyra.repository.QuizRepository;
import br.fiap.com.br.lyra.service.CareerTrailService;
import br.fiap.com.br.lyra.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final QuizRepository quizRepository;
    private final CareerTrailService careerTrailService;

    public record CareerTrailDTO(String profile, String content, Instant createdAt) {}


    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        String email = auth.getName();
        User user = userService.findByEmail(email);

        model.addAttribute("user", user);

        // Último quiz
        quizRepository.findTopByUserOrderByCreatedAtDesc(user)
                .ifPresent(q -> model.addAttribute("quiz", q));

        // Última trilha de carreira — agora DTO
        var careerTrailDTO = careerTrailService.findLatestByUser(user.getId());

        if (careerTrailDTO != null) {
            model.addAttribute("careerTrail", careerTrailDTO);
        }

        return "dashboard";
    }

}
