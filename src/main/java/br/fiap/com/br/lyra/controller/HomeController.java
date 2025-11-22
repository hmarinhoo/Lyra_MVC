package br.fiap.com.br.lyra.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.fiap.com.br.lyra.model.User;
import br.fiap.com.br.lyra.service.QuizService;
import br.fiap.com.br.lyra.service.UserService;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
    private final QuizService quizService;

    public HomeController(UserService userService, QuizService quizService) {
        this.userService = userService;
        this.quizService = quizService;
    }

    @GetMapping
    public String home(Model model) {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        String email = auth.getName(); 
        User user = userService.findByEmail(email);

        var quizDTO = quizService.getLastQuizDTO(user.getId());
        model.addAttribute("quiz", quizDTO);
        model.addAttribute("user", user);

        return "home";
    }
}
