package ru.donorsearch.backend.controller.dto;

public class ConfirmEmailResponse {
    private Long id;

    public ConfirmEmailResponse(Long id) {
        this.id = id;
    }

    public ConfirmEmailResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
