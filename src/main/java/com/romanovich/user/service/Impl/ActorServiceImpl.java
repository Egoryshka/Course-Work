package com.romanovich.user.service.Impl;

import com.romanovich.user.model.Actor;
import com.romanovich.user.model.Movie;
import com.romanovich.user.repository.ActorRepository;
import com.romanovich.user.repository.MovieRepository;
import com.romanovich.user.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Егор on 09.05.2016.
 */

@Service
@Transactional
public class ActorServiceImpl implements ActorService {

    ActorRepository actorRepository;
    MovieRepository movieRepository;

    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }


    @Override
    public void addActor(String actorName, Long movieId){
        Actor actor = actorRepository.findByText(actorName);
        Movie movie = movieRepository.findOne(movieId);
        actor = nullActorCheck(actorName, actor);
        addActorToMovie(actor, movie);
        saveMovieAndActor(actor, movie);
    }

    private Actor nullActorCheck(String name, Actor actor) {
        if(actor == null) {
            actor = new Actor();
            actor.setName(name);
        }
        return actor;
    }

    private void saveMovieAndActor(Actor actor, Movie movie) {
        actorRepository.save(actor);
        movieRepository.save(movie);
    }

    private void addActorToMovie(Actor actor, Movie movie) {
        generateActor(actor, movie);
        List<Actor> actors = movie.getActors();
        actors.add(actor);
        movie.setActors(actors);
    }

    private void generateActor(Actor actor, Movie movie) {
        List<Movie> movies = actor.getMovies();
        movies.add(movie);
        actor.setMovies(movies);
    }

    @Override
    public List<Actor> getAllActorsInMovie(Long movieId) {
        Movie movie = movieRepository.findOne(movieId);
        return movie.getActors();
    }

    @Override
    public void deleteActorFromMovie(Long actorId, Long movieId){
        Actor actor = actorRepository.findOne(actorId);
        deleteActor(movieId, actor);
    }

    private void deleteActor(Long movieId, Actor actor) {
        Movie movie = movieRepository.findOne(movieId);
        movie.getActors().remove(actor);
        actor.getMovies().remove(movie);
        saveMovieAndActor(actor, movie);
    }

    @Override
    public void deleteActorFromMovieByName(String actorName, Long movieId){
        Actor actor = actorRepository.findByText(actorName);
        deleteActor(movieId, actor);
    }

    @Override
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @Override
    public List<Movie> gatAllMoviesByActor(String text) {
        return actorRepository.findByText(text).getMovies();
    }

    @Override
    public Actor save(Actor actor) {
        return actorRepository.save(actor);
    }

    @Override
    public Movie updateActorsInMovie(Movie movie, List<Actor> actors){
        for (Actor movieActor : movie.getActors()) {
            for (Actor actor : actors) {
                if (actor.getName().equals(movieActor.getName())) {
                    movieActor.setMovies(actor.getMovies());
                    movieActor.setId(actor.getId());
                }
            }
            movieActor.getMovies().add(movie);
            save(movieActor);
        }
        return movie;
    }

    @Override
    public Actor findByText(String actorName) {
        return actorRepository.findByText(actorName);
    }
}
