package com.hcmute.myanime.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hcmute.myanime.common.Common;
import com.hcmute.myanime.dto.*;
import com.hcmute.myanime.exception.BadRequestException;
import com.hcmute.myanime.mapper.MovieSeriesMapper;
import com.hcmute.myanime.mapper.SeriesDetailMapper;
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
        List<MovieSeriesEntity> movieSeriesEntityList = movieSeriesService.findAll();
        List<MovieSeriesDTO> movieSeriesDTOList = new ArrayList<>();
        movieSeriesEntityList.forEach(movieSeriesEntity -> {
            movieSeriesDTOList.add(MovieSeriesMapper.toDTO(movieSeriesEntity));
        });
        return ResponseEntity.ok(movieSeriesDTOList);
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
            Long seriesTotalView = movieSeriesService.totalViewByMovieSeriesEntity(movieSeriesEntity);
            Long seriesTotalComment = commentService.totalCommentByMovieSeriesEntity(movieSeriesEntity);
            seriesDetailDTOList.add(SeriesDetailMapper.toDTO(movieSeriesEntity, seriesTotalView,seriesTotalComment));
        });
        return ResponseEntity.ok(seriesDetailDTOList);
    }

    //Movie va series lay id
    @GetMapping("/movie-and-series/{seriesId}")
    public ResponseEntity<?> movieAndSeriesBySeriesId(@PathVariable int seriesId)
    {
        MovieSeriesEntity movieSeriesEntity = movieSeriesService.findById(seriesId);
        Long seriesTotalView = movieSeriesService.totalViewByMovieSeriesEntity(movieSeriesEntity);
        Long seriesTotalComment = commentService.totalCommentByMovieSeriesEntity(movieSeriesEntity);
        SeriesDetailDTO seriesDetailDTO = SeriesDetailMapper.toDTO(movieSeriesEntity, seriesTotalView,seriesTotalComment);
        return ResponseEntity.ok(seriesDetailDTO);
    }

    //Movie va series lay cac series cùng 1 movie id
    @GetMapping("/movie-and-series/get-all-series/{seriesId}")
    public ResponseEntity<?> getAllMovieAndSeriesBySeriesId(@PathVariable int seriesId)
    {
        List<MovieSeriesEntity> movieSeriesEntityList = movieSeriesService.findAllMovieAndSeriesById(seriesId);
        List<SeriesDetailDTO> seriesDetailDTOList = new ArrayList<>();
        movieSeriesEntityList.forEach(movieSeriesEntity -> {
            Long seriesTotalView = movieSeriesService.totalViewByMovieSeriesEntity(movieSeriesEntity);
            Long seriesTotalComment = commentService.totalCommentByMovieSeriesEntity(movieSeriesEntity);
            seriesDetailDTOList.add(SeriesDetailMapper.toDTO(movieSeriesEntity, seriesTotalView,seriesTotalComment));
        });
        return ResponseEntity.ok(seriesDetailDTOList);
    }

    @PostMapping("/admin/movie-series")
    public ResponseEntity<?> storage(
            @RequestParam String model,
            @RequestParam(value = "sourceFile", required = false) MultipartFile sourceFile
    ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MovieSeriesDTO movieSeriesDTO = mapper.readValue(model, MovieSeriesDTO.class);

        MovieSeriesEntity movieSeriesEntity = movieSeriesService.save(movieSeriesDTO, sourceFile);
        MovieSeriesDTO movieSeriesResponseDTO = MovieSeriesMapper.toDTO(movieSeriesEntity);
        return ResponseEntity.ok(
                new ResponseDTO(
                        HttpStatus.OK,
                        "Create series success",
                        movieSeriesResponseDTO
                )
        );
    }


    @PutMapping("/admin/movie-series/{seriesID}")
    public ResponseEntity<?> updateSeriesById(
            @RequestParam String model,
            @RequestParam(value = "sourceFile", required = false) MultipartFile sourceFile,
            @PathVariable int seriesID) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        MovieSeriesDTO movieSeriesDTO = mapper.readValue(model, MovieSeriesDTO.class);

        MovieSeriesEntity movieSeriesEntity = movieSeriesService.updateById(seriesID, movieSeriesDTO, sourceFile);
        MovieSeriesDTO movieSeriesResponseDTO = MovieSeriesMapper.toDTO(movieSeriesEntity);
        return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK,
                            "Update series success",
                            movieSeriesResponseDTO)
        );
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
