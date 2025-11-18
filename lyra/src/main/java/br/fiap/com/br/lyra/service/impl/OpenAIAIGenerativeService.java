package br.fiap.com.br.lyra.service.impl;

import br.fiap.com.br.lyra.service.AIGenerativeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@ConditionalOnProperty(name = "ai.provider", havingValue = "openai")
public class OpenAIAIGenerativeService implements AIGenerativeService {

    @Value("${ai.openai.api-key:}")
    private String apiKey;

    @Value("${ai.openai.model:gpt-4o-mini}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String generateCareerTrail(String profile, String context) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("OpenAI API key is not configured (OPENAI_API_KEY)");
        }

        String prompt = buildPrompt(profile, context);

        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("messages", new Object[]{message});
        body.put("max_tokens", 1200);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> resp = restTemplate.postForEntity(url, request, String.class);
        try {
            JsonNode root = mapper.readTree(resp.getBody());
            JsonNode choices = root.path("choices");
            if (choices.isArray() && choices.size() > 0) {
                JsonNode messageNode = choices.get(0).path("message").path("content");
                if (!messageNode.isMissingNode()) {
                    return messageNode.asText();
                }
            }
            return "";
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse OpenAI response", e);
        }
    }

    private String buildPrompt(String profile, String context) {
        StringBuilder sb = new StringBuilder();
        sb.append("Gere uma trilha de aprendizado para o seguinte perfil profissional:\n");
        sb.append("Perfil: ").append(profile).append("\n\n");
        sb.append("Contexto/Respostas do quiz: \n").append(context == null ? "(sem contexto)" : context).append("\n\n");
        sb.append("A trilha deve incluir: áreas promissoras, cursos e certificações recomendadas, uma simulação de evolução até 2030, e quais ODS serão impactados. Responda em português e formate em markdown.\n");
        return sb.toString();
    }
}
