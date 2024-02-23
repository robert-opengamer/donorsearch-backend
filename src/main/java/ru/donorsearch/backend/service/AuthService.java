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
import ru.donorsearch.backend.entity.User;
import ru.donorsearch.backend.repository.UserRepo;

import java.io.UnsupportedEncodingException;

@Service
public class AuthService {

    private final AuthHttpClient authHttpClient;

    private final UserRepo userRepo;

    @Autowired
    public AuthService(AuthHttpClient authHttpClient, UserRepo userRepo) {
        this.authHttpClient = authHttpClient;
        this.userRepo = userRepo;
    }

    public RegistrationResponse registerUser(RegistrationRequest request) throws UnsupportedEncodingException, JsonProcessingException {
        RegistrationResponse response = new RegistrationResponse(authHttpClient.registerClient(request));
        userRepo.save(new User(response.getId(), request.getEmail()));
        return response;
    }

    public ConfirmEmailResponse confirmEmail(ConfirmEmailRequest request) {

    }

    public ResponseEntity<LoginResponse> login(LoginRequest request) throws UnsupportedEncodingException, JsonProcessingException {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.add("token", authHttpClient.loginClient(request));

        LoginResponse response = new LoginResponse("ASD");

        return new ResponseEntity<>(response, params, HttpStatus.OK);
    }


}
