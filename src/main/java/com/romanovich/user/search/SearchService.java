package com.romanovich.user.search;

import com.romanovich.user.model.Movie;
import java.util.List;

/**
 * Created by Alex on 17.02.2016.
 */
public interface SearchService {
    List<Movie> search(String text);
}

