package br.fiap.com.br.lyra.repository;

import br.fiap.com.br.lyra.model.Quiz;
import br.fiap.com.br.lyra.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // Busca todos os quizzes de um usuário ordenados por data
    List<Quiz> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Busca o último quiz de um usuário
    Optional<Quiz> findTopByUserOrderByCreatedAtDesc(User user);
}
