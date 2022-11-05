package com.hcmute.myanime.dto;

public class SubscriptionPackageDTO {
    private int id;
    private String name;
    private int day;
    private double price;
    private String description;
    private boolean enable;

    public SubscriptionPackageDTO() {
    }

    public SubscriptionPackageDTO(int id, String name, int day, double price, String description, boolean enable) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.price = price;
        this.description = description;
        this.enable = enable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
