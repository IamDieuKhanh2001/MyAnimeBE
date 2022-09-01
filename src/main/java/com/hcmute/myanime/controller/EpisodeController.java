package com.hcmute.myanime.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmute.myanime.dto.EpisodeDTO;
import com.hcmute.myanime.dto.ResponseDTO;
import com.hcmute.myanime.exception.BadRequestException;
import com.hcmute.myanime.model.EpisodeEntity;
import com.hcmute.myanime.service.CloudinaryService;
import com.hcmute.myanime.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EpisodeController {

    @Autowired
    private EpisodeService episodeService;

    @GetMapping(value = "/episode/series/{seriesId}")
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

    @GetMapping(value = "/episode/{episodeId}")
    public ResponseEntity<?> getEpisodeById(@PathVariable int episodeId){
        EpisodeEntity episodeEntity = episodeService.findById(episodeId);
        EpisodeDTO episodeDTO = new EpisodeDTO(
                episodeEntity.getId(),
                episodeEntity.getCreateAt(),
                episodeEntity.getResource(),
                episodeEntity.getTitle());
        return ResponseEntity.ok(episodeDTO);
    }

    @PostMapping("/admin/episode/series/{seriesId}")
    public ResponseEntity<?> createEpisodeOfSeries(
            @RequestParam String model,
            @RequestParam(value = "sourceFile", required = false) MultipartFile sourceFile,
            @PathVariable int seriesId
    ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        EpisodeDTO episodeDTO = mapper.readValue(model, EpisodeDTO.class);
        if(episodeService.save(episodeDTO, sourceFile, seriesId))
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
            @RequestParam String model,
            @RequestParam(value = "sourceFile", required = false) MultipartFile sourceFile,
            @PathVariable int episodeId
    ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        EpisodeDTO episodeDTO = mapper.readValue(model, EpisodeDTO.class);

        if(episodeService.updateByEpisodeId(episodeId, episodeDTO, sourceFile)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, "Update episode success")
            );
        } else {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.BAD_REQUEST, "Update episode fail")
            );
        }
    }

    @Autowired
    private CloudinaryService cloudinaryService;

    @DeleteMapping("/admin/episode/{episodeId}")
    public ResponseEntity<?> deleteEpisodeById(@PathVariable int episodeId) {
        if(episodeService.deleteById(episodeId)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, "Delete episode success")
            );
        } else {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.BAD_REQUEST, "Detele episode Fail")
            );
        }
    }
}