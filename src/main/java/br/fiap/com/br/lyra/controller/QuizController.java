package br.fiap.com.br.lyra.controller;

import br.fiap.com.br.lyra.model.Quiz;
import br.fiap.com.br.lyra.model.User;
import br.fiap.com.br.lyra.repository.QuizRepository;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuizController {

    private final QuizRepository quizRepository;

    public QuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @GetMapping("/quiz")
    public String quizPage(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "quiz";
    }

    @PostMapping("/quiz")
    public String submitQuiz(@RequestParam("skill") String skill,
                             @RequestParam("interest") String interest,
                             @RequestParam("goal") String goal,
                             HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Simula perfil gerado pela IA
        String generatedProfile = "Técnico Criativo";

        // Converte respostas em JSON (simples)
        String answersJson = String.format("{\"skill\":\"%s\",\"interest\":\"%s\",\"goal\":\"%s\"}", skill, interest, goal);

        Quiz quiz = Quiz.builder()
                .user(user)
                .answersJson(answersJson)
                .profile(generatedProfile)
                .build();

        quizRepository.save(quiz);

        return "redirect:/dashboard";
    }
}
