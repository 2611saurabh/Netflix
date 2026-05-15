package com.netflix.contentservice.service;


import com.netflix.contentservice.dto.MoviesRequest;
import com.netflix.contentservice.dto.MoviesResponse;
import com.netflix.contentservice.model.Genre;
import com.netflix.contentservice.model.Movie;
import com.netflix.contentservice.model.VideoStatus;
import com.netflix.contentservice.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContentService {

    private final MovieRepository movieRepository;


    /*
    * Add new movie to ctalog
    * Videos is not uploaded yet this stage
    */

    public MoviesResponse addMovie(MoviesRequest request){

        log.info("Adding new movie: {}", request.getTitle());

        Movie movie = new Movie();

        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setGenre(request.getGenre());
        movie.setDirector(request.getDirector());
        movie.setCast(request.getCast());
        movie.setReleaseYear(request.getReleaseYear());
        movie.setRating(request.getRating());
        movie.setThumbnailUrl(request.getThumbnailUrl());
        movie.setDurationMinute(request.getDurationInMinute());
        movie.setVideoStatus(VideoStatus.PENDING);

        Movie savedMovie = movieRepository.save(movie);
        log.info("movie added with id: {}", savedMovie.getId());

        return mapToResponse(savedMovie);

    }

    /*
    *Get all movies in catlog
    */
    public List<MoviesResponse> getAllMovies(){

        return movieRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /*
    * Get Movie by ID
    */

    public MoviesResponse getMoviesById(String movieId){
        log.info("Getting movie by id: {}", movieId);

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found: "+movieId));

        return mapToResponse(movie);
    }

    /*
    *Get movies by genre
     */

    public List<MoviesResponse> getMoviesByGenre(Genre genre){

        return movieRepository.findByGenre(genre)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }


    public List<MoviesResponse> searchMovie(String title){

        return movieRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::mapToResponse).collect(Collectors.toList());

    }

    public void updateVideoKey(String movieId, String videoKey){

        log.info("Updating videokey for movie {}", movieId);

        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found: {}"+movieId));
        movie.setVideoKey(videoKey);
        movie.setVideoStatus(VideoStatus.UPLOADED);
        movieRepository.save(movie);

    }

    public void updateHlsUrl(String movieId, String hlsUrl){
        log.info("Updating HLS url for movie {}", movieId);
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found: {}"+movieId));

        movie.setHlsUrl(hlsUrl);
        movie.setVideoStatus(VideoStatus.READY);
        movieRepository.save(movie);
        log.info("Movie {} is now ready for streaming", movieId);

    }


    private MoviesResponse mapToResponse(Movie movie){

        MoviesResponse response = new MoviesResponse();
        response.setId(movie.getId());
        response.setTitle(movie.getTitle());
        response.setDescription(movie.getDescription());
        response.setGenre(movie.getGenre());
        response.setDirector(movie.getDirector());
        response.setCast(movie.getCast());
        response.setReleaseYear(movie.getReleaseYear());
        response.setRating(movie.getRating());
        response.setThumbnailUrl(movie.getThumbnailUrl());
        response.setDurationMinutes(movie.getDurationMinute());
        response.setVideoStatus(movie.getVideoStatus());
        response.setVideoKey(movie.getVideoKey());
        response.setHlsUrl(movie.getHlsUrl());
        response.setCreatedAt(movie.getCreatedAt());

        return response;
    }


}
