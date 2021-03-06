package com.romanovich.user.service;

import com.romanovich.user.model.Genre;
import com.romanovich.user.model.Movie;

import java.util.List;

/**
 * Created by Егор on 09.05.2016.
 */
public interface GenreService {
    void addGenre(String genreText, Long movieId);
    List<Genre> getAllGenresInMovie(Long movieId);
    void deleteGenreFromMovie(Long genreId, Long movieId);
    void deleteGenresFromMovie(Long movieId);
    void deleteGenreFromMovieByName(String genreText, Long movieId);
    List<Genre> getAllGenres();
    List<Movie> gatAllMoviesByGenre(String value);
    Genre save(Genre actor);
    Movie updateGenresInMovie(Movie movie);
    Genre findByText(String text);
}
