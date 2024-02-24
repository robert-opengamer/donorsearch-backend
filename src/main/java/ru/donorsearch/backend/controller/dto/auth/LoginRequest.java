package ru.donorsearch.backend.controller.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {
    private String username;
    private String password;
    @JsonProperty("chat_id")
    private long chatId;

    public LoginRequest(String username, String password, long chatId) {
        this.username = username;
        this.password = password;
        this.chatId = chatId;
    }


    public LoginRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}
