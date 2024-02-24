package ru.donorsearch.backend.client;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Value;

public class DonationHttpClient {

    private final CloseableHttpClient httpClient;
    private final String DONATION_PLAN_URI;

    public DonationHttpClient(CloseableHttpClient httpClient,
                              @Value("${spring.data.uri.donation.donation-plan}") String DONATION_PLAN_URI) {
        this.httpClient = httpClient;
        this.DONATION_PLAN_URI = DONATION_PLAN_URI;
    }


}
