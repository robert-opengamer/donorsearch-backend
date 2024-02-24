package ru.donorsearch.backend.controller.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfirmEmailRequest {
    @JsonProperty("user_id")
    private Long userId;
    private String email;
    private String code;
    @JsonProperty("chat_id")
    private long chatId;

    public ConfirmEmailRequest(Long userId, String email, String code, long chatId) {
        this.userId = userId;
        this.email = email;
        this.code = code;
        this.chatId = chatId;
    }

    public ConfirmEmailRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}
