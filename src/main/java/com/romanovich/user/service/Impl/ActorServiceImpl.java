package com.romanovich.user.service.Impl;

import com.romanovich.user.repository.ActorRepository;
import com.romanovich.user.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Егор on 09.05.2016.
 */

@Service
@Transactional
public class ActorServiceImpl implements ActorService {

    ActorRepository actorRepository;

    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }
}
