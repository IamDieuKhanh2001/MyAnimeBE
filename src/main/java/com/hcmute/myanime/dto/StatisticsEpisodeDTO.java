package com.hcmute.myanime.dto;

import java.sql.Timestamp;

public class StatisticsEpisodeDTO extends EpisodeDTO{

    private long totalView;

    public StatisticsEpisodeDTO(int id, Timestamp createAt, String resource, String title, long totalView) {
        super(id, createAt, resource, title);
        this.totalView = totalView;
    }

    public long getTotalView() {
        return totalView;
    }

    public void setTotalView(long totalView) {
        this.totalView = totalView;
    }

    public StatisticsEpisodeDTO() {
    }
}
