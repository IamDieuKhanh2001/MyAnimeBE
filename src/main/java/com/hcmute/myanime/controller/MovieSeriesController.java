package com.hcmute.myanime.controller;

import com.cloudinary.api.exceptions.BadRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmute.myanime.dto.*;
import com.hcmute.myanime.exception.BadRequestException;
import com.hcmute.myanime.model.MovieSeriesEntity;
import com.hcmute.myanime.service.CommentService;
import com.hcmute.myanime.service.MovieSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping()
public class MovieSeriesController {
    @Autowired
    private MovieSeriesService movieSeriesService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/admin/movie-series")
    public ResponseEntity<?> findAll()
    {
        return ResponseEntity.ok( movieSeriesService.findAll());
    }

    @GetMapping("/movie-and-series")
    public ResponseEntity<?> movieAndSeriesFindAll()
    {
        List<MovieSeriesEntity> movieSeriesEntityList = movieSeriesService.findAll();
        List<SeriesDetailDTO> seriesDetailDTOList = new ArrayList<>();
        movieSeriesEntityList.forEach(movieSeriesEntity -> {
            seriesDetailDTOList.add(
                    new SeriesDetailDTO(
                            movieSeriesEntity.getId(),
                            movieSeriesEntity.getMovieByMovieId().getTitle(),
                            movieSeriesEntity.getDescription(),
                            movieSeriesEntity.getMovieByMovieId().getStudioName(),
                            movieSeriesEntity.getImage(),
                            movieSeriesEntity.getDateAired(),
                            movieSeriesEntity.getMovieByMovieId().getCreateAt(),
                            movieSeriesService.totalViewByMovieSeriesEntity(movieSeriesEntity),
                            commentService.totalCommentByMovieSeriesEntity(movieSeriesEntity),
                            movieSeriesEntity.getCreateAt(),
                            movieSeriesEntity.getName(),
                            movieSeriesEntity.getTotalEpisode(),
                            movieSeriesEntity.getMovieByMovieId().getId()
                    )
            );
        });
        return ResponseEntity.ok(seriesDetailDTOList);
    }

    @PostMapping("/admin/movie-series")
    public ResponseEntity<?> storage(
            @RequestParam String model,
            @RequestParam(value = "sourceFile", required = false) MultipartFile sourceFile
    ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        MovieSeriesDTO movieSeriesDTO = mapper.readValue(model, MovieSeriesDTO.class);

        if(movieSeriesService.save(movieSeriesDTO, sourceFile)) {
            return ResponseEntity.ok(
                    new ResponseDTO(
                            HttpStatus.OK,
                            "Create new movie series success"
                    )
            );
        } else {
            throw new BadRequestException("create fail");
        }
    }


    @PutMapping("/admin/movie-series/{seriesID}")
    public ResponseEntity<?> updateSeriesById(
            @RequestParam String model,
            @RequestParam(value = "sourceFile", required = false) MultipartFile sourceFile,
            @PathVariable int seriesID) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        MovieSeriesDTO movieSeriesDTO = mapper.readValue(model, MovieSeriesDTO.class);

        if(movieSeriesService.updateById(seriesID, movieSeriesDTO, sourceFile)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, "Update series success")
            );
        } else {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.BAD_REQUEST, "Update fail")
            );
        }
    }

    @DeleteMapping("/admin/movie-series/{seriesID}")
    public ResponseEntity<?> deleteSeriesById(@PathVariable int seriesID) {
        if(movieSeriesService.deleteById(seriesID)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, "Delete series success")
            );
        } else {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.BAD_REQUEST, "Detele Fail")
            );
        }
    }
}
