package com.hcmute.myanime.dto;

import java.sql.Timestamp;

public class MovieSeriesDTO {
    private int id;
    private Timestamp createAt;
    private Timestamp dateAired;
    private String description;
    private String image;
    private String name;
    private int totalEpisode;
    private int movieId;

    public MovieSeriesDTO(int id, Timestamp createAt, Timestamp dateAired, String description, String image, String name, int totalEpisode, int movieId) {
        this.id = id;
        this.createAt = createAt;
        this.dateAired = dateAired;
        this.description = description;
        this.image = image;
        this.name = name;
        this.totalEpisode = totalEpisode;
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getDateAired() {
        return dateAired;
    }

    public void setDateAired(Timestamp dateAired) {
        this.dateAired = dateAired;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalEpisode() {
        return totalEpisode;
    }

    public void setTotalEpisode(int totalEpisode) {
        this.totalEpisode = totalEpisode;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
