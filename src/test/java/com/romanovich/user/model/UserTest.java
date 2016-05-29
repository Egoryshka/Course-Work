package com.romanovich.user.model;

import org.junit.Test;

import static com.romanovich.user.model.UserAssert.assertThat;


public class UserTest {

    private static final String EMAIL = "test@movieshop.by";
    private static final String FIRST_NAME = "Admin";
    private static final String LAST_NAME = "Admin";
    private static final String PASSWORD = "password";

    @Test
    public void buildRegisteredUserTest() {
        User user = User.getBuilder()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .build();

        assertThat(user)
                .hasNoId()
                .hasEmail(EMAIL)
                .hasFirstName(FIRST_NAME)
                .hasLastName(LAST_NAME)
                .hasPassword(PASSWORD)
                .isRegisteredUser()
                .isRegisteredByUsingNormalRegistration();
    }

}
