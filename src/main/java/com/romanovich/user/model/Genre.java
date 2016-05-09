package com.romanovich.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Егор on 09.05.2016.
 */

@Entity
@Indexed
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue
    private Long id;

    @Field
    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @JsonIgnore
    @ManyToMany( mappedBy = "genres", fetch = FetchType.EAGER)
    private List<Movie> movies;

    public Genre() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", movies=" + movies +
                '}';
    }
}
