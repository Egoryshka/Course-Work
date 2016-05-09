package com.romanovich.config;

import com.romanovich.security.service.RepositoryUserDetailsService;
import com.romanovich.security.service.SimpleSocialUserDetailsService;
import com.romanovich.user.repository.*;
import com.romanovich.user.search.SearchServiceImpl;
import com.romanovich.user.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;


@Configuration
@ComponentScan(basePackages = {"com.romanovich.user.controller"})
@EnableWebSecurity
public class SecurityContext extends WebSecurityConfigurerAdapter {

    private static PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Bean
    public RatingServiceImpl repositoryRatingService(){return new RatingServiceImpl(ratingRepository);}
    @Bean
    public SearchServiceImpl searchServiceImpl(){return new SearchServiceImpl();}

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                //Spring Security ignores request to static resources such as CSS or JS files.
                .ignoring()
                    .antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //Configures form login
                .csrf().disable()

                .formLogin()
                    .loginPage("/home")
                    .loginProcessingUrl("/login/authenticate")
                    .failureUrl("/home?error=bad_credentials")
                //Configures the logout function
                .and()
                    .logout()
                        .deleteCookies("JSESSIONID")
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home")
                //Configures url based authorization
                .and()
                    .authorizeRequests()
                        //Anyone can access the urls
                        .antMatchers(
                                "/auth/**",
                                "/home/**",
                                "/signup/**",
                                "/user/register/**",
                                "/user/signIn/**",
                                "/user/profilePage/**",
                                "/user/userHomePage/**",
                                "/user/singlePost/**",
                                "user/templates/**",
                                "/getprofile",
                                "/banOrUnBanUser",
                                "/getUserInfo",
                                "/saveprofile",
                                "/savepost",
                                "/addpost",
                                "/getposts",
                                "/deletepost",
                                "/get-tags-list/**",
                                "/get-tags-/**",
                                "/saveavatar",
                                "/saveimage",
                                "/getSinglePost",
                                "/getUserName",
                                "/saveComment",
                                "/getComments",
                                "/getAllPosts",
                                "/getCategoryPosts",
                                "/search",
                                "/getRating",
                                "/getLikes",
                                "/changeLikes",
                                "/getPopularPosts",
                                "/getCloudTags",
                                "/getPersonalRating",
                                "/getTagsPost",
                                "/getUserHomePagePosts",
                                "/getAchievements",
                                "/getUserAchievements",
                                "/deletePostByAdmin",
                                "/savePostByAdmin"

                        ).permitAll()
                        //The rest of the our application is protected.

                        .antMatchers("/**").hasAnyRole("USER","ADMIN")
                        .antMatchers("/user/admin/**").hasRole("ADMIN")
                        .antMatchers("/getAllUsers").hasRole("ADMIN")



                //Adds the SocialAuthenticationFilter to Spring Security's filter chain.
                .and()
                    .apply(new SpringSocialConfigurer());
    }

    /**
     * Configures the authentication manager bean which processes authentication
     * requests.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    /**
     * This is used to hash the password of the user.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        if(encoder == null) {
            encoder = new BCryptPasswordEncoder(10);
        }
        return encoder;
    }

    /**
     * This bean is used to load the user specific data when social sign in
     * is used.
     */
    @Bean
    public SocialUserDetailsService socialUserDetailsService() {
        return new SimpleSocialUserDetailsService(userDetailsService());
    }

    /**
     * This bean is load the user specific data when form login is used.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new RepositoryUserDetailsService(userRepository);
    }



}
