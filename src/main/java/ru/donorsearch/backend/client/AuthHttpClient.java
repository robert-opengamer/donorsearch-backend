package ru.donorsearch.backend.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.BadRequestException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.donorsearch.backend.controller.dto.ConfirmEmailRequest;
import ru.donorsearch.backend.controller.dto.ConfirmPhoneRequest;
import ru.donorsearch.backend.controller.dto.LoginRequest;
import ru.donorsearch.backend.controller.dto.RegistrationRequest;
import ru.donorsearch.backend.exceptions.AuthException;

@Component
public class AuthHttpClient {

    private final Logger logger = LoggerFactory.getLogger(AuthHttpClient.class);
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    private final String LOGIN_URI;
    private final String REG_URI;
    private final String CONFIRM_EMAIL_URI;
    private final String CONFIRM_PHONE_URI;

    public AuthHttpClient(CloseableHttpClient httpClient,
                          ObjectMapper objectMapper,
                          @Value("${spring.data.auth.login}") String LOGIN_URI,
                          @Value("${spring.data.auth.reg}") String REG_URI,
                          @Value("${spring.data.auth.confirm-email}") String CONFIRM_EMAIL_URI,
                          @Value("${spring.data.auth.confirm-phone}") String CONFIRM_PHONE_URI) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.LOGIN_URI = LOGIN_URI;
        this.REG_URI = REG_URI;
        this.CONFIRM_EMAIL_URI = CONFIRM_EMAIL_URI;
        this.CONFIRM_PHONE_URI = CONFIRM_PHONE_URI;
    }

    public long registerClient(RegistrationRequest request) throws UnsupportedEncodingException, JsonProcessingException {

        HttpPost httpPost = new HttpPost(REG_URI);
        httpPost.setHeader("Content-Type", "application/json");

        String requestJson = objectMapper.writeValueAsString(request);
        logger.info(requestJson);
        httpPost.setEntity(new StringEntity(requestJson));

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("Retrieve response from: {}", REG_URI);
            logger.info("Status code: {}", statusCode);
            if (statusCode == 200) {
                JsonNode jsonNode = objectMapper.readTree(EntityUtils.toString(response.getEntity()));
                return jsonNode.get("user_id").asLong();
            } else {
                throw new BadRequestException("Bad Request (Invalid email/phone)");
            }

        } catch (IOException e) {
            logger.error("Error occurred with send POST to: {}", REG_URI);
            throw new RuntimeException(e);
        }
    }

    public HttpResponse loginClient(LoginRequest request) throws IOException {

        HttpPost httpPost = new HttpPost(LOGIN_URI);
        httpPost.setHeader("Content-Type", "application/json");

        String requestJson = objectMapper.writeValueAsString(request);
        httpPost.setEntity(new StringEntity(requestJson));
        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        logger.info("Retrieve response from: " + LOGIN_URI);
        logger.info(response.toString());
        logger.info(requestJson);
        logger.info("Status code: {}", statusCode);
        if (statusCode == 200) {
            return response;
        } else {
            throw new AuthException("Invalid login or password");
        }

    }

    public HttpResponse confirmEmailClient(ConfirmEmailRequest request) throws JsonProcessingException, UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(CONFIRM_EMAIL_URI);
        httpPost.setHeader("Content-Type", "application/json");

        String requestJson = objectMapper.writeValueAsString(request);
        httpPost.setEntity(new StringEntity(requestJson));

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("Retrieve response from: {}", CONFIRM_EMAIL_URI);
            logger.info("Status code: {}", statusCode);
            if (statusCode == 200) {
                return response;
            } else {
                throw new AuthException("Invalid login or password");
            }

        } catch (IOException e) {
            logger.error("Error occurred with send POST to: " + CONFIRM_EMAIL_URI);
            throw new RuntimeException(e);
        }
    }

    public HttpResponse confirmPhoneClient(ConfirmPhoneRequest request) throws JsonProcessingException, UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(CONFIRM_PHONE_URI);
        httpPost.setHeader("Content-Type", "application/json");

        String requestJson = objectMapper.writeValueAsString(request);
        httpPost.setEntity(new StringEntity(requestJson));

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("Retrieve response from: {}", CONFIRM_PHONE_URI);
            logger.info("Status code: {}", statusCode);
            if (statusCode == 200) {
                return response;
            } else {
                throw new AuthException("Invalid login or password");
            }

        } catch (IOException e) {
            logger.error("Error occurred with send POST to: {}", CONFIRM_PHONE_URI);
            throw new RuntimeException(e);
        }
    }
}
