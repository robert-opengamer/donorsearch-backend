package ru.donorsearch.backend.controller.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "blood_station_id",
        "city_id",
        "object_id",
        "blood_class",
        "plan_date",
        "payment_type",
        "status",
        "is_out",
        "donation",
        "station"

})
public class DonationPlanDTO {
    @JsonProperty("blood_station_id")
    private int bloodStationId;

    @JsonProperty("city_id")
    private int cityId;

    @JsonProperty("object_id")
    private int objectId;

    @JsonProperty("blood_class")
    private String bloodClass;

    @JsonProperty("plan_date")
    private String planDate;

    @JsonProperty("payment_type")
    private String paymentType;

    private String status;

    @JsonProperty("is_out")
    private boolean isOut;

    private StationDTO station;

    public DonationPlanDTO(int bloodStationId, int cityId, int objectId,
                               String bloodClass, String planDate, String paymentType,
                               String status, boolean isOut, StationDTO station) {
        this.bloodStationId = bloodStationId;
        this.cityId = cityId;
        this.objectId = objectId;
        this.bloodClass = bloodClass;
        this.planDate = planDate;
        this.paymentType = paymentType;
        this.status = status;
        this.isOut = isOut;
        this.station = station;
    }

    public DonationPlanDTO() {
    }

    public StationDTO getStation() {
        return station;
    }

    public void setStation(StationDTO station) {
        this.station = station;
    }

    public int getBloodStationId() {
        return bloodStationId;
    }

    public void setBloodStationId(int bloodStationId) {
        this.bloodStationId = bloodStationId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getBloodClass() {
        return bloodClass;
    }

    public void setBloodClass(String bloodClass) {
        this.bloodClass = bloodClass;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

}
