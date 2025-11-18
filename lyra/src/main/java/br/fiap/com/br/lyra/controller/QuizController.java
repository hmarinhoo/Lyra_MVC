package br.fiap.com.br.lyra.controller;

import br.fiap.com.br.lyra.model.CareerTrail;
import br.fiap.com.br.lyra.model.Quiz;
import br.fiap.com.br.lyra.service.QuizService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import br.fiap.com.br.lyra.repository.CareerTrailRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;
    private final CareerTrailRepository trailRepository;

    public QuizController(QuizService quizService, CareerTrailRepository trailRepository) {
        this.quizService = quizService;
        this.trailRepository = trailRepository;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submit(@RequestParam Long userId, @RequestBody @NotBlank String answersJson) {
        try {
            Quiz saved = quizService.submitQuiz(userId, answersJson);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("quizId", saved.getId(), "profile", saved.getProfile()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/trails")
    public ResponseEntity<?> listTrails(@RequestParam Long userId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PageRequest pg = PageRequest.of(page, size);
        Page<CareerTrail> trails = trailRepository.findByUserIdOrderByCreatedAtDesc(userId, pg);
        return ResponseEntity.ok(trails);
    }
}
