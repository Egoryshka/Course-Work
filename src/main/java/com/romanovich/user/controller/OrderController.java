package com.romanovich.user.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.romanovich.user.dto.OrderDTO;
import com.romanovich.user.model.Order;
import com.romanovich.user.model.User;
import com.romanovich.user.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.Map;

/**
 * Created by Егор on 29.05.2016.
 */

@RestController
public class OrderController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/prepareOrder", method = RequestMethod.GET)
    public OrderDTO prepareOrder(Principal principal, HttpSession session) {
        if (principal != null) {
            @SuppressWarnings("unchecked")
            Map<Long, Integer> basket = (Map<Long, Integer>)session.getAttribute("basket");
            String name = userService.findUser(principal.getName()).getFirstName();
            OrderDTO dto = orderService.prepareDTO(basket);
            dto.setName(name);
            return dto;
        }
        return null;
    }

    @RequestMapping(value = "/makeOrder", method = RequestMethod.POST)
    public
    @ResponseBody HttpStatus makeOrder(@RequestBody String data, Principal principal, HttpSession session) throws IOException, ParseException {
        if (principal != null) {
            session.removeAttribute("basket");
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            OrderDTO dto = mapper.readValue(data, OrderDTO.class);
            User user = userService.findUser(principal.getName());
            Order order = Order.getOrderFromDTO(dto, user);
            orderService.makeOrder(order);
            return HttpStatus.OK;
        }
        return null;
    }
}
