package ru.donorsearch.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegByPhone {
    private String phone;

    @JsonProperty("first_name")
    private String firstName;
    private String password;
    private String tag;

    public RegByPhone(String phone, String firstName, String password, String tag) {
        this.phone = phone;
        this.firstName = firstName;
        this.password = password;
        this.tag = tag;
    }

    public RegByPhone() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
