package ru.donorsearch.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfirmPhoneRequest {
    @JsonProperty("user_id")
    private Long userId;

    private String phone;

    private String code;

    public ConfirmPhoneRequest(Long userId, String phone, String code) {
        this.userId = userId;
        this.phone = phone;
        this.code = code;
    }

    public ConfirmPhoneRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
