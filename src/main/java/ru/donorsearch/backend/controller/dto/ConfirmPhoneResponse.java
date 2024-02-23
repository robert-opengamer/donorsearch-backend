package ru.donorsearch.backend.controller.dto;

public class ConfirmPhoneResponse {
    private Long id;

    public ConfirmPhoneResponse(Long id) {
        this.id = id;
    }

    public ConfirmPhoneResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
