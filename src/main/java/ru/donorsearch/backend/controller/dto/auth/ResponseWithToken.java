package ru.donorsearch.backend.controller.dto.auth;

public class ResponseWithToken {
    private String token;

    public ResponseWithToken(String token) {
        this.token = token;
    }

    public ResponseWithToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
