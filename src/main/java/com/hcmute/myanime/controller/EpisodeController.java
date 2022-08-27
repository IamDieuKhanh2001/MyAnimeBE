package com.hcmute.myanime.controller;

import com.cloudinary.api.exceptions.BadRequest;
import com.hcmute.myanime.dto.EpisodeDTO;
import com.hcmute.myanime.model.CategoryEntity;
import com.hcmute.myanime.model.EpisodeEntity;
import com.hcmute.myanime.model.MovieSeriesEntity;
import com.hcmute.myanime.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EpisodeController {

    @Autowired
    private EpisodeService episodeService;

    @GetMapping("/user/episode/series/{seriesId}")
    public ResponseEntity<?> getEpisodeOfSeries(@PathVariable int seriesId){
        List<EpisodeEntity> listEpisodeBySeriesId = episodeService.findBySeriesId(seriesId);
        List<EpisodeDTO> episodeDTOList = new ArrayList<>();
        listEpisodeBySeriesId.forEach((episode) -> {
            System.out.println(episode.getCommentsById().size());
            EpisodeDTO episodeDTO = new EpisodeDTO(
                    episode.getId(),
                    episode.getCreateAt(),
                    episode.getResource(),
                    episode.getTitle());
            episodeDTOList.add(episodeDTO);
        });
        return ResponseEntity.ok(episodeDTOList);
    }
}
