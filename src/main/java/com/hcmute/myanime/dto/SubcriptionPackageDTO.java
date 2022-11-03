package com.hcmute.myanime.dto;

public class SubcriptionPackageDTO {
    private int id;
    private int day;
    private String description;
    private int enable;
    private String name;
    private double price;

    public SubcriptionPackageDTO() {
    }

    public SubcriptionPackageDTO(int id, int day, String description, int enable, String name, double price) {
        this.id = id;
        this.day = day;
        this.description = description;
        this.enable = enable;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
