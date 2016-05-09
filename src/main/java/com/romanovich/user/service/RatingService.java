package com.romanovich.user.service;

import com.romanovich.user.model.Movie;
import com.romanovich.user.model.Rating;
import com.romanovich.user.model.User;

/**
 * Created by Alex on 17.02.2016.
 */
public interface RatingService {
    Integer getScore(Movie movie);
    void saveOrDeleteRating(Rating rating, Movie movie, User user);
    Rating findByUserAndMovie(User user, Movie movie);
}
