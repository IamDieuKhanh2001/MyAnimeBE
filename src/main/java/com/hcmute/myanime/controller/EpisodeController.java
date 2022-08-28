package com.hcmute.myanime.controller;

import com.cloudinary.api.exceptions.BadRequest;
import com.hcmute.myanime.dto.CategoryDTO;
import com.hcmute.myanime.dto.EpisodeDTO;
import com.hcmute.myanime.dto.ResponseDTO;
import com.hcmute.myanime.exception.BadRequestException;
import com.hcmute.myanime.model.CategoryEntity;
import com.hcmute.myanime.model.EpisodeEntity;
import com.hcmute.myanime.model.MovieSeriesEntity;
import com.hcmute.myanime.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            EpisodeDTO episodeDTO = new EpisodeDTO(
                    episode.getId(),
                    episode.getCreateAt(),
                    episode.getResource(),
                    episode.getTitle());
            episodeDTOList.add(episodeDTO);
        });
        return ResponseEntity.ok(episodeDTOList);
    }

    @PostMapping("/admin/episode/series/{seriesId}")
    public ResponseEntity<?> createEpisodeOfSeries(
            @RequestBody EpisodeDTO episodeDTO,
            @PathVariable int seriesId
    )
    {
        if(episodeService.save(episodeDTO, seriesId))
        {
            return ResponseEntity.ok(
                    new ResponseDTO(
                            HttpStatus.OK, "add episode success")
            );
        }
        else
        {
            throw new BadRequestException("add episode fail");
        }
    }

    @PutMapping("/admin/episode/{episodeId}")
    public ResponseEntity<?> updateEpisodeOfSeries(
            @RequestBody EpisodeDTO episodeDTO,
            @PathVariable int episodeId
    ) {
        if(episodeService.updateByEpisodeId(episodeId, episodeDTO)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, "Update episode success")
            );
        } else {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.BAD_REQUEST, "Update episode fail")
            );
        }
    }
}
