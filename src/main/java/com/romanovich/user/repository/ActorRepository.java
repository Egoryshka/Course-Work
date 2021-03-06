package com.romanovich.user.repository;

import com.romanovich.user.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Егор on 09.05.2016.
 */
public interface ActorRepository extends JpaRepository<Actor, Long> {
    Actor findByName(String actorName);
}
