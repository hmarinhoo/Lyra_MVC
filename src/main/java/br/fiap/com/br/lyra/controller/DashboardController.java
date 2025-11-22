package br.fiap.com.br.lyra.controller;

import br.fiap.com.br.lyra.model.User;
import br.fiap.com.br.lyra.service.CareerTrailService;
import br.fiap.com.br.lyra.service.QuizService;
import br.fiap.com.br.lyra.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final QuizService quizService;
    private final CareerTrailService careerTrailService;

    @GetMapping("/dashboard")
    @Transactional(readOnly = true)
    public String dashboard(HttpSession session, Model model) {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        String email = auth.getName();
        User user = userService.findByEmail(email);

        model.addAttribute("user", user);

        // Último quiz
        var quizDTO = quizService.getLastQuizDTO(user.getId());
                model.addAttribute("quiz", quizDTO);

        // Última trilha de carreira — agora DTO
        var careerTrailDTO = careerTrailService.findLatestByUser(user.getId());

        if (careerTrailDTO != null) {
            model.addAttribute("careerTrail", careerTrailDTO);
        }

        return "dashboard";
    }

}
