package ru.donorsearch.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.donorsearch.backend.service.DonationPlanService;

@RestController
@RequestMapping("/api/donation_plan")
public class DonationPlanController {

    private final DonationPlanService donationPlanService;

    @Autowired
    public DonationPlanController(DonationPlanService donationPlanService) {
        this.donationPlanService = donationPlanService;
    }


//    @PostMapping
//    public ResponseEntity<DonationPlanResponse> createDonationPlan(@RequestBody DonationPlanResponse donationPlanResponse) {
//
//    }



}
