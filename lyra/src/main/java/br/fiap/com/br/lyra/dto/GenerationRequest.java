package br.fiap.com.br.lyra.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerationRequest implements Serializable {
    private Long quizId;
    private Long userId;
    private String profile;
    private String context;

}
