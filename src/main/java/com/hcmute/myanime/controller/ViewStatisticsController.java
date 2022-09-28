package com.hcmute.myanime.controller;

import com.hcmute.myanime.dto.ResponseDTO;
import com.hcmute.myanime.service.EpisodeService;
import com.hcmute.myanime.service.ViewStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("statistics")
public class ViewStatisticsController {

    @Autowired
    private ViewStatisticService viewStatisticService;

    @Autowired
    private EpisodeService episodeService;

    @GetMapping("/top-most-view-in-week")
    public ResponseEntity<?> getEpisodeMostViewInWeek()
    {
        return ResponseEntity.ok(episodeService.getTopMostView(7));
    }

    @GetMapping("/top-most-view-in-month")
    public ResponseEntity<?> getEpisodeMostViewInMonth()
    {
        return ResponseEntity.ok(episodeService.getTopMostView(30));
    }
}
