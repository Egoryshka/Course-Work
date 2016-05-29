package com.romanovich.user.service;

import com.romanovich.user.dto.OrderDTO;
import com.romanovich.user.model.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by Егор on 09.05.2016.
 */
public interface OrderService {
    OrderDTO prepareDTO(Map<Long, Integer> basket);
    void makeOrder(Order order);
    List<Order> getOrders();
    List<Order> getUnCompleteOrders();
    List<Order> getCompleteOrders();
}
