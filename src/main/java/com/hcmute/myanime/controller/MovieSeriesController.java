package com.hcmute.myanime.controller;

import com.hcmute.myanime.dto.CategoryDTO;
import com.hcmute.myanime.dto.MovieSeriesDTO;
import com.hcmute.myanime.dto.ResponseDTO;
import com.hcmute.myanime.model.MovieSeriesEntity;
import com.hcmute.myanime.service.MovieSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class MovieSeriesController {
    @Autowired
    private MovieSeriesService movieSeriesService;

    @GetMapping("/movie-series")
    public ResponseEntity<?> findAll()
    {
        return ResponseEntity.ok( movieSeriesService.findAll());
    }

    @PostMapping("/movie-series")
    public ResponseEntity<?> storage(@RequestBody MovieSeriesDTO movieSeriesDTO)
    {
        if(movieSeriesService.save(movieSeriesDTO))
        {
            return ResponseEntity.ok(
                    new ResponseDTO(
                            HttpStatus.OK,
                            "Create new movie series success"
                    )
            );
        }
        else
        {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.NO_CONTENT, "create fail")
            );
        }
    }

    @PutMapping("/movie-series/{seriesID}")
    public ResponseEntity<?> updateSeriesById(@RequestBody MovieSeriesDTO movieSeriesDTO,
                                                @PathVariable int seriesID) {

        if(movieSeriesService.updateById(seriesID, movieSeriesDTO)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, "Update series success")
            );
        } else {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.BAD_REQUEST, "Update fail")
            );
        }
    }

    @DeleteMapping("/movie-series/{seriesID}")
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
