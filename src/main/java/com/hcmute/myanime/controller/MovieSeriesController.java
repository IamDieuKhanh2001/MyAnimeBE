package com.hcmute.myanime.controller;

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
import java.util.Map;

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

    @GetMapping("/movie-and-series/count")
    public ResponseEntity<?> countSeries(@RequestParam Map<String, String> requestParams)
    {
        String keywordSearch = requestParams.get("keyword");
        return ResponseEntity.ok(new TotalSeriesDTO(movieSeriesService.countSeries(keywordSearch)));
    }

    //Movie va series co phan trang
    @GetMapping("/movie-and-series")
    public ResponseEntity<?> movieAndSeriesFindAll(@RequestParam Map<String, String> requestParams)
    {

        String page = requestParams.get("page");
        String limit = requestParams.get("limit");
        String keywordSearch = requestParams.get("keyword");
        List<MovieSeriesEntity> movieSeriesEntityList = movieSeriesService.getByPageAndLimit(page, limit, keywordSearch);
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
                            movieSeriesEntity.getEpisodesById().size(),
                            movieSeriesEntity.getTotalEpisode(),
                            movieSeriesEntity.getMovieByMovieId().getId(),
                            movieSeriesEntity.getMovieByMovieId().getCategoryEntityCollection().stream().toList()
                    )
            );
        });
        return ResponseEntity.ok(seriesDetailDTOList);
    }

    //Movie va series lay id
    @GetMapping("/movie-and-series/{seriesId}")
    public ResponseEntity<?> movieAndSeriesBySeriesId(@PathVariable int seriesId)
    {
        MovieSeriesEntity movieSeriesEntity = movieSeriesService.findById(seriesId);
        SeriesDetailDTO seriesDetailDTO = new SeriesDetailDTO(
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
                movieSeriesEntity.getEpisodesById().size(),
                movieSeriesEntity.getTotalEpisode(),
                movieSeriesEntity.getMovieByMovieId().getId(),
                movieSeriesEntity.getMovieByMovieId().getCategoryEntityCollection().stream().toList()
        );
        return ResponseEntity.ok(seriesDetailDTO);
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
            throw new BadRequestException("create new movie series fail");
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
            throw new BadRequestException("Update series fail");
        }
    }

    @DeleteMapping("/admin/movie-series/{seriesID}")
    public ResponseEntity<?> deleteSeriesById(@PathVariable int seriesID) {
        if(movieSeriesService.deleteById(seriesID)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, "Delete series success")
            );
        } else {
            throw new BadRequestException("Delete series fail");
        }
    }
}
