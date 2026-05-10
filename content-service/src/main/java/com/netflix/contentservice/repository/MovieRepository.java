package com.netflix.contentservice.repository;

import com.netflix.contentservice.model.Genre;
import com.netflix.contentservice.model.Movie;
import org.aspectj.weaver.ast.Literal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, String> {

    List<Movie> findByGenre(Genre genre);
}
