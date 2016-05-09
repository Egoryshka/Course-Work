package com.romanovich.user.repository;

import com.romanovich.user.model.Movie;
import com.romanovich.user.model.Rating;
import com.romanovich.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Alex on 17.02.2016.
 */
public interface RatingRepository extends JpaRepository<Rating,Long>{
    Rating findByUserAndMovie(User user, Movie movie);
}
