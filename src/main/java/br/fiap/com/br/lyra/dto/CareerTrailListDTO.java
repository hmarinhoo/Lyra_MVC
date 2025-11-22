package br.fiap.com.br.lyra.dto;

import java.time.Instant;

public record CareerTrailListDTO(
    Long id,
    String profile,
    Instant createdAt
) {}