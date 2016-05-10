package com.romanovich.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Егор on 09.05.2016.
 */

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "date", columnDefinition="DATE")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "orders_movies",
            joinColumns = { @JoinColumn(name = "ORDER_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "MOVIE_ID",
                    nullable = false, updatable = false) })
    private List<Movie> movies;

    private String address;

    private String phone;

    private Long summaryCost = 0L;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getSummaryCost() {
        return summaryCost;
    }

    public void setSummaryCost(Long summaryCost) {
        this.summaryCost = summaryCost;
    }
}
