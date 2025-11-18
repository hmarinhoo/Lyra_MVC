package br.fiap.com.br.lyra.service;

public interface AIGenerativeService {
    /**
     * Generate a career trail given a profile (e.g., "técnico") and optional context.
     * Implementation should call a generative AI provider (Spring AI) or a queued job.
     */
    String generateCareerTrail(String profile, String context);
}
