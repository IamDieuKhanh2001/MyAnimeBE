package com.hcmute.myanime.dto;

import java.sql.Timestamp;

public class SeriesDetailDTO {
    private int id;
    private String movieTitle;
    private String description;
    private String studioName;
    private String image;
    private Timestamp dateAired;
    private Timestamp movieCreateAt;
    private Long views;
    private Long commentTotal;
    private Timestamp seriesCreateAt;
    private String seriesName;
    private int totalEpisode;
    private int movieId;

    public SeriesDetailDTO(int id, String movieTitle, String description, String studioName, String image, Timestamp dateAired, Timestamp movieCreateAt, Long views, Long commentTotal, Timestamp seriesCreateAt, String seriesName, int totalEpisode, int movieId) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.description = description;
        this.studioName = studioName;
        this.image = image;
        this.dateAired = dateAired;
        this.movieCreateAt = movieCreateAt;
        this.views = views;
        this.commentTotal = commentTotal;
        this.seriesCreateAt = seriesCreateAt;
        this.seriesName = seriesName;
        this.totalEpisode = totalEpisode;
        this.movieId = movieId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getDateAired() {
        return dateAired;
    }

    public void setDateAired(Timestamp dateAired) {
        this.dateAired = dateAired;
    }

    public Timestamp getMovieCreateAt() {
        return movieCreateAt;
    }

    public void setMovieCreateAt(Timestamp movieCreateAt) {
        this.movieCreateAt = movieCreateAt;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getCommentTotal() {
        return commentTotal;
    }

    public void setCommentTotal(Long commentTotal) {
        this.commentTotal = commentTotal;
    }

    public Timestamp getSeriesCreateAt() {
        return seriesCreateAt;
    }

    public void setSeriesCreateAt(Timestamp seriesCreateAt) {
        this.seriesCreateAt = seriesCreateAt;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
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
