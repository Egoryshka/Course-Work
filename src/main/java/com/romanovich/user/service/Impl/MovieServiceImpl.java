package com.romanovich.user.service.Impl;

import com.romanovich.user.model.Movie;
import com.romanovich.user.repository.MovieRepository;
import com.romanovich.user.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Created by Егор on 09.05.2016.
 */

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getPopularTopTen() {
        List<Movie> movies = movieRepository.findAll();
        Collections.sort(movies);
        try {
            return movies.subList(0, 10);
        } catch (IndexOutOfBoundsException e) {
            return movies;
        }
    }

    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie findOne(Long id) {
        return movieRepository.findOne(id);
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public void delete(Movie movie) {
        movieRepository.delete(movie);
    }


}
