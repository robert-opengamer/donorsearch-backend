package ru.donorsearch.backend.controller.dto.auth;

public class RegistrationResponse {
    private Long id;

    private String type;

    public RegistrationResponse(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RegistrationResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
