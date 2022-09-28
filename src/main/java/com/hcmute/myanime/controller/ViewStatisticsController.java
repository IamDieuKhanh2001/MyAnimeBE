package com.hcmute.myanime.controller;

import com.hcmute.myanime.dto.ResponseDTO;
import com.hcmute.myanime.service.EpisodeService;
import com.hcmute.myanime.service.ViewStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("statistics")
public class ViewStatisticsController {

    @Autowired
    private ViewStatisticService viewStatisticService;

    @Autowired
    private EpisodeService episodeService;

    @GetMapping("/top-view-one-week")
    public ResponseEntity<?> getTop5EpisodeViewOnWeek()
    {
        episodeService.getTop5EpisodeViewOnWeek();
        return ResponseEntity.ok(
                new ResponseDTO(
                        HttpStatus.OK,
                        "Create new category success"
                )
        );
    }
}
