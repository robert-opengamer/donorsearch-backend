package ru.donorsearch.backend.entity;

import jakarta.persistence.*;

@Entity
public class DonationPlan {

    @Id
    private long id;
    private int bloodStationId;
    private int cityId;
    private String bloodClass;
    private String planDate;
    private String paymentType;
    private boolean isOut;

    private String station;

    @ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public DonationPlan(long id, int bloodStationId, int cityId, String bloodClass, String planDate, String paymentType, boolean isOut, String station) {
        this.id = id;
        this.bloodStationId = bloodStationId;
        this.cityId = cityId;
        this.bloodClass = bloodClass;
        this.planDate = planDate;
        this.paymentType = paymentType;
        this.isOut = isOut;
        this.station = station;
    }

    public DonationPlan() {
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    @Override
    public String toString() {
        return "DonationPlan{" +
                "id=" + id +
                ", bloodStationId=" + bloodStationId +
                ", cityId=" + cityId +
                ", bloodClass='" + bloodClass + '\'' +
                ", planDate='" + planDate + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", isOut=" + isOut +
                ", user=" + user +
                '}';
    }
}
