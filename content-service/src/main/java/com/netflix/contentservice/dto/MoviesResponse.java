package com.netflix.contentservice.dto;

import com.netflix.contentservice.model.Genre;
import com.netflix.contentservice.model.VideoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoviesResponse {

    private String id;

    private String title;

    private String description;

    private Genre genre;

    private String director;

    private String cast;

    private int releaseYear;
    private double rating;

    private String thumbnailUrl;

    private int durationMinutes;

    //s3 key for video file
    private String videoKey;

    //hls master playlist URL for streaming
    private String hlsUrl;

    //status of video Processing
    private VideoStatus videoStatus;

    private LocalDateTime  createdAt;
    private LocalDateTime updatedAt;
}
