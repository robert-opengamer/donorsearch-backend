package ru.donorsearch.backend.controller.dto.donation;

public class CityDTO {
    private int id;
    private String title;
    private String slug;

    public CityDTO(int id, String title, String slug) {
        this.id = id;
        this.title = title;
        this.slug = slug;
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

}
