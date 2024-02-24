package ru.donorsearch.backend.controller.dto.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.donorsearch.backend.config.RegistrationSerializer;

@JsonSerialize(using = RegistrationSerializer.class)
public class RegistrationRequest {
    private String login;
    @JsonProperty("first_name")
    private String firstName;
    private String password;
    private String tag;

    public RegistrationRequest(String login, String firstName, String password, String tag) {
        this.login = login;
        this.firstName = firstName;
        this.password = password;
        this.tag = tag;
    }

    public RegistrationRequest() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String email) {
        this.login = email;
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
