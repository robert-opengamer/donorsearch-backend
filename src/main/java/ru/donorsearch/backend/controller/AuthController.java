package ru.donorsearch.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.donorsearch.backend.controller.dto.*;
import ru.donorsearch.backend.service.AuthService;

@RestController("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponse> registration(@RequestBody RegistrationRequest request) {
        return new ResponseEntity<>(authService.registerUser(request), HttpStatus.OK);
    }

    @PostMapping("/confirm_email_reg")
    public ResponseEntity<ConfirmEmailResponse> confirmEmailReg(@RequestBody ConfirmEmailRequest request) {
        return new ResponseEntity<>(authService.confirmEmail(request), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }


}
