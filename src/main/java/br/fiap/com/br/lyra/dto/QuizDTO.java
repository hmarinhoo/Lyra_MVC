package br.fiap.com.br.lyra.dto;


import java.time.Instant;

public record QuizDTO(
        Long id,
        String profile,
        String answersJson,
        Instant createdAt
) {}

