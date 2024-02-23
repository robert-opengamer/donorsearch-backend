package ru.donorsearch.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegByEmail {
    private String email;

    @JsonProperty("first_name")
    private String firstName;
    private String password;
    private String tag;

    public RegByEmail(String email, String firstName, String password, String tag) {
        this.email = email;
        this.firstName = firstName;
        this.password = password;
        this.tag = tag;
    }

    public RegByEmail() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
