package br.fiap.com.br.lyra.repository;

import br.fiap.com.br.lyra.dto.QuizDTO;
import br.fiap.com.br.lyra.model.Quiz;
import br.fiap.com.br.lyra.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // Busca todos os quizzes de um usuário ordenados por data
    List<Quiz> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Busca o último quiz de um usuário
    Optional<Quiz> findTopByUserOrderByCreatedAtDesc(User user);

    @Query("""
        SELECT new br.fiap.com.br.lyra.dto.QuizDTO(
            q.id,
            q.profile,
            q.answersJson,
            q.createdAt
        )
        FROM Quiz q
        WHERE q.user.id = :userId
        ORDER BY q.createdAt DESC
    """)
    QuizDTO findLatestQuizDTO(Long userId);
}
