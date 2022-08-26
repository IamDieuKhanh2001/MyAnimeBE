package com.hcmute.myanime.controller;

import com.hcmute.myanime.common.Common;
import com.hcmute.myanime.dto.MovieDTO;
import com.hcmute.myanime.dto.ResponseDTO;
import com.hcmute.myanime.exception.BadRequestException;
import com.hcmute.myanime.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @GetMapping("/movie")
    public ResponseEntity<?> getAllMovie()
    {
        return ResponseEntity.ok(movieService.findAll());
    }
    @PostMapping("/movie")
    public ResponseEntity<?> createNewMovie(@RequestBody MovieDTO movieDTO)
    {
        if(movieService.save(movieDTO))
        {
            return ResponseEntity.ok(
                    new ResponseDTO(
                            HttpStatus.OK, Common.MessageRespone.StorageMovieSuccess)
            );
        }
        else
        {
            throw new BadRequestException(Common.MessageRespone.StorageMovieFail);
        }
    }
    @PutMapping("/movie/{movieID}")
    public ResponseEntity<?> updateMovieByID(@RequestBody MovieDTO movieDTO, @PathVariable int movieID)
    {
        if(movieService.updateById(movieID, movieDTO)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, Common.MessageRespone.UpdateMovieSuccess)
            );
        } else {
            throw new BadRequestException("Update movie fail");
        }
    }

    @DeleteMapping("/movie/{movieID}")
    public ResponseEntity<?> deleteMovieByID(@PathVariable int movieID) {
        if(movieService.deleteById(movieID)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, "Delete movie success")
            );
        } else {
            return ResponseEntity.ok(
              new ResponseDTO(HttpStatus.BAD_REQUEST, "ID not found")
            );
        }
    }
}
