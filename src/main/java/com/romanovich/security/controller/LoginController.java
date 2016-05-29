package com.romanovich.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    protected static final String VIEW_NAME_LOGIN_PAGE = "home/signIn";

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String showStartPage() {
        LOGGER.debug("Rendering SignIn page");
        return VIEW_NAME_LOGIN_PAGE;
    }
}
