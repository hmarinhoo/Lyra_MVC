package br.fiap.com.br.lyra.controller;

import br.fiap.com.br.lyra.dto.QuizDTO;
import br.fiap.com.br.lyra.model.User;
import br.fiap.com.br.lyra.repository.UserRepository;
import br.fiap.com.br.lyra.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final UserRepository userRepository;

    // Página do quiz
    @GetMapping
    public String quizPage(Model model) {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return "redirect:/login";

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Carrega DTO em vez de entidade (corrige erro LOB)
        QuizDTO lastQuiz = quizService.getLastQuizDTO(user.getId());
        model.addAttribute("lastQuiz", lastQuiz);

        return "quiz";
    }

    // Submissão do quiz
    @PostMapping
    public String submitQuiz(@RequestParam("skill") String skill,
                             @RequestParam("interest") String interest,
                             @RequestParam("goal") String goal) {
        try {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) return "redirect:/login";

            User user = userRepository.findByEmail(auth.getName())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            String answersJson = """
                {"skill":"%s","interest":"%s","goal":"%s"}
                """.formatted(skill, interest, goal);

            // Remove último quiz (seguro)
            var lastQuiz = quizService.getLastQuizDTO(user.getId());
            if (lastQuiz != null) {
                quizService.deleteQuiz(lastQuiz.id());
            }

            quizService.submitQuiz(user.getId(), answersJson);

            return "redirect:/dashboard";

        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/error-app";
        }
    }

    // Refazer quiz
    @PostMapping("/redo")
    public String redoQuiz() {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return "redirect:/login";

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        QuizDTO lastQuiz = quizService.getLastQuizDTO(user.getId());
        if (lastQuiz != null) quizService.deleteQuiz(lastQuiz.id());

        return "redirect:/quiz";
    }
}
