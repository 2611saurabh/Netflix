package com.netflix.contentservice.controller;


import com.netflix.contentservice.dto.MoviesRequest;
import com.netflix.contentservice.dto.MoviesResponse;
import com.netflix.contentservice.model.Genre;
import com.netflix.contentservice.service.ContentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@Slf4j
@RequiredArgsConstructor
public class ContentController {


    private final ContentService contentService;


    //Add new movie to Catalog

    public ResponseEntity<MoviesResponse> addMovie(@Valid @RequestBody MoviesRequest moviesRequest){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contentService.addMovie(moviesRequest));
    }

    //Get All movie
    @GetMapping
    public ResponseEntity<List<MoviesResponse>> getAllMovies(){

        return ResponseEntity.ok(contentService.getAllMovies());

    }

    //Get movie by genre
    @PutMapping("/genre/{genre}")
    public ResponseEntity<List<MoviesResponse>> getMovieByGenre(
            @PathVariable Genre genre
    )
    {
        return ResponseEntity.ok(contentService.getMoviesByGenre(genre));
    }

    //Get movie by genre
    @GetMapping("/{movieId}")
    public ResponseEntity<MoviesResponse> getMovieById(
            @PathVariable String movieId
    )
    {
        return ResponseEntity.ok(contentService.getMoviesById(movieId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<MoviesResponse>> searchMovies(@RequestParam String title){

        return ResponseEntity.ok(contentService.searchMovie(title));
    }
}
