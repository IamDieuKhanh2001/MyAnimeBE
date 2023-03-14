package com.hcmute.myanime.dto;

public class ViewStatisticsInMonthDTO {
    private int month;
    private Long totalView;

    public ViewStatisticsInMonthDTO(int month, Long totalView) {
        this.month = month;
        this.totalView = totalView;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Long getTotalView() {
        return totalView;
    }

    public void setTotalView(Long totalView) {
        this.totalView = totalView;
    }
}
