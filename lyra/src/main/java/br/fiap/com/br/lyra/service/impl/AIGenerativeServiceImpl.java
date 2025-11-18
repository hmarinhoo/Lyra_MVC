package br.fiap.com.br.lyra.service.impl;

import br.fiap.com.br.lyra.service.AIGenerativeService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "ai.provider", havingValue = "none", matchIfMissing = true)
public class AIGenerativeServiceImpl implements AIGenerativeService {

    @Override
    @Cacheable(value = "generatedTrails", key = "#profile + ':' + (#context == null ? '' : #context.hashCode())")
    public String generateCareerTrail(String profile, String context) {
        // TODO: Replace stub with Spring AI provider integration.
        // For the MVP return a structured markdown as placeholder.
        StringBuilder sb = new StringBuilder();
        sb.append("# Trilha de Aprendizado para perfil: ").append(profile).append("\n\n");
        sb.append("Descrição: Uma trilha focada em desenvolvimento de habilidades técnicas alinhadas a impacto social.\n\n");
        sb.append("## Áreas promissoras\n");
        sb.append("- Tecnologia para energias renováveis\n");
        sb.append("- Dados e IA aplicada ao setor ambiental\n");
        sb.append("- Gestão de projetos sustentáveis\n\n");
        sb.append("## Cursos e certificações recomendadas\n");
        sb.append("- Curso A: Fundamentos de Energia Limpa\n");
        sb.append("- Certificação B: Data Science para Sustentabilidade\n\n");
        sb.append("## Passos (0-2 anos)\n");
        sb.append("1. Aprender bases de programação e análise de dados\n");
        sb.append("2. Fazer curso de energia renovável\n\n");
        sb.append("## Simulação 2030\n");
        sb.append("- Cenário otimista: forte demanda por engenheiros de dados ambientais\n");
        sb.append("- Cenário conservador: transição gradual com oportunidades em empresas de consultoria\n\n");
        sb.append("## Impacto ODS\n");
        sb.append("- ODS 7: Energia Limpa e Acessível\n");
        sb.append("- ODS 13: Ação contra a Mudança Global do Clima\n");

        return sb.toString();
    }
}
