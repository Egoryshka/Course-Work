package com.romanovich.user.service.Impl;

import com.romanovich.user.model.Movie;
import com.romanovich.user.model.Rating;
import com.romanovich.user.model.User;
import com.romanovich.user.repository.RatingRepository;
import com.romanovich.user.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Alex on 17.02.2016.
 */

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Integer getScore(Movie movie) {
        Integer result = 0;
        for (Rating rating : movie.getRatings()) {
            if (rating.getPositive()) {
                result++;
            } else result--;
        }
        return result;
    }

    @Override
    public void saveOrDeleteRating(Rating rating, Movie movie, User user) {
        for (Rating ratingItem : movie.getRatings()) {
            if (rating.getUser().getId().equals(ratingItem.getUser().getId())) {
                if (rating.getPositive() == ratingItem.getPositive())
                    return;
                else {
                    movie.removeRating(ratingItem);
                    return;
                }
            }
        }
        ratingRepository.save(rating);
        movie.addRating(rating);
    }

    @Override
    public Rating findByUserAndMovie(User user, Movie movie) {
        return ratingRepository.findByUserAndMovie(user, movie);
    }
}
