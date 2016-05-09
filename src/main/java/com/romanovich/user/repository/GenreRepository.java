package com.romanovich.user.repository;

import com.romanovich.user.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Егор on 09.05.2016.
 */
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
