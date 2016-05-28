package com.romanovich.user.controller;

import com.romanovich.user.model.User;
import com.romanovich.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import java.security.Principal;

/**
 * Created by Alex on 14.02.2016.
 */
@Controller
public class PagesController {
    @Autowired
    UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PagesController.class);


    protected static final String VIEW_NAME_SIGNIN_PAGE = "home/signIn";
//    protected static final String VIEW_NAME_PROFILEPAGE_PAGE = "user/profilePage";
//    protected static final String VIEW_NAME_TEMPLATES_PAGE = "user/templates";
    protected static final String VIEW_NAME_MOVIE_PAGE = "movie/moviePage";
//    protected static final String VIEW_NAME_USERHOMEPAGE_PAGE = "user/userHomePage";
    protected static final String VIEW_NAME_STARTPAGE_PAGE = "home/home";
    protected static final String VIEW_NAME_ADMIN_PAGE = "admin/mainAdminPage";
    protected static final String VIEW_ADMIN_ADD_NEW_MOVIE = "admin/addMoviePage";


    @RequestMapping(value = "/home/signIn", method = RequestMethod.GET)
    public String showSignIn(WebRequest request) {
        LOGGER.debug("Rendering SignIn page.");
        return VIEW_NAME_SIGNIN_PAGE;
    }

//    @RequestMapping(value = "/user/profilePage", method = RequestMethod.GET)
//    public String showProfilePage(WebRequest request) {
//        LOGGER.debug("Rendering profilePage page.");
//
//        return VIEW_NAME_PROFILEPAGE_PAGE;
//    }

//    @RequestMapping(value = "/user/templates", method = RequestMethod.GET)
//    public String showTemplatesPage(WebRequest request) {
//        LOGGER.debug("Rendering Templates page.");
//
//        return VIEW_NAME_TEMPLATES_PAGE;
//    }

    @RequestMapping(value = "/home/movie/{id}", method = RequestMethod.GET)
    public String showSinglePost(@PathVariable Long id,WebRequest webRequest) {
        LOGGER.debug("Rendering movie page.");
        return VIEW_NAME_MOVIE_PAGE;
    }

//    @RequestMapping(value = "/user/userHomePage/{userId}", method = RequestMethod.GET)
//    public String showUserHomePage(@PathVariable Long userId,WebRequest webRequest, Principal principal) {
//        if(principal!=null) {
//            User user=userService.findUser(principal.getName());
//            if (user.getId().equals(userId))
//                return "index";
//        }
//        LOGGER.debug("Rendering UserHomePage page.");
//
//        return VIEW_NAME_USERHOMEPAGE_PAGE;
//    }

//    @RequestMapping(value = "/home/home/category={category}", method = RequestMethod.GET)
//    public String showStartPageByCategory(@PathVariable String category, WebRequest webRequest) {
//        LOGGER.debug("Rendering UserHomePage page.");
//        return VIEW_NAME_STARTPAGE_PAGE;
//    }

//    @RequestMapping(value = "/home/home/tags={tags}", method = RequestMethod.GET)
//    public String showStartPageByTags(@PathVariable String tags, WebRequest webRequest) {
//        LOGGER.debug("Rendering UserHomePage page.");
//        return VIEW_NAME_STARTPAGE_PAGE;
//    }

    @RequestMapping(value = "/admin/mainAdminPage/**", method = RequestMethod.GET)
    public String showAdminPage(WebRequest webRequest) {
        LOGGER.debug("Rendering Admin page.");
        return VIEW_NAME_ADMIN_PAGE;
    }

    @RequestMapping(value = "/admin/addMoviePage/**", method = RequestMethod.GET)
    public String showAddMoviePage(WebRequest webRequest) {
        LOGGER.debug("Rendering Admin add movie page.");
        return VIEW_ADMIN_ADD_NEW_MOVIE;
    }
}
