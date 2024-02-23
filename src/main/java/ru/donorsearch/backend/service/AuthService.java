package ru.donorsearch.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import ru.donorsearch.backend.client.AuthHttpClient;
import ru.donorsearch.backend.controller.dto.*;

import java.io.UnsupportedEncodingException;

@Service
public class AuthService {

    private final AuthHttpClient authHttpClient;

    @Autowired
    public AuthService(AuthHttpClient authHttpClient) {
        this.authHttpClient = authHttpClient;
    }

    public RegistrationResponse registerUser(RegistrationRequest request) throws UnsupportedEncodingException, JsonProcessingException {
        return new RegistrationResponse(authHttpClient.registerClient(request));
    }

//    public ConfirmEmailResponse confirmEmail(ConfirmEmailRequest request) {
//
//    }

    public ResponseEntity<LoginResponse> login(LoginRequest request) throws UnsupportedEncodingException, JsonProcessingException {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.add("token", authHttpClient.loginClient(request));

        LoginResponse response = new LoginResponse("ASD");

        return new ResponseEntity<>(response, params, HttpStatus.OK);
    }


}
