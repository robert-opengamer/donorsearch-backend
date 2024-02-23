package ru.donorsearch.backend.controller.dto;

public class LoginResponse {
    private String msg;

    public LoginResponse(String msg) {
        this.msg = msg;
    }

    public LoginResponse() {
    }


    public String getToken() {
        return msg;
    }

    public void setToken(String msg) {
        this.msg = msg;
    }

}
