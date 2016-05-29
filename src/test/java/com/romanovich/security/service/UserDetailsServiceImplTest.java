package com.romanovich.security.service;

import com.romanovich.user.model.User;
import com.romanovich.user.repository.UserRepository;
import com.romanovich.security.dto.ExampleUserDetails;
import com.romanovich.user.model.UserBuilder;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static com.romanovich.security.dto.UserDetailsDTOAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {
    private static final Long ID = 1L;
    private static final String EMAIL = "test@movieshop.by";
    private static final String FIRST_NAME = "Admin";
    private static final String LAST_NAME = "Admin";
    private static final String PASSWORD = "password";

    private UserDetailsServiceImpl service;

    @Mock
    private UserRepository repositoryMock;

    @Before
    public void setUp() {
        service = new UserDetailsServiceImpl(repositoryMock);
    }

    @Test
    public void loadByUsernameUserNotFoundTest() {
        when(repositoryMock.findByEmail(EMAIL)).thenReturn(null);

        catchException(service).loadUserByUsername(EMAIL);
        Assertions.assertThat(caughtException())
                .isExactlyInstanceOf(UsernameNotFoundException.class)
                .hasMessage("No user found with username: " + EMAIL)
                .hasNoCause();

        verify(repositoryMock, times(1)).findByEmail(EMAIL);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void loadByUsernameTest() {
        User found = new UserBuilder()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .id(ID)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .build();

        when(repositoryMock.findByEmail(EMAIL)).thenReturn(found);

        UserDetails user = service.loadUserByUsername(EMAIL);
        ExampleUserDetails actual = (ExampleUserDetails) user;

        assertThat(actual)
                .hasFirstName(FIRST_NAME)
                .hasId(ID)
                .hasLastName(LAST_NAME)
                .hasPassword(PASSWORD)
                .hasUsername(EMAIL)
                .isActive()
                .isRegisteredUser()
                .isRegisteredByUsingFormRegistration();

        verify(repositoryMock, times(1)).findByEmail(EMAIL);
        verifyNoMoreInteractions(repositoryMock);
    }
}
