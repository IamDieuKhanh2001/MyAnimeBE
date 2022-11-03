package com.hcmute.myanime.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

public class EpisodeDTO {
    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createAt;
    private String resourceCD;
    private String resourceDO;
    private String title;

    public EpisodeDTO(int id, Timestamp createAt, String resourceCD, String resourceDO, String title) {
        this.id = id;
        this.createAt = createAt;
        this.resourceCD = resourceCD;
        this.resourceDO = resourceDO;
        this.title = title;
    }

    public EpisodeDTO(int id, Timestamp createAt, String resourceCD, String title) {
        this.id = id;
        this.createAt = createAt;
        this.resourceCD = resourceCD;
        this.title = title;
    }

    public String getResourceCD() {
        return resourceCD;
    }

    public void setResourceCD(String resourceCD) {
        this.resourceCD = resourceCD;
    }

    public EpisodeDTO() {
    }

    public String getResourceDO() {
        return resourceDO;
    }

    public void setResourceDO(String resourceDO) {
        this.resourceDO = resourceDO;
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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
