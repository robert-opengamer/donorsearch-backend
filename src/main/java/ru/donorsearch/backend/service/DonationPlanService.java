package ru.donorsearch.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.donorsearch.backend.client.AuthHttpClient;
import ru.donorsearch.backend.client.DonationHttpClient;
import ru.donorsearch.backend.config.StatusResponse;
import ru.donorsearch.backend.controller.dto.donation.DonationPlanDTO;
import ru.donorsearch.backend.controller.dto.donation.StationDTO;
import ru.donorsearch.backend.entity.DonationPlan;
import ru.donorsearch.backend.entity.User;
import ru.donorsearch.backend.repository.DonationPlanRepo;
import ru.donorsearch.backend.repository.UserRepo;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class DonationPlanService {

    private static final Logger logger = LoggerFactory.getLogger(DonationPlanService.class);

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

        donationPlanDTO.setPlanDate(convertDate(donationPlanDTO.getPlanDate()));

        User user = userRepo.findById(authHttpClient.getUserIdFromSession(token)).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        Long id = donationHttpClient.createDonationPlanAndGetId(donationPlanDTO, token);
        StatusResponse statusResponse = new StatusResponse("OK");

        DonationPlan donationPlan = new DonationPlan(
                id,
                donationPlanDTO.getBloodStationId(),
                donationPlanDTO.getCityId(),
                donationPlanDTO.getBloodClass(),
                donationPlanDTO.getPlanDate(),
                donationPlanDTO.getPaymentType(),
                donationPlanDTO.isOut(),
                donationPlanDTO.getStation().getTitle(),
                parseAddress(donationPlanDTO.getStation()),
                donationPlanDTO.getStation().getPhones(),
                donationPlanDTO.getStation().getWorktime());
        donationPlan.setUser(user);
        user.getDonationPlans().add(donationPlan);
        userRepo.save(user);

        donationPlanRepository.save(donationPlan);
        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }

    public ResponseEntity<StatusResponse> deleteDonationPlan(String token, Long id) {
        donationPlanRepository.deleteById(id);
        Long deletedId = donationHttpClient.deleteDonationPlanById(id, token);
        StatusResponse statusResponse = new StatusResponse("OK");

        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }

    private String convertDate(String inputDate) {
        logger.info(inputDate);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(inputDate, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(outputFormatter);
    }

    private String parseAddress(StationDTO station) {
        String cityTitle = station.getCity().getTitle();
        String address = cityTitle + ", " + station.getAddress();
        logger.info(address);
        return address;
    }

    @Scheduled(cron = "0 59 23 * * *")
    public void updateDonationPlans() {
        LocalDate currentDate = LocalDate.now();

        String date = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<DonationPlan> donationPlans = donationPlanRepository.findDonationPlansByPlanDate(date);

        donationPlanRepository.deleteAll(donationPlans);
    }

}
