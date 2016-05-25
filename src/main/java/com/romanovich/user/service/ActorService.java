package com.romanovich.user.service;

import com.romanovich.user.model.Actor;
import com.romanovich.user.model.Movie;

import java.util.List;

/**
 * Created by Егор on 09.05.2016.
 */
public interface ActorService {
    void addActor(String actorName, Long movieId);
    List<Actor> getAllActorsInMovie(Long movieId);
    void deleteActorFromMovie(Long actorId, Long movieId);
    void deleteActorsFromMovie(Long movieId);
    void deleteActorFromMovieByName(String actorName, Long movieId);
    List<Actor> getAllActors();
    List<Movie> gatAllMoviesByActor(String value);
    Actor save(Actor actor);
    Movie updateActorsInMovie(Movie movie);
    Actor findByText(String text);
}
