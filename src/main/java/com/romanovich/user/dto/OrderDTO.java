package com.romanovich.user.dto;

import com.romanovich.user.model.Movie;
import com.romanovich.user.model.User;

import java.util.*;

/**
 * Created by Егор on 29.05.2016.
 */
public class OrderDTO {

    private Long orderId;
    private Date date;
    private List<Movie> movies = new ArrayList<>();
    private List<Integer> count = new ArrayList<>();
    private String name;
    private String address;
    private String phone;
    private Long summaryCost = 0L;

    public OrderDTO() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public List<Movie> getMoviesList() {
        List<Movie> result = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            for (int j = 0; j < count.get(i); j++) {
                result.add(movies.get(i));
            }
        }
        return result;
    }

    public void setMoviesList(List<Movie> movies) {
        List<Movie> list = new ArrayList<>();
        Map<Long, Integer> temporary = new HashMap<>();
        for (Movie movie : movies) {
            Long id = movie.getId();
            if (!temporary.containsKey(id)) {
                temporary.put(id, 1);
                list.add(movie);
            }
            else {
                int count = temporary.remove(id) + 1;
                temporary.put(id, count);
            }
        }
        this.movies.clear();
        this.count.clear();
        this.summaryCost = 0L;
        for (Movie movie : list) {
            int count = temporary.get(movie.getId());
            this.movies.add(movie);
            this.count.add(count);
            this.summaryCost += movie.getCost() * count;
        }
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void setCount(List<Integer> count) {
        this.count = count;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Integer> getCount() {
        return count;
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
}
