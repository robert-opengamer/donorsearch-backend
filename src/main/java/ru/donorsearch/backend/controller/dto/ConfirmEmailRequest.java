package ru.donorsearch.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfirmEmailRequest {
    @JsonProperty("user_id")
    private Long userId;

    private String email;

    private String code;

    public ConfirmEmailRequest(Long userId, String email, String code) {
        this.userId = userId;
        this.email = email;
        this.code = code;
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
}
