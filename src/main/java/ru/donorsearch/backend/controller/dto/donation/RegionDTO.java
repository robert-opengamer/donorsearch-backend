package ru.donorsearch.backend.controller.dto.donation;

public class RegionDTO {
    private int id;
    private String title;

    public RegionDTO(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public RegionDTO() {
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
}