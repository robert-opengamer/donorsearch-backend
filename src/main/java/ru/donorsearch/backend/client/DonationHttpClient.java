package ru.donorsearch.backend.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.BadRequestException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.donorsearch.backend.controller.dto.donation.DonationPlanDTO;
import ru.donorsearch.backend.entity.DonationPlan;

@Component
public class DonationHttpClient {

    private final static Logger logger = LoggerFactory.getLogger(DonationHttpClient.class);

    private final CloseableHttpClient httpClient;
    private final String DONATION_PLAN_URI;
    private final ObjectMapper objectMapper;

    public DonationHttpClient(CloseableHttpClient httpClient,
                              ObjectMapper objectMapper,
                              @Value("${spring.data.uri.donation.donation-plan}") String DONATION_PLAN_URI) {
        this.httpClient = httpClient;
        this.DONATION_PLAN_URI = DONATION_PLAN_URI;
        this.objectMapper = objectMapper;
    }

    public long createDonationPlanAndGetId(DonationPlanDTO request, String token) throws JsonProcessingException, UnsupportedEncodingException {

        HttpPost httpPost = new HttpPost(DONATION_PLAN_URI);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", token);

        String json = objectMapper.writeValueAsString(request);
        StringEntity entity = new StringEntity(objectMapper.writeValueAsString(request));
        httpPost.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("Retrieve response from: {}", DONATION_PLAN_URI);
            logger.info(json);
            logger.info("Status Code: {}", statusCode);
            if (statusCode == 201) {
                JsonNode jsonNode = objectMapper.readTree(EntityUtils.toString(response.getEntity()));
                return jsonNode.get("id").asLong();
            } else {
                throw new BadRequestException("Invalid request body");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public long deleteDonationPlanById(long id, String token) {

        HttpDelete httpDelete = new HttpDelete(DONATION_PLAN_URI + id + "/");
        httpDelete.setHeader("Authorization", token);

        try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("Retrieve response from: {}", DONATION_PLAN_URI);
            logger.info("Status Code: {}", statusCode);
            if (statusCode == 204) {
                JsonNode jsonNode = objectMapper.readTree(EntityUtils.toString(response.getEntity()));
                return jsonNode.get("id").asLong();
            } else {
                throw new BadRequestException("Invalid path variable");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DonationPlan> getAllDonationPlans(String token) {
        HttpGet httpGet = new HttpGet(DONATION_PLAN_URI);
        httpGet.setHeader("Authorization", token);

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("Retrieve response from: {}", DONATION_PLAN_URI);
            logger.info("Status Code: {}", statusCode);
            if (statusCode == 200) {
                List<DonationPlan> donationPlans = new ArrayList<>();
                JsonNode rootNode = objectMapper.readTree(EntityUtils.toString(response.getEntity()));
                JsonNode resultsNode = rootNode.get("results");
                if (resultsNode.isArray()) {
                    for (JsonNode donationPlanNode : resultsNode) {
                        DonationPlan donationPlan = mapJsonToDonationPlan(donationPlanNode);
                        donationPlans.add(donationPlan);
                    }
                }
                return donationPlans;
            } else {
                throw new BadRequestException("Invalid path variable");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private DonationPlan mapJsonToDonationPlan(JsonNode jsonNode) {
        DonationPlan donationPlan = new DonationPlan();
        donationPlan.setId(jsonNode.get("id").asLong());
        donationPlan.setBloodStationId(jsonNode.get("blood_station_id").asInt());
        donationPlan.setCityId(jsonNode.get("city_id").asInt());
        donationPlan.setBloodClass(jsonNode.get("blood_class").asText());
        donationPlan.setPlanDate(jsonNode.get("plan_date").asText());
        donationPlan.setPaymentType(jsonNode.get("payment_type").asText());
        donationPlan.setOut(jsonNode.get("is_out").asBoolean());

        return donationPlan;
    }
}
