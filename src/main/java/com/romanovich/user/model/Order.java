package com.romanovich.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.romanovich.user.dto.OrderDTO;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.ArrayList;
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
    @LazyToOne(LazyToOneOption.FALSE)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "orders_movies",
            joinColumns = { @JoinColumn(name = "ORDER_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "MOVIE_ID",
                    nullable = false, updatable = false) })
    private List<Movie> movies = new ArrayList<>();

    private String name;

    private String address;

    private String phone;

    private Long summaryCost = 0L;

    private boolean complete = false;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public static Order getOrderFromDTO(OrderDTO dto, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setDate(new Date());
        order.setMovies(dto.getMoviesList());
        order.setName(dto.getName());
        order.setSummaryCost(dto.getSummaryCost());
        order.setAddress(dto.getAddress());
        order.setPhone(dto.getPhone());
        return order;
    }
}
