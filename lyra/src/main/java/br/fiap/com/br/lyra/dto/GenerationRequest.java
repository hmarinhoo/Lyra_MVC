package br.fiap.com.br.lyra.dto;

import java.io.Serializable;

public class GenerationRequest implements Serializable {
    private Long quizId;
    private Long userId;
    private String profile;
    private String context;

    public GenerationRequest() {}

    public GenerationRequest(Long quizId, Long userId, String profile, String context) {
        this.quizId = quizId;
        this.userId = userId;
        this.profile = profile;
        this.context = context;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
