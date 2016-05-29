package com.romanovich.user.controller;

import com.romanovich.TestUtil;
import com.romanovich.WebTestConstants;
import com.romanovich.config.UnitTestContext;
import com.romanovich.config.WebAppContext;
import com.romanovich.security.util.SecurityContextAssert;
import com.romanovich.user.dto.RegistrationForm;
import com.romanovich.user.dto.RegistrationFormAssert;
import com.romanovich.user.model.User;
import com.romanovich.user.model.UserBuilder;
import com.romanovich.user.service.DuplicateEmailException;
import com.romanovich.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAppContext.class, UnitTestContext.class})
@WebAppConfiguration
public class RegistrationControllerTest {

    private static final String EMAIL = "test@movieshop.by";
    private static final String MALFORMED_EMAIL = "test.movieshop.by";
    private static final String FIRST_NAME = "Admin";
    private static final String LAST_NAME = "Admin";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_VERIFICATION = "passwordVerification";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private UserService userServiceMock;

    @Before
    public void setUp() {
        Mockito.reset(userServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                .build();

        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    public void showRegistrationFormTest() throws Exception {
        mockMvc.perform(get("/home/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/registrationForm"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/home/registrationForm.jsp"))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString())
                )));

        verifyZeroInteractions(userServiceMock);
    }


    @Test
    public void registerUserAccountWithEmptyFormTest() throws Exception {
        mockMvc.perform(post("/home/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("home/registrationForm"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/home/registrationForm.jsp"))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString())
                )))
                .andExpect(model().attributeHasFieldErrors(
                        WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL,
                        WebTestConstants.FORM_FIELD_FIRST_NAME,
                        WebTestConstants.FORM_FIELD_LAST_NAME,
                        WebTestConstants.FORM_FIELD_PASSWORD,
                        WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION
                ));

        SecurityContextAssert.assertThat(SecurityContextHolder.getContext()).userIsAnonymous();
        verifyZeroInteractions(userServiceMock);
    }

    @Test
    public void registerUserAccountWithTooLongValuesTest() throws Exception {
        String email = TestUtil.createStringWithLength(101);
        String firstName = TestUtil.createStringWithLength(101);
        String lastName = TestUtil.createStringWithLength(101);

        mockMvc.perform(post("/home/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, email)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, firstName)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, lastName)
                .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                .param(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, PASSWORD)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("home/registrationForm"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/home/registrationForm.jsp"))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(email)),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(firstName)),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(lastName)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, is(PASSWORD)),
                        hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString())
                )))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, 
                        WebTestConstants.FORM_FIELD_EMAIL, 
                        WebTestConstants.FORM_FIELD_FIRST_NAME, 
                        WebTestConstants.FORM_FIELD_LAST_NAME
                ));

        SecurityContextAssert.assertThat(SecurityContextHolder.getContext()).userIsAnonymous();
        verifyZeroInteractions(userServiceMock);
    }

    @Test
    public void registerUserAccountWithPasswordMismatchTest() throws Exception {
        mockMvc.perform(post("/home/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, EMAIL)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                .param(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, PASSWORD_VERIFICATION)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("home/registrationForm"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/home/registrationForm.jsp"))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(EMAIL)),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(FIRST_NAME)),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(LAST_NAME)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, is(PASSWORD_VERIFICATION)),
                        hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString())
                )))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_PASSWORD,
                        WebTestConstants.FORM_FIELD_PASSWORD
                ));

        SecurityContextAssert.assertThat(SecurityContextHolder.getContext()).userIsAnonymous();
        verifyZeroInteractions(userServiceMock);
    }

    @Test
    public void registerUserAccountWithEmailExistsTest() throws Exception {
        when(userServiceMock.registerNewUserAccount(isA(RegistrationForm.class))).thenThrow(new DuplicateEmailException(""));

        mockMvc.perform(post("/home/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, EMAIL)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                .param(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, PASSWORD)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("home/registrationForm"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/home/registrationForm.jsp"))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(EMAIL)),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(FIRST_NAME)),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(LAST_NAME)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                        hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString())
                )))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, WebTestConstants.FORM_FIELD_EMAIL));

        SecurityContextAssert.assertThat(SecurityContextHolder.getContext()).userIsAnonymous();

        ArgumentCaptor<RegistrationForm> registrationFormArgument = ArgumentCaptor.forClass(RegistrationForm.class);
        verify(userServiceMock, times(1)).registerNewUserAccount(registrationFormArgument.capture());
        verifyNoMoreInteractions(userServiceMock);

        RegistrationForm formObject = registrationFormArgument.getValue();
        RegistrationFormAssert.assertThatRegistrationForm(formObject)
                .isNormalRegistration()
                .hasEmail(EMAIL)
                .hasFirstName(FIRST_NAME)
                .hasLastName(LAST_NAME)
                .hasPassword(PASSWORD)
                .hasPasswordVerification(PASSWORD);
    }

    @Test
    public void registerUserAccountWithMalformedEmail() throws Exception {
        mockMvc.perform(post("/home/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, MALFORMED_EMAIL)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                .param(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, PASSWORD)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("home/registrationForm"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/home/registrationForm.jsp"))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(MALFORMED_EMAIL)),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(FIRST_NAME)),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(LAST_NAME)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                        hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString())
                )))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, WebTestConstants.FORM_FIELD_EMAIL));

        SecurityContextAssert.assertThat(SecurityContextHolder.getContext()).userIsAnonymous();

        verifyZeroInteractions(userServiceMock);
    }

    @Test
    public void registerUserAccountNormalRegistrationTest() throws Exception {
        User registered = new UserBuilder()
                .id(1L)
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .build();

        when(userServiceMock.registerNewUserAccount(isA(RegistrationForm.class))).thenReturn(registered);

        mockMvc.perform(post("/home/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, EMAIL)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                .param(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, PASSWORD)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
        )
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/home"));

        SecurityContextAssert.assertThat(SecurityContextHolder.getContext())
                .loggedInUserIs(registered)
                .loggedInUserHasPassword(registered.getPassword())
                .loggedInUserIsRegisteredByUsingNormalRegistration();

        ArgumentCaptor<RegistrationForm> registrationFormArgument = ArgumentCaptor.forClass(RegistrationForm.class);
        verify(userServiceMock, times(1)).registerNewUserAccount(registrationFormArgument.capture());
        verifyNoMoreInteractions(userServiceMock);

        RegistrationForm formObject = registrationFormArgument.getValue();
        RegistrationFormAssert.assertThatRegistrationForm(formObject)
                .isNormalRegistration()
                .hasEmail(EMAIL)
                .hasFirstName(FIRST_NAME)
                .hasLastName(LAST_NAME)
                .hasPassword(PASSWORD)
                .hasPasswordVerification(PASSWORD);
    }
}
