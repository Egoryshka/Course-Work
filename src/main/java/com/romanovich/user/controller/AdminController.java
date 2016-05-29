package com.romanovich.user.controller;

import com.romanovich.user.model.User;
import com.romanovich.user.service.UserService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


@RestController
public class AdminController {

    @Autowired
    UserService userService;

}
