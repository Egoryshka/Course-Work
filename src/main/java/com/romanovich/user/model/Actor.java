package com.romanovich.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Егор on 09.05.2016.
 */
public class Actor {
    @Id
    @GeneratedValue
    private Long id;

    @Field
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @JsonIgnore
    @ManyToMany( mappedBy = "actors",fetch = FetchType.EAGER)
    private List<Movie> movies;

    public Actor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", movies=" + movies +
                '}';
    }
}
