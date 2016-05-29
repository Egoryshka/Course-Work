package com.romanovich.security.util;

import com.romanovich.user.model.SocialMediaService;
import com.romanovich.user.model.User;
import com.romanovich.user.model.UserBuilder;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.romanovich.security.util.SecurityContextAssert.assertThat;

public class SecurityUtilTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "test@movieshop.by";
    private static final String FIRST_NAME = "Admin";
    private static final String LAST_NAME = "Admin";
    private static final String PASSWORD = "password";

    @Test
    public void logInUserTest() {
        User user = new UserBuilder()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .id(ID)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .build();

        SecurityUtil.logInUser(user);

        assertThat(SecurityContextHolder.getContext())
                .loggedInUserIs(user)
                .loggedInUserHasPassword(PASSWORD)
                .loggedInUserIsRegisteredByUsingNormalRegistration();
    }
}
