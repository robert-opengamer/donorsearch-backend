package ru.donorsearch.backend.exceptions.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.BadRequestException;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.donorsearch.backend.controller.dto.auth.AuthErrorResponse;
import ru.donorsearch.backend.exceptions.AuthException;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AuthErrorResponse> authExceptionHandler(AuthException e, HttpServletRequest req) {

        AuthErrorResponse error = new AuthErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                req.getRequestURI(),
                e.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<AuthErrorResponse> badRequestExceptionHandler(BadRequestException e, HttpServletRequest req) {

        AuthErrorResponse error = new AuthErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                req.getRequestURI(),
                e.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
