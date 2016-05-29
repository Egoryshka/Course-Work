package com.romanovich.user.service;

import com.romanovich.user.model.UserAssert;
import com.romanovich.user.dto.RegistrationForm;
import com.romanovich.user.dto.RegistrationFormBuilder;
import com.romanovich.user.model.SocialMediaService;
import com.romanovich.user.model.User;
import com.romanovich.user.repository.UserRepository;
import com.romanovich.user.service.Impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String EMAIL = "test@movieshop.by";
    private static final String FIRST_NAME = "Admin";
    private static final String LAST_NAME = "Admin";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encodedPassword";

    private UserServiceImpl service;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Mock
    private UserRepository repositoryMock;

    @Before
    public void setUp() {
        service = new UserServiceImpl(passwordEncoderMock, repositoryMock);
    }

    @Test
    public void registerNewUserAccountTest() throws DuplicateEmailException {
        RegistrationForm registration = new RegistrationFormBuilder()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .passwordVerification(PASSWORD)
                .build();

        when(repositoryMock.findByEmail(EMAIL)).thenReturn(null);
        when(passwordEncoderMock.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);

        when(repositoryMock.save(isA(User.class))).thenAnswer(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                return (User) arguments[0];
            }
        });

        User registered = service.registerNewUserAccount(registration);

        UserAssert.assertThat(registered)
                .hasEmail(EMAIL)
                .hasFirstName(FIRST_NAME)
                .hasLastName(LAST_NAME)
                .hasPassword(ENCODED_PASSWORD)
                .isRegisteredUser()
                .isRegisteredByUsingNormalRegistration();

        verify(repositoryMock, times(1)).findByEmail(EMAIL);

        verify(passwordEncoderMock, times(1)).encode(PASSWORD);
        verifyNoMoreInteractions(passwordEncoderMock);

        verify(repositoryMock, times(1)).save(registered);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void registerNewUserAccounWithDuplicateEmailTest() throws DuplicateEmailException {
        RegistrationForm registration = new RegistrationFormBuilder()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .passwordVerification(PASSWORD)
                .build();

        when(repositoryMock.findByEmail(EMAIL)).thenReturn(new User());

        catchException(service).registerNewUserAccount(registration);

        Assertions.assertThat(caughtException())
                .isExactlyInstanceOf(DuplicateEmailException.class)
                .hasMessage("The email address: " + EMAIL + " is already in use.")
                .hasNoCause();

        verify(repositoryMock, times(1)).findByEmail(EMAIL);
        verifyNoMoreInteractions(repositoryMock);
        verifyZeroInteractions(passwordEncoderMock);
    }
}
