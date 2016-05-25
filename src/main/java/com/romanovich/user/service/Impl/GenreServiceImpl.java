package com.romanovich.user.service.Impl;

import com.romanovich.user.model.Genre;
import com.romanovich.user.model.Movie;
import com.romanovich.user.repository.GenreRepository;
import com.romanovich.user.repository.MovieRepository;
import com.romanovich.user.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Егор on 09.05.2016.
 */

@Service
@Transactional
public class GenreServiceImpl implements GenreService {

    GenreRepository genreRepository;
    MovieRepository movieRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreepository, MovieRepository movieRepository) {
        this.genreRepository = genreepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void addGenre(String genreText, Long movieId){
        Genre genre = genreRepository.findByText(genreText);
        Movie movie = movieRepository.findOne(movieId);
        genre = nullGenreCheck(genreText, genre);
        addGenreToMovie(genre, movie);
        saveMovieAndGenre(genre, movie);
    }

    private Genre nullGenreCheck(String name, Genre genre) {
        if(genre == null) {
            genre = new Genre();
            genre.setText(name);
        }
        return genre;
    }

    private void saveMovieAndGenre(Genre genre, Movie movie) {
        genreRepository.save(genre);
        movieRepository.save(movie);
    }

    private void addGenreToMovie(Genre genre, Movie movie) {
        generateGenre(genre, movie);
        List<Genre> genres = movie.getGenres();
        genres.add(genre);
        movie.setGenres(genres);
    }

    private void generateGenre(Genre genre, Movie movie) {
        List<Movie> movies = genre.getMovies();
        movies.add(movie);
        genre.setMovies(movies);
    }

    @Override
    public List<Genre> getAllGenresInMovie(Long movieId) {
        Movie movie = movieRepository.findOne(movieId);
        return movie.getGenres();
    }

    @Override
    public void deleteGenreFromMovie(Long genreId, Long movieId){
        deleteGenre(movieId, genreId);
    }

    @Override
    public void deleteGenresFromMovie(Long movieId){
        List<Genre> genres = movieRepository.findOne(movieId).getGenres();
        int len = genres.size();
        for (int i = 0; i < len; i++) {
            deleteGenre(movieId, genres.get(0).getId());
        }
    }

    private void deleteGenre(Long movieId, Long genreId) {
        Movie movie = movieRepository.findOne(movieId);
        Genre genre = genreRepository.findOne(genreId);
        movie.getGenres().remove(genre);
        genre.getMovies().remove(movie);
        saveMovieAndGenre(genre, movie);
    }

    @Override
    public void deleteGenreFromMovieByName(String genreName, Long movieId){
        Genre genre = genreRepository.findByText(genreName);
        deleteGenre(movieId, genre.getId());
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public List<Movie> gatAllMoviesByGenre(String text) {
        return genreRepository.findByText(text).getMovies();
    }

    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Movie updateGenresInMovie(Movie movie){
        List<Genre> genres = getAllGenres();
        for (Genre movieGenre : movie.getGenres()) {
            for (Genre genre : genres) {
                if (genre.getText().equals(movieGenre.getText())) {
                    movieGenre.setMovies(genre.getMovies());
                    movieGenre.setId(genre.getId());
                }
            }
            movieGenre.getMovies().add(movie);
            save(movieGenre);
        }
        return movie;
    }

    @Override
    public Genre findByText(String genreName) {
        return genreRepository.findByText(genreName);
    }
}
