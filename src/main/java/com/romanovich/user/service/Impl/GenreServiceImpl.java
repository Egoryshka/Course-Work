package com.romanovich.user.service.Impl;

import com.romanovich.user.repository.GenreRepository;
import com.romanovich.user.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Егор on 09.05.2016.
 */

@Service
@Transactional
public class GenreServiceImpl implements GenreService {

    GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
}
