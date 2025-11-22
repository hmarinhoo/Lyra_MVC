package br.fiap.com.br.lyra.service;

import br.fiap.com.br.lyra.dto.QuizDTO;
import br.fiap.com.br.lyra.model.Quiz;
import br.fiap.com.br.lyra.model.User;
import br.fiap.com.br.lyra.repository.QuizRepository;
import br.fiap.com.br.lyra.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final MessageProducer producer;
    private final CareerTrailService careerTrailService;

    public QuizService(QuizRepository quizRepository,
                       UserRepository userRepository,
                       MessageProducer producer,
                        CareerTrailService carrerTrailsService) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.producer = producer;
        this.careerTrailService=carrerTrailsService;
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

    public Quiz getLastQuizByUser(Long userId) {
        List<Quiz> quizzes = quizRepository.findByUserIdOrderByCreatedAtDesc(userId);
        if (quizzes.isEmpty()) return null;
        return quizzes.get(0); // último quiz
    }

    @Transactional
    public void deleteQuiz(Long quizId) {
        var quizOpt = quizRepository.findById(quizId);
        if (quizOpt.isPresent()) {
            // Remover trilhas associadas
            careerTrailService.deleteByUser(quizOpt.get().getUser().getId());
            quizRepository.deleteById(quizId);
        }
    }

    @Transactional(readOnly = true)
    public QuizDTO getLastQuizDTO(Long userId) {
        return quizRepository.findLatestQuizDTO(userId);
    }

}
