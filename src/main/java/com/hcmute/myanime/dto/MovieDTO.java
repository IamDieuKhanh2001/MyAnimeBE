package com.hcmute.myanime.dto;

import java.sql.Timestamp;

public class MovieDTO {
    private int id;
    private String title;
    private String description;
    private String videoTrailer;
    private String studioName;
    private String image;
    private Timestamp dateAired;
    private Timestamp createAt;
    private int views;
    private int commentTotal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getVideoTrailer() {
        return videoTrailer;
    }

    public void setVideoTrailer(String videoTrailer) {
        this.videoTrailer = videoTrailer;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getCommentTotal() {
        return commentTotal;
    }

    public void setCommentTotal(int commentTotal) {
        this.commentTotal = commentTotal;
    }
}
