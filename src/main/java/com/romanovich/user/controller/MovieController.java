package com.romanovich.user.controller;

import com.romanovich.user.model.*;
import com.romanovich.user.service.*;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 10.02.2016.
 *
 * Modified by Егор on 25.05.2016
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
    HttpStatus saveMovie(@RequestBody String data) throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Movie movie = mapper.readValue(data, Movie.class);
        movie = actorService.updateActorsInMovie(movie);
        movie = genreService.updateGenresInMovie(movie);
        movieService.save(movie);

        return HttpStatus.OK;
    }

    @RequestMapping(value = "/deleteMovie", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpStatus deleteMovie(@RequestBody String data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Movie movie = mapper.readValue(data, Movie.class);
        actorService.deleteActorsFromMovie(movie.getId());
        genreService.deleteGenresFromMovie(movie.getId());
        movieService.delete(movie);
        return HttpStatus.OK;
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

    @RequestMapping(value = "/getAllMovies", method = RequestMethod.GET, produces = "application/json")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
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
            Movie movie = movieService.findOne(movieRating.getMovieId());
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

    @RequestMapping(value = "/addMovieToBasket", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpStatus addMovieToBasket(@RequestBody Long movieId, Principal principal, HttpSession session) {
        if (principal != null) {
            @SuppressWarnings("unchecked")
            Map<Long, Integer> basket = (Map<Long, Integer>)session.getAttribute("basket");
            if (basket == null) {
                basket = new HashMap<>();
            }
            if (!basket.containsKey(movieId)) {
                basket.put(movieId, 1);
                session.setAttribute("basket", basket);
            }
            else {
                Integer count = basket.remove(movieId);
                basket.put(movieId, ++count);
                session.setAttribute("basket", basket);
            }
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }
}