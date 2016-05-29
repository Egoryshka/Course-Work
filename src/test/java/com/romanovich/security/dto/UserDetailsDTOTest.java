package com.romanovich.security.dto;

import com.romanovich.user.model.Role;
import org.junit.Test;

import static com.romanovich.security.dto.UserDetailsDTOAssert.assertThat;

public class UserDetailsDTOTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "test@movieshop.by";
    private static final String FIRST_NAME = "Admin";
    private static final String LAST_NAME = "Admin";
    private static final String PASSWORD = "password";

    @Test
    public void userRegisteredTest() {
        ExampleUserDetails user = ExampleUserDetails.getBuilder()
                .firstName(FIRST_NAME)
                .id(ID)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .role(Role.ROLE_USER)
                .username(EMAIL)
                .build();

        assertThat(user).hasFirstName(FIRST_NAME)
                .hasId(ID)
                .hasLastName(LAST_NAME)
                .hasPassword(PASSWORD)
                .hasUsername(EMAIL)
                .isActive()
                .isRegisteredUser()
                .isRegisteredByUsingFormRegistration();
    }
}
