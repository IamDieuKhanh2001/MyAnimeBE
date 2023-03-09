package com.hcmute.myanime.dto;

public class StatisticsSubscriptionPackageDTO {
    private int statisticsTopUpTotal;

    public StatisticsSubscriptionPackageDTO(int statisticsTopUpTotal) {
        this.statisticsTopUpTotal = statisticsTopUpTotal;
    }

    public int getStatisticsTopUpTotal() {
        return statisticsTopUpTotal;
    }

    public void setStatisticsTopUpTotal(int statisticsTopUpTotal) {
        this.statisticsTopUpTotal = statisticsTopUpTotal;
    }
}
