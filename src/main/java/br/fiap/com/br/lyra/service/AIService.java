package br.fiap.com.br.lyra.service;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final OpenAiChatModel chatModel;

    public AIService(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String generateCareerTrail(String profileText) {

        String prompt = """
            Gere uma trilha de carreira detalhada baseada no perfil abaixo:

            Perfil do usuário:
            %s

            Retorne:
            - áreas recomendadas
            - cursos sugeridos
            - profissões do futuro
            - conexão com os ODS
            """.formatted(profileText);

        return chatModel.call(prompt);
    }
}
