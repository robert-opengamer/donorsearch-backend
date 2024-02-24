package ru.donorsearch.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.donorsearch.backend.client.AuthHttpClient;
import ru.donorsearch.backend.client.DonationHttpClient;
import ru.donorsearch.backend.controller.dto.auth.*;
import ru.donorsearch.backend.entity.User;
import ru.donorsearch.backend.repository.DonationPlanRepo;
import ru.donorsearch.backend.repository.UserRepo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

@Service
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthHttpClient.class);

    private final Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    private final AuthHttpClient authHttpClient;
    private final UserRepo userRepo;
    private final ObjectMapper objectMapper;

    private final DonationPlanRepo donationPlanRepo;

    private final DonationHttpClient donationHttpClient;

    @Autowired
    public AuthService(AuthHttpClient authHttpClient, UserRepo userRepo, ObjectMapper objectMapper, DonationPlanRepo donationPlanRepo, DonationHttpClient donationHttpClient) {
        this.authHttpClient = authHttpClient;
        this.userRepo = userRepo;
        this.objectMapper = objectMapper;
        this.donationPlanRepo = donationPlanRepo;
        this.donationHttpClient = donationHttpClient;
    }

    public RegistrationResponse registerUser(RegistrationRequest request) throws UnsupportedEncodingException, JsonProcessingException {
        RegistrationResponse response = new RegistrationResponse(authHttpClient.registerClient(request));
        User user = new User();
        user.setPhoneVerified(false);
        user.setEmailVerified(false);;
        if (pattern.matcher(request.getLogin()).matches()) {
            response.setType("email");
            user.setEmail(request.getLogin());
            user.setPhoneNumber(null);
        } else {
            response.setType("phone");
            user.setPhoneNumber(request.getLogin());
            user.setEmail(null);
        }
        user.setId(response.getId());

        userRepo.save(user);
        return response;
    }

    public ResponseEntity<ResponseWithToken> confirmEmail(ConfirmEmailRequest request) throws UnsupportedEncodingException, JsonProcessingException {
        User user = userRepo.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        HttpResponse response = authHttpClient.confirmEmailClient(request);
        String token = null;
        for (Header header : response.getAllHeaders()) {
            if (header.getName().equalsIgnoreCase("token")) {
                token = header.getValue();
            }
        }

        ResponseWithToken responseWithToken = new ResponseWithToken(token);

        user.setEmailVerified(true);
        userRepo.save(user);


        return new ResponseEntity<>(responseWithToken, HttpStatus.OK);
    }

    public ResponseEntity<ResponseWithToken> confirmPhone(ConfirmPhoneRequest request) throws UnsupportedEncodingException, JsonProcessingException {
        User user = userRepo.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        HttpResponse response = authHttpClient.confirmPhoneClient(request);
        String token = null;
        for (Header header : response.getAllHeaders()) {
            if (header.getName().equalsIgnoreCase("token")) {
                token = header.getValue();
            }
        }
        ResponseWithToken responseWithToken = new ResponseWithToken(token);


        user.setPhoneVerified(true);
        userRepo.save(user);

        return new ResponseEntity<>(responseWithToken, HttpStatus.OK);
    }

    public ResponseEntity<LoginResponse> login(LoginRequest request) throws IOException {
        HttpResponse response = authHttpClient.loginClient(request);
        String token = null;
        for (Header header : response.getAllHeaders()) {
            if (header.getName().equalsIgnoreCase("token")) {
                token = header.getValue();
            }
        }

        String responseJson = EntityUtils.toString(response.getEntity());
        logger.info(responseJson);
        JsonNode jsonNode = objectMapper.readTree(responseJson);
        long id = jsonNode.get("id").asLong();
        if (!userRepo.existsById(id)) {

            String email = jsonNode.get("email").asText();
            String phone = jsonNode.get("phone").asText();
            boolean isEmailVerified = jsonNode.get("is_email_verified").asBoolean();
            boolean isPhoneVerified = jsonNode.get("is_phone_verified").asBoolean();

            User newUser = new User();
            newUser.setId(id);
            newUser.setChatId(request.getChatId());
            newUser.setEmail(email);
            newUser.setPhoneNumber(phone);
            newUser.setEmailVerified(isEmailVerified);
            newUser.setPhoneVerified(isPhoneVerified);
            newUser.getDonationPlans().addAll(donationHttpClient.getAllDonationPlans(token));
            userRepo.save(newUser);
        } else {
            User oldUser = userRepo.findById(id).get();
            oldUser.setChatId(request.getChatId());
            userRepo.save(oldUser);
        }
        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }


}
