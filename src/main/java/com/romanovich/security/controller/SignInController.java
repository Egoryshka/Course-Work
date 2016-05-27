package com.romanovich.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Alex on 07.02.2016.
 */
@Controller
public class SignInController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignInController.class);

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String showStartPage() {
        LOGGER.debug("Rendering SignIn page");

        return "redirect:home/signIn";
    }
}
