package br.fiap.com.br.lyra.service;

import br.fiap.com.br.lyra.model.Quiz;
import br.fiap.com.br.lyra.model.User;
import br.fiap.com.br.lyra.repository.QuizRepository;
import br.fiap.com.br.lyra.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final MessageProducer producer;

    public QuizService(QuizRepository quizRepository,
                       UserRepository userRepository,
                       MessageProducer producer) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.producer = producer;
    }

    @Transactional
    public Quiz submitQuiz(Long userId, String answersJson) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Quiz quiz = Quiz.builder()
                .user(user)
                .answersJson(answersJson)
                .profile(null) // profile will be filled by AI later
                .createdAt(Instant.now())
                .build();

        Quiz saved = quizRepository.save(quiz);

        // Envia para fila para gerar trilha de carreira
        producer.sendQuizForProcessing(saved.getId());

        return saved;
    }

    public List<Quiz> listByUser(Long userId) {
        return quizRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
