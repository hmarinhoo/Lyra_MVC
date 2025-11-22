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
        Você é um assistente de carreira que cria trilhas de desenvolvimento personalizadas e inspiradoras para usuários com base no perfil e nas respostas do quiz.

        Gere uma trilha de carreira detalhada e motivadora, usando um **tom amigável e encorajador**. A resposta deve conter duas versões: Português (PT) e Inglês (EN), cada uma organizada de forma clara, com subtítulos e listas fáceis de ler.

        Perfil do usuário:
        %s

        Para cada língua, inclua as seguintes seções:

        1️⃣ Áreas recomendadas / Recommended fields
        - Liste 3 a 5 áreas alinhadas ao perfil e interesses do usuário.
        - Explique brevemente por que cada área é adequada, com frases curtas e motivadoras.

        2️⃣ Cursos sugeridos / Suggested courses
        - Liste 3 a 5 cursos online ou presenciais relevantes.
        - Dê uma pequena dica de como o curso ajuda no desenvolvimento de habilidades.

        3️⃣ Profissões do futuro / Future careers
        - Indique 3 a 5 profissões emergentes relacionadas às áreas recomendadas.
        - Descreva em uma frase o que cada profissão envolve.

        4️⃣ Conexão com os ODS / Connection with SDGs
        - Mostre como a carreira pode contribuir para pelo menos 2 Objetivos de Desenvolvimento Sustentável (ODS).

        Formate a saída **como texto estruturado e visualmente agradável**:
        - Use subtítulos (por exemplo: "🎯 Áreas recomendadas").
        - Use listas com emojis ou bullets.
        - Mantenha frases curtas, positivas e coerentes com as respostas do quiz.
        - Comece sempre com PT, depois EN.

        Não inclua instruções ou explicações fora das seções solicitadas.
        """.formatted(profileText);


        return chatModel.call(prompt);
    }
}
