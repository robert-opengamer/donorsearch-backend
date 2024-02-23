package ru.donorsearch.backend.controller.dto;

public class RegistrationResponse {
    private Long id;

    public RegistrationResponse(Long id) {
        this.id = id;
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
