package com.romanovich.user.controller;

import com.romanovich.user.model.*;
import com.romanovich.user.service.*;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 10.02.2016.
 */
@RestController
public class MovieController {

    private static final String CLOUD_PROFILE = "cloudinary://685379273612296:YPWoJVObyT5D9sZ1YSMf7kkHcg4@itracloud";

    @Autowired
    private ActorService actorService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getPopularMovies", method = RequestMethod.GET)
    public List<Movie> getPopularMovies() {
        return movieService.getPopularTopTen();
    }

    @RequestMapping(value = "/saveMovie", method = RequestMethod.POST)
    public
    @ResponseBody
    Integer saveMovie(@RequestBody String data) throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Movie movie = mapper.readValue(data, Movie.class);
        if (movie.getId() == null) {
            List<Actor> actors = actorService.getAllActors();
            movie = actorService.updateActorsInMovie(movie, actors);
            List<Genre> genres = genreService.getAllGenres();
            movie = genreService.updateGenresInMovie(movie, genres);
        } else {
            List<Actor> actors = actorService.getAllActorsInMovie(movie.getId());
            for (Actor actor : actors) {
                actorService.deleteActorFromMovie(actor.getId(), movie.getId());
            }
            actors = actorService.getAllActors();
            movie = actorService.updateActorsInMovie(movie, actors);

            List<Genre> genres = genreService.getAllGenresInMovie(movie.getId());
            for (Genre genre : genres) {
                genreService.deleteGenreFromMovie(genre.getId(), movie.getId());
            }
            genres = genreService.getAllGenres();
            movie = genreService.updateGenresInMovie(movie, genres);
        }
        movieService.save(movie);
        return 200;
    }


    @RequestMapping(value = "/deleteMovie", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteMovie(@RequestBody String data, Principal principal) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Movie movie = mapper.readValue(data, Movie.class);

        List<Actor> actors = actorService.getAllActorsInMovie(movie.getId());
        for (Actor actor : actors) {
            actorService.deleteActorFromMovie(actor.getId(), movie.getId());
        }

        List<Genre> genres = genreService.getAllGenresInMovie(movie.getId());
        for (Genre genre : genres) {
            genreService.deleteGenreFromMovie(genre.getId(), movie.getId());
        }

        movieService.delete(movie);
        return "OK";
    }

    @RequestMapping(value = "/saveImage", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    String saveImage(@RequestBody String data) throws IOException {
        Cloudinary cloudinary = new Cloudinary(CLOUD_PROFILE);
        Map map = cloudinary.uploader().upload(data, ObjectUtils.emptyMap());
        String url = map.get("url").toString();
        JSONObject obj = new JSONObject();
        obj.put("poster", url);
        return obj.toString();
    }

    @RequestMapping(value = "/getSingleMovie", method = RequestMethod.POST)
    public Movie getSingleMovie(@RequestBody Long id) {
        return movieService.findOne(id);

    }

    @RequestMapping(value = "/getAllMovies", method = RequestMethod.GET)
    public List<Movie> getAllMovies() {
        List<Movie> result = movieService.findAll();
        Collections.reverse(result);
        return result;
    }

    @RequestMapping(value = "/getMoviesByGenre", method = RequestMethod.POST)
    public List<Movie> getMoviesByGenre(@RequestBody String genreText) {
        Genre genre = genreService.findByText(genreText);
        List<Movie> movies = genre.getMovies();
        Collections.reverse(movies);
        return movies;
    }

    @RequestMapping(value = "/getMoviesByActor", method = RequestMethod.POST)
    public List<Movie> getMoviesByActor(@RequestBody String actorName) {
        Actor actor = actorService.findByText(actorName);
        List<Movie> movies = actor.getMovies();
        Collections.reverse(movies);
        return movies;
    }

    @RequestMapping(value = "/changeRating", method = RequestMethod.POST)
    public Integer changeRating(@RequestBody String ratings, Principal principal) throws IOException {
        if (principal != null) {
            ObjectMapper mapper = new ObjectMapper();
            MovieRating movieRating = mapper.readValue(ratings, MovieRating.class);
            Movie movie = movieService.findOne(movieRating.getPostId());
            User user = userService.findUser(principal.getName());
            Rating rating = new Rating();
            rating.setUser(user);
            rating.setMovie(movie);
            rating.setPositive(movieRating.isPositive());
            ratingService.saveOrDeleteRating(rating, movie,user);
            movieService.save(movie);
            return ratingService.getScore(movie);
        }
        return null;
    }

    @RequestMapping(value = "/getRating", method = RequestMethod.POST)
    public Integer getRating(@RequestBody Long id) {
        Movie movie = movieService.findOne(id);
        return ratingService.getScore(movie);
    }

    @RequestMapping(value = "/getPersonalRating", method = RequestMethod.POST)
    public
    @ResponseBody
    Rating getPersonalRating(@RequestBody Long id, Principal principal) {
        if (principal != null) {
            Movie movie = movieService.findOne(id);
            User user = userService.findUser(principal.getName());
            return ratingService.findByUserAndMovie(user, movie);
        }
        return null;
    }
}