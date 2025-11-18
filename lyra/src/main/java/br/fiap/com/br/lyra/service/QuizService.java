package br.fiap.com.br.lyra.service;

import br.fiap.com.br.lyra.model.CareerTrail;
import br.fiap.com.br.lyra.model.Quiz;

public interface QuizService {
    Quiz submitQuiz(Long userId, String answersJson);
    CareerTrail generateAndSaveTrail(Quiz quiz);
}
