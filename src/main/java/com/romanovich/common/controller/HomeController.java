package com.romanovich.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    protected static final String VIEW_NAME_HOMEPAGE = "home/home";

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String showStartPage() {
        LOGGER.debug("Rendering home page.");
        return VIEW_NAME_HOMEPAGE;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String showHomePage() {
        LOGGER.debug("Rendering home page.");
        return VIEW_NAME_HOMEPAGE;
    }
}
