package ru.donorsearch.backend.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.donorsearch.backend.controller.dto.LoginRequest;
import ru.donorsearch.backend.controller.dto.RegistrationRequest;

@Component
public class AuthHttpClient {

    private final Logger logger = LoggerFactory.getLogger(AuthHttpClient.class);
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    private final String LOGIN_URI;
    private final String REG_URI;

    public AuthHttpClient(CloseableHttpClient httpClient,
                          ObjectMapper objectMapper,
                          @Value("${spring.data.auth.login}") String LOGIN_URI,
                          @Value("${spring.data.auth.reg}") String REG_URI) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.LOGIN_URI = LOGIN_URI;
        this.REG_URI = REG_URI;
    }

    public long registerClient(RegistrationRequest request) throws UnsupportedEncodingException, JsonProcessingException {

        HttpPost httpPost = new HttpPost(REG_URI);
        httpPost.setHeader("Content-Type", "application/json");

        String requestJson = objectMapper.writeValueAsString(request);
        httpPost.setEntity(new StringEntity(requestJson));

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("Retrieve response from: " + REG_URI);
            if (statusCode == 200) {
                JsonNode jsonNode = objectMapper.readTree(EntityUtils.toString(response.getEntity()));
                return jsonNode.get("user_id").asLong();
            } else {
                return -1;
            }

        } catch (IOException e) {
            logger.error("Error occurred with send POST to: " + REG_URI);
            throw new RuntimeException(e);
        }
    }

    public String loginClient(LoginRequest request) throws UnsupportedEncodingException, JsonProcessingException {

        HttpPost httpPost = new HttpPost(LOGIN_URI);
        httpPost.setHeader("Content-Type", "application/json");

        String requestJson = objectMapper.writeValueAsString(request);
        httpPost.setEntity(new StringEntity(requestJson));

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("Retrieve response from: " + LOGIN_URI);
            if (statusCode == 200) {
                JsonNode jsonNode = objectMapper.readTree(EntityUtils.toString(response.getEntity()));
                return jsonNode.get("token").asText();
            } else {
                return null;
            }

        } catch (IOException e) {
            logger.error("Error occurred with send POST to: " + LOGIN_URI);
            throw new RuntimeException(e);
        }
    }
}
