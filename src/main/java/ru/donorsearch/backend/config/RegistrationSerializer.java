package ru.donorsearch.backend.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import ru.donorsearch.backend.controller.dto.RegistrationRequest;

public class RegistrationSerializer extends JsonSerializer<RegistrationRequest> {
    @Override
    public void serialize(RegistrationRequest registrationRequest, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String login = registrationRequest.getLogin();
        jsonGenerator.writeStartObject();
        if (isEmail(login)) {
            jsonGenerator.writeStringField("email", login);
        } else if (isPhoneNumber(login)) {
            jsonGenerator.writeStringField("phone", login);
        } else {
            throw new IllegalArgumentException("Неверный формат данных для login");
        }
        jsonGenerator.writeStringField("first_name", registrationRequest.getFirstName());
        jsonGenerator.writeStringField("password", registrationRequest.getPassword());
        jsonGenerator.writeStringField("tag", registrationRequest.getTag());
        jsonGenerator.writeEndObject();
    }

    private boolean isEmail(String login) {
        return login.contains("@");
    }

    private boolean isPhoneNumber(String login) {
        return login.matches("\\d{11}");
    }
}
