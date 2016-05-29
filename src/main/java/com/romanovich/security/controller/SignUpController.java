package com.romanovich.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class SignUpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignUpController.class);

    protected static final String VIEW_NAME_SIGNUP_PAGE = "home/register";

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String redirectRequestToRegistrationPage() {
        LOGGER.debug("Rendering registration page.");
        return VIEW_NAME_SIGNUP_PAGE;
    }
}
