package com.romanovich.config;

import com.romanovich.user.search.SearchService;
import com.romanovich.user.service.*;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import static org.mockito.Mockito.mock;

/**
 * @author Petri Kainulainen
 */
@Configuration
public class UnitTestContext {

    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    @Bean
    public ActorService actorService() {
        return mock(ActorService.class);
    }

    @Bean
    public GenreService genreService() {
        return mock(GenreService.class);
    }

    @Bean
    public MovieService movieService() {
        return mock(MovieService.class);
    }

    @Bean
    public OrderService orderService() {
        return mock(OrderService.class);
    }

    @Bean
    public RatingService ratingService() {
        return mock(RatingService.class);
    }

    @Bean
    public SearchService searchService() {
        return mock(SearchService.class);
    }

    @Bean
    public UserService userService() {
        return mock(UserService.class);
    }
}
