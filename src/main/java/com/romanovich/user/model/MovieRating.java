package com.romanovich.user.model;

/**
 * Created by Егор on 10.05.2016.
 */
public class MovieRating {

    private Long movieId;
    private boolean positive;

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
