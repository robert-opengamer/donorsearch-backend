package ru.donorsearch.backend.controller.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public StationDTO(int id, int cityId, CityDTO city, boolean hasBloodGroup, String bloodStatus, String title, String address, String site, String phones, String email, String worktime) {
        this.id = id;
        this.cityId = cityId;
        this.city = city;
        this.hasBloodGroup = hasBloodGroup;
        this.bloodStatus = bloodStatus;
        this.title = title;
        this.address = address;
        this.site = site;
        this.phones = phones;
        this.email = email;
        this.worktime = worktime;
    }

    public StationDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public boolean isHasBloodGroup() {
        return hasBloodGroup;
    }

    public void setHasBloodGroup(boolean hasBloodGroup) {
        this.hasBloodGroup = hasBloodGroup;
    }

    public String getBloodStatus() {
        return bloodStatus;
    }

    public void setBloodStatus(String bloodStatus) {
        this.bloodStatus = bloodStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }
}
