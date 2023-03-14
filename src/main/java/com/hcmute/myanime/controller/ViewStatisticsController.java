package com.hcmute.myanime.controller;

import com.hcmute.myanime.dto.ViewStatisticsInMonthDTO;
import com.hcmute.myanime.service.EpisodeService;
import com.hcmute.myanime.service.ViewStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("statistics")
public class ViewStatisticsController {

    @Autowired
    private ViewStatisticService viewStatisticService;

    @Autowired
    private EpisodeService episodeService;

    @GetMapping("/top-ep-most-view-in-week")
    public ResponseEntity<?> getEpisodeMostViewInWeek()
    {
        return ResponseEntity.ok(episodeService.getTopEpisodeMostView(7, 5));
    }
    @GetMapping("/top-ep-most-view-in-month")
    public ResponseEntity<?> getEpisodeMostViewInMonth()
    {
        return ResponseEntity.ok(episodeService.getTopEpisodeMostView(30, 5));
    }

    @GetMapping("/top-movie-series-most-view-in-number-of-day")
    public ResponseEntity<?> getMovieSeriesMostViewInNumberOfDay(@RequestParam(defaultValue = "1") int numberOfDay,
                                                                 @RequestParam(defaultValue = "5") int size)
    {
        return ResponseEntity.ok(episodeService.getTopSeriesMostView(numberOfDay, size));
    }
    @GetMapping("/count-in-year")
    public ResponseEntity<?> countViewStatisticInYear(@RequestParam Integer year)
    {
        List<ViewStatisticsInMonthDTO> viewStatisticsInMonthDTOList = new ArrayList<>();
        if(year == null) {          //Get default value year is current year if parameter year is null
            year = Year.now().getValue();
        }
        for(int month = 1; month <= 12; month++) { //count view in month 1 -> 12
            Long totalViewInMonth = viewStatisticService.countViewStatisticsByYearAndMonth(year, month);
            viewStatisticsInMonthDTOList.add(new ViewStatisticsInMonthDTO(month, totalViewInMonth));
        }
        return ResponseEntity.ok(viewStatisticsInMonthDTOList);
    }
}
