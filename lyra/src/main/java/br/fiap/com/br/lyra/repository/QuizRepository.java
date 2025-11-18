package br.fiap.com.br.lyra.repository;

import br.fiap.com.br.lyra.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByUserIdOrderByCreatedAtDesc(Long userId);
}
