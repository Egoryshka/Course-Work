package com.romanovich.user.service;

import com.romanovich.user.dto.OrderDTO;

import java.util.Map;

/**
 * Created by Егор on 09.05.2016.
 */
public interface OrderService {
    OrderDTO prepareDTO(Map<Long, Integer> basket);
}
