package com.romanovich.user.service;

import com.romanovich.user.model.Movie;

import java.util.List;

/**
 * Created by Егор on 09.05.2016.
 */
public interface MovieService {
    List<Movie> getPopularTopTen();
    Movie save(Movie movie);
    Movie findOne(Long id);
    List<Movie> getAllMovies();
    void delete(Movie movie);
}
