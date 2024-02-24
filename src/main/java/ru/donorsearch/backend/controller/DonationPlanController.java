package ru.donorsearch.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.donorsearch.backend.config.StatusResponse;
import ru.donorsearch.backend.controller.dto.donation.DonationPlanDTO;
import ru.donorsearch.backend.service.DonationPlanService;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/donation_plan")
public class DonationPlanController {

    private final DonationPlanService donationPlanService;

    @Autowired
    public DonationPlanController(DonationPlanService donationPlanService) {
        this.donationPlanService = donationPlanService;
    }


    @PostMapping
    public ResponseEntity<StatusResponse> createDonationPlan(@RequestHeader("Authorization") String token,
                                                             @RequestBody DonationPlanDTO donationPlanDTO) throws UnsupportedEncodingException, JsonProcessingException {
        return donationPlanService.createDonationPlan(token, donationPlanDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StatusResponse> deleteDonationPlan(@RequestHeader("Authorization") String token,
                                                             @PathVariable Long id) {
        return donationPlanService.deleteDonationPlan(token, id);
    }



}
