package com.romanovich.user.controller;

import com.romanovich.user.model.Actor;
import com.romanovich.user.service.ActorService;
import com.romanovich.user.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 12.02.2016.
 */
@RestController
public class ActorController {

    @Autowired
    MovieService movieService;

    @Autowired
    ActorService actorService;

    @RequestMapping(value = "/autocompleteActors",method = RequestMethod.GET)
    public List<Actor> getAllActors(){
        return actorService.getAllActors();
    }

    @RequestMapping(value = "/getActorsIds",method = RequestMethod.GET)
    public ArrayList<Long> getMovieActorIds(@RequestParam Long movieId){
        List<Actor> allActors= movieService.findOne(movieId).getActors();
        ArrayList<Long> actorsIds = new ArrayList<Long>();
        for(Actor actor : allActors ){
            actorsIds.add(actor.getId());
        }
        return actorsIds;
    }
}


