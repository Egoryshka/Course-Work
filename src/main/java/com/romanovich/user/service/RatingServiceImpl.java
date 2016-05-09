package com.romanovich.user.service;

import com.romanovich.user.model.Movie;
import com.romanovich.user.model.Rating;
import com.romanovich.user.model.User;
import com.romanovich.user.repository.RatingRepository;
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


    public Integer getScore(Movie movie) {
        Integer result = 0;
        for (Rating rait : movie.getRatings()) {
            if (rait.getPositive()) {
                result++;
            } else result--;
        }
        return result;
    }

    @Override
    public void saveOrDeleteRating(Rating rating, Movie movie, User user) {
        for (Rating rait : movie.getRatings()) {
            if (rating.getUser().getId().equals(rait.getUser().getId())) {
                if (rating.getPositive() == rait.getPositive())
                    return;
                else {
                    movie.removeRating(rait);
                    return;
                }
            }
        }
        ratingRepository.save(rating);
        post.addRating(rating);
    }
}
