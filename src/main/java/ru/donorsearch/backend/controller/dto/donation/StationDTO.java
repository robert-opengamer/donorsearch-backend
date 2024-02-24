package ru.donorsearch.backend.controller.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class StationDTO {
    private int id;
    @JsonProperty("city_id")
    private int cityId;
    private CityDTO city;
    @JsonProperty("has_blood_group")
    private boolean hasBloodGroup;
    private String bloodStatus;
    private String title;
    private String address;
    private String site;
    private String phones;
    private String email;
    private String worktime;
    @JsonProperty("without_registration")
    private boolean withoutRegistration;
    @JsonProperty("with_typing")
    private boolean withTyping;
    @JsonProperty("for_moscow")
    private boolean forMoscow;
    private boolean closed;
    private int priority;

}
