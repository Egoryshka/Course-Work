package com.romanovich.security.util;

import com.romanovich.user.model.SocialMediaService;
import com.romanovich.user.model.User;
import com.romanovich.security.dto.ExampleUserDetails;
import com.romanovich.security.dto.UserDetailsDTOAssert;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

public class SecurityContextAssert extends AbstractAssert<SecurityContextAssert, SecurityContext> {

    private SecurityContextAssert(SecurityContext actual) {
        super(actual, SecurityContextAssert.class);
    }

    public static SecurityContextAssert assertThat(SecurityContext actual) {
        return new SecurityContextAssert(actual);
    }

    public SecurityContextAssert userIsAnonymous() {
        isNotNull();

        Authentication authentication = actual.getAuthentication();

        Assertions.assertThat(authentication)
                .overridingErrorMessage("Expected authentication to be <null> but was <%s>.",
                        authentication
                )
                .isNull();

        return this;
    }

    public SecurityContextAssert loggedInUserIs(User user) {
        isNotNull();

        ExampleUserDetails loggedIn = (ExampleUserDetails) actual.getAuthentication().getPrincipal();

        Assertions.assertThat(loggedIn)
                .overridingErrorMessage("Expected logged in user to be <%s> but was <null>",
                        user
                )
                .isNotNull();

        UserDetailsDTOAssert.assertThat(loggedIn)
                .hasFirstName(user.getFirstName())
                .hasId(user.getId())
                .hasLastName(user.getLastName())
                .hasUsername(user.getEmail())
                .isActive()
                .isRegisteredUser();

        return this;
    }

    public SecurityContextAssert loggedInUserHasPassword(String password) {
        isNotNull();

        ExampleUserDetails loggedIn = (ExampleUserDetails) actual.getAuthentication().getPrincipal();

        Assertions.assertThat(loggedIn)
                .overridingErrorMessage("Expected logged in user to be <not null> but was <null>")
                .isNotNull();

        UserDetailsDTOAssert.assertThat(loggedIn)
                .hasPassword(password);

        return this;
    }

    public SecurityContextAssert loggedInUserIsRegisteredByUsingNormalRegistration() {
        isNotNull();

        ExampleUserDetails loggedIn = (ExampleUserDetails) actual.getAuthentication().getPrincipal();

        Assertions.assertThat(loggedIn)
                .overridingErrorMessage("Expected logged in user to be <not null> but was <null>")
                .isNotNull();

        UserDetailsDTOAssert.assertThat(loggedIn)
                .isRegisteredByUsingFormRegistration();

        return this;
    }
}
