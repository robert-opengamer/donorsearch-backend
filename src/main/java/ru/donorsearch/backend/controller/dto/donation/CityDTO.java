package ru.donorsearch.backend.controller.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class CityDTO {
    private int id;
    private String title;
    private String slug;
    @JsonProperty("region_id")
    private int regionId;
    private RegionDTO region;
    private CountryDTO country;
}
