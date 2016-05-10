package com.romanovich.user.controller;

import com.romanovich.user.model.Movie;
import com.romanovich.user.search.SearchService;
import com.romanovich.user.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 17.02.2016.
 */
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value="/search",method = RequestMethod.POST)
    public List<Movie> search(@RequestBody String search) {
        List<Movie> searchResults = new ArrayList<>();
        searchResults.addAll(searchService.search(search));
        return searchResults;
    }


}
