package ru.donorsearch.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    public ConfirmEmailResponse confirmEmail(ConfirmEmailRequest request) throws UnsupportedEncodingException, JsonProcessingException {
        return new ConfirmEmailResponse(authHttpClient.confirmEmailClient(request));
    }

    public ResponseEntity<LoginResponse> login(LoginRequest request) throws UnsupportedEncodingException, JsonProcessingException {
        String token = authHttpClient.loginClient(request);

        Duration cookieDuration = Duration.ofHours(1);
        ZonedDateTime expiresDateTime = ZonedDateTime.now().plus(cookieDuration);
        String expiresFormatted = expiresDateTime.format(DateTimeFormatter.RFC_1123_DATE_TIME);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, String.format("token=%s; expires=%s", token, expiresFormatted));

        return new ResponseEntity<>(new LoginResponse(token), headers, HttpStatus.OK);
    }


}
