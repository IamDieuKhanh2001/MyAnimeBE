package com.hcmute.myanime.dto;

import java.sql.Timestamp;

public class EpisodeDTO {
    private int id;
    private Timestamp createAt;
    private String resource;
    private String title;

    public EpisodeDTO(int id, Timestamp createAt, String resource, String title) {
        this.id = id;
        this.createAt = createAt;
        this.resource = resource;
        this.title = title;
    }

    public EpisodeDTO() {
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
