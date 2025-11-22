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
            Gere uma trilha de carreira detalhada baseada no perfil abaixo. 
            A resposta deve conter duas versões: Português (PT) e Inglês (EN).

            Perfil do usuário:
            %s

            Retorne as seguintes informações para cada língua:
            - Áreas recomendadas / Recommended fields
            - Cursos sugeridos / Suggested courses
            - Profissões do futuro / Future careers
            - Conexão com os ODS / Connection with SDGs

            Formate como texto claro separado por idioma, primeiro PT, depois EN.
            """.formatted(profileText);

        return chatModel.call(prompt);
    }
}
