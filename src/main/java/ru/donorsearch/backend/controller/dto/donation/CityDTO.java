package ru.donorsearch.backend.controller.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
public class CityDTO {
    private int id;
    private String title;
    private String slug;
    @JsonProperty("region_id")
    private int regionId;
    private RegionDTO region;
    private CountryDTO country;

    public CityDTO(int id, String title, String slug, int regionId, RegionDTO region, CountryDTO country) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.regionId = regionId;
        this.region = region;
        this.country = country;
    }

    public CityDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }
}
