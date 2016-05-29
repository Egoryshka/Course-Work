package com.romanovich.user.service.Impl;

import com.romanovich.user.dto.OrderDTO;
import com.romanovich.user.model.Movie;
import com.romanovich.user.repository.MovieRepository;
import com.romanovich.user.repository.OrderRepository;
import com.romanovich.user.repository.UserRepository;
import com.romanovich.user.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Егор on 09.05.2016.
 */

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    MovieRepository movieRepository;

    UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OrderDTO prepareDTO(Map<Long, Integer> basket) {
        OrderDTO dto = new OrderDTO();
        List<Movie> list = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : basket.entrySet()) {
            Movie movie = movieRepository.findOne(entry.getKey());
            for (int i = 0; i < entry.getValue(); i++) {
                list.add(movie);
            }
        }
        dto.setMoviesList(list);
        return dto;
    }
}
