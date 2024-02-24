package ru.donorsearch.backend.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.BadRequestException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.donorsearch.backend.controller.dto.donation.DonationPlanDTO;

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

        HttpDelete httpDelete = new HttpDelete(DONATION_PLAN_URI + id);
        httpDelete.setHeader("Authorization", token);

        try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("Retrieve response from: {}", DONATION_PLAN_URI);
            logger.info("Status Code: {}", statusCode);
            if (statusCode == 200) {
                JsonNode jsonNode = objectMapper.readTree(EntityUtils.toString(response.getEntity()));
                return jsonNode.get("id").asLong();
            } else {
                throw new BadRequestException("Invalid path variable");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
