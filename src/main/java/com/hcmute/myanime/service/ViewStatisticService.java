package com.hcmute.myanime.service;

import com.hcmute.myanime.repository.ViewStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewStatisticService {
    @Autowired
    private ViewStatisticsRepository viewStatisticsRepository;

    public Long countViewStatisticsByYearAndMonth(int yearCreateAt, int montCreateAt) {
        return viewStatisticsRepository.countByYearCreateAtAndMonthCreateAt_FunctionSQL(yearCreateAt, montCreateAt);
    }

    public Long countViewStatisticsByYear(int yearCreateAt) {
        return viewStatisticsRepository.countByYearCreateAt_FunctionSQL(yearCreateAt);
    }
}
