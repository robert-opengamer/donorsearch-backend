package ru.donorsearch.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.donorsearch.backend.client.AuthHttpClient;
import ru.donorsearch.backend.controller.dto.*;
import ru.donorsearch.backend.entity.User;
import ru.donorsearch.backend.repository.UserRepo;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Service
public class AuthService {

    private final Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    private final AuthHttpClient authHttpClient;

    private final UserRepo userRepo;

    @Autowired
    public AuthService(AuthHttpClient authHttpClient, UserRepo userRepo) {
        this.authHttpClient = authHttpClient;
        this.userRepo = userRepo;
    }

    public RegistrationResponse registerUser(RegistrationRequest request) throws UnsupportedEncodingException, JsonProcessingException {
        User user = new User();
        user.setPhoneVerified(false);
        user.setEmailVerified(false);;
        if (pattern.matcher(request.getLogin()).matches()) {
            user.setEmail(request.getLogin());
            user.setPhoneNumber(null);
        } else {
            user.setPhoneNumber(request.getLogin());
            user.setEmail(null);
        }
        RegistrationResponse response = new RegistrationResponse(authHttpClient.registerClient(request));
        user.setId(response.getId());


        userRepo.save(user);
        return response;
    }


    public ConfirmEmailResponse confirmEmail(ConfirmEmailRequest request) throws UnsupportedEncodingException, JsonProcessingException {
        User user = userRepo.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        ConfirmEmailResponse response = new ConfirmEmailResponse(authHttpClient.confirmEmailClient(request));

        user.setEmailVerified(true);
        userRepo.delete(user);
        userRepo.save(user);

        return response;
    }

    public ConfirmPhoneResponse confirmPhone(ConfirmPhoneRequest request) throws UnsupportedEncodingException, JsonProcessingException {
        User user = userRepo.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        ConfirmPhoneResponse response = new ConfirmPhoneResponse(authHttpClient.confirmPhoneClient(request));

        user.setPhoneVerified(true);
        userRepo.delete(user);
        userRepo.save(user);

        return response;
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
