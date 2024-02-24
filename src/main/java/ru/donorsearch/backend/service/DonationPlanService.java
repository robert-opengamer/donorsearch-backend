package ru.donorsearch.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.donorsearch.backend.client.AuthHttpClient;
import ru.donorsearch.backend.client.DonationHttpClient;
import ru.donorsearch.backend.config.StatusResponse;
import ru.donorsearch.backend.controller.dto.donation.DonationPlanDTO;
import ru.donorsearch.backend.entity.DonationPlan;
import ru.donorsearch.backend.entity.User;
import ru.donorsearch.backend.repository.DonationPlanRepo;
import ru.donorsearch.backend.repository.UserRepo;

import java.io.UnsupportedEncodingException;

@Service
public class DonationPlanService {

    private final UserRepo userRepo;

    private final AuthHttpClient authHttpClient;

    private final DonationHttpClient donationHttpClient;

    private final DonationPlanRepo donationPlanRepository;

    @Autowired
    public DonationPlanService(UserRepo userRepo, AuthHttpClient authHttpClient, DonationHttpClient donationHttpClient, DonationPlanRepo donationPlanRepository) {
        this.userRepo = userRepo;
        this.authHttpClient = authHttpClient;
        this.donationHttpClient = donationHttpClient;
        this.donationPlanRepository = donationPlanRepository;
    }

    public ResponseEntity<StatusResponse> createDonationPlan(String token, DonationPlanDTO donationPlanDTO)
            throws UnsupportedEncodingException, JsonProcessingException {
        User user = userRepo.findById(authHttpClient.getUserIdFromSession(token)).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        Long id = donationHttpClient.createDonationPlanAndGetId(donationPlanDTO, token);
        StatusResponse statusResponse = new StatusResponse("OK");

        DonationPlan donationPlan = new DonationPlan(id, donationPlanDTO.getBloodStationId(),
                donationPlanDTO.getCityId(), donationPlanDTO.getBloodClass(),
                donationPlanDTO.getPlanDate(), donationPlanDTO.getPaymentType(),
                donationPlanDTO.isOut());
        donationPlan.setUser(user);
        user.getDonationPlans().add(donationPlan);
        userRepo.save(user);

        donationPlanRepository.save(donationPlan);
        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }

    public ResponseEntity<StatusResponse> deleteDonationPlan(String token, Long id) {

        Long deletedId = donationHttpClient.deleteDonationPlanById(id, token);
        StatusResponse statusResponse = new StatusResponse("OK");
        donationPlanRepository.deleteById(deletedId);

        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }

    /*public ResponseEntity<StatusResponse> updateDonationPlan(String token, DonationPlanResponse donationPlanResponse) {

    }

    public ResponseEntity<StatusResponse> getDonationPlan(String token, DonationPlanResponse donationPlanResponse) {

    }*/
}
