package com.romanovich.user.controller;


import com.romanovich.config.UnitTestContext;
import com.romanovich.config.WebAppContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAppContext.class, UnitTestContext.class})
@WebAppConfiguration
public class PagesControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webAppContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                .build();
    }

    @Test
    public void showMovieTest() throws Exception {
        mockMvc.perform(get("/home/movie/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("movie/moviePage"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/movie/moviePage.jsp"));
    }

    @Test
    public void showBasketTest() throws Exception {
        mockMvc.perform(get("/home/basket"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/basket"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/home/basket.jsp"));
    }

    @Test
    public void showAdminPage() throws Exception {
        mockMvc.perform(get("/admin/mainAdminPage"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/mainAdminPage"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/mainAdminPage.jsp"));
    }

    @Test
    public void showAddMoviePage() throws Exception {
        mockMvc.perform(get("/admin/addMoviePage"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/addMoviePage"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/addMoviePage.jsp"));
    }

}
