package com.romanovich.user.repository;

import com.romanovich.user.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Егор on 09.05.2016.
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
