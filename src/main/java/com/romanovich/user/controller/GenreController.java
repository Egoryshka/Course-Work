package com.romanovich.user.controller;

import com.romanovich.user.model.Genre;
import com.romanovich.user.service.GenreService;
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
public class GenreController {

    @Autowired
    MovieService movieService;

    @Autowired
    GenreService genreService;

    @RequestMapping(value = "/autocompleteGenres",method = RequestMethod.GET)
    public List<Genre> getAllGenres(){
        return genreService.getAllGenres();
    }

    @RequestMapping(value = "/getGenresIds",method = RequestMethod.GET)
    public ArrayList<Long> getMovieGenresIds(@RequestParam Long movieId){
        List<Genre> allGenres = movieService.findOne(movieId).getGenres();
        ArrayList<Long> genresIds = new ArrayList<Long>();
        for(Genre genre : allGenres ){
            genresIds.add(genre.getId());
        }
        return genresIds;
    }
}


