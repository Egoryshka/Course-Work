package com.romanovich.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.*;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Егор on 09.05.2016.
 */
@Entity
@Table(name = "movies")
public class Movie implements Comparable<Movie>{
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title", columnDefinition="TEXT")
    @Field(index=Index.YES, analyze= Analyze.YES, store= Store.NO)
    private String title;

    @Column(name = "year", columnDefinition = "INT")
    @Field(index=Index.YES, analyze= Analyze.YES, store= Store.NO)
    private Integer year;

    @Column(name = "country", columnDefinition="TEXT")
    @Field(index=Index.YES, analyze= Analyze.YES, store= Store.NO)
    private String country;

    @Column(name = "trailer", columnDefinition="TEXT")
    private String trailer;

    @Column(name = "poster", columnDefinition="TEXT")
    private String poster;

    @Column(name = "notice", columnDefinition="TEXT")
    @Field(index=Index.YES, analyze= Analyze.YES, store= Store.NO)
    private String notice;

    @IndexedEmbedded
    @ManyToMany(fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "movies_genres",
            joinColumns = { @JoinColumn(name = "MOVIE_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "GENRE_ID",
                    nullable = false, updatable = false) })
    private List<Genre> genres;

    @IndexedEmbedded
    @ManyToMany(fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "movies_actors",
            joinColumns = { @JoinColumn(name = "MOVIE_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "ACTOR_ID",
                    nullable = false, updatable = false) })
    private List<Actor> actors;

    @Column(name = "cost")
    private Long cost;

    @OneToMany(mappedBy = "movie",cascade=CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<Rating> ratings;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany( mappedBy = "movies",fetch = FetchType.LAZY)
    private List<Order> orders;

    public Movie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }


    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public void addRating(Rating rating){ratings.add(rating);}

    public void removeRating(Rating rating) {
        for (int i = 0; i < ratings.size(); i++) {
            if (ratings.get(i).getId().equals(rating.getId())) {
                ratings.remove(i);
                break;
            }
        }
    }

    @Override
    public int compareTo(Movie o) {
        if(this.getRatingInNumber() < o.getRatingInNumber())return 1;
        if(this.getRatingInNumber() > o.getRatingInNumber())return -1;
        return 0;
    }

    private Integer getRatingInNumber() {
        Integer result = 0;
        for (Rating rait : this.getRatings()) {
            if (rait.getPositive()) {
                result++;
            } else
                result--;
        }
        return result;
    }
}
