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
import java.util.Collections;
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

//    @RequestMapping(value = "/get-tags-list",method = RequestMethod.GET)
//    public List<Actor> getAllActors(){
//        List<Actor> tags=actorService.getAllActors();
//        return tags;
//    }
//    @RequestMapping(value = "/get-tags",method = RequestMethod.GET)
//    public ArrayList<Long> getPostActorIds(@RequestParam Long postId){
//        List<Actor> allActors= movieService.findOne(postId).getActors();
//        ArrayList<Long> tagIds = new ArrayList<Long>();
//        for(Actor tag : allActors ){
//            if(tag.getWeight()!=0)
//                tagIds.add(tag.getActorId());
//        }
//        return tagIds;
//    }
//    @RequestMapping(value = "/getCloudActors",method = RequestMethod.GET)
//    public List<Actor> getCloudActors(){
//        List<Actor> tags=actorService.getAllActors();
//        Collections.sort(tags);
//        try {
//            tags = tags.subList(0,15);
//            Collections.shuffle(tags);
//            return tags;
//        }catch (IndexOutOfBoundsException e){
//            Collections.shuffle(tags);
//            return tags;
//        }
//    }
}


