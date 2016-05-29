package com.romanovich.user.repository;

import com.romanovich.user.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Егор on 09.05.2016.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByComplete(boolean isComplete);
}
