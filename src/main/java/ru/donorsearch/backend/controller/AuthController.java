package ru.donorsearch.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.donorsearch.backend.controller.dto.auth.ConfirmEmailRequest;
import ru.donorsearch.backend.controller.dto.auth.ConfirmPhoneRequest;
import ru.donorsearch.backend.controller.dto.auth.LoginRequest;
import ru.donorsearch.backend.controller.dto.auth.LoginResponse;
import ru.donorsearch.backend.controller.dto.auth.RegistrationRequest;
import ru.donorsearch.backend.controller.dto.auth.RegistrationResponse;
import ru.donorsearch.backend.controller.dto.auth.ResponseWithToken;
import ru.donorsearch.backend.service.AuthService;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponse> registration(@RequestBody RegistrationRequest request) throws UnsupportedEncodingException, JsonProcessingException {
        System.out.println(request.getLogin());
        return new ResponseEntity<>(authService.registerUser(request), HttpStatus.OK);
    }

    @PostMapping("/confirm_email_reg")
    public ResponseEntity<ResponseWithToken> confirmEmailReg(@RequestBody ConfirmEmailRequest request) throws UnsupportedEncodingException, JsonProcessingException {
        return authService.confirmEmail(request);
    }

    @PostMapping("/confirm_phone_reg")
    public ResponseEntity<ResponseWithToken> confirmPhoneReg(@RequestBody ConfirmPhoneRequest request) throws UnsupportedEncodingException, JsonProcessingException {
        return authService.confirmPhone(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws IOException {
        return authService.login(request);
    }

    @GetMapping("/test")
    public String test() {
        return "OK";
    }

}
