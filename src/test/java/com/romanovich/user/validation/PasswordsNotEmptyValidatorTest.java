package com.romanovich.user.validation;

import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static com.romanovich.user.validation.PasswordsNotEmptyAssert.assertThat;


public class PasswordsNotEmptyValidatorTest {

    private static final String PASSWORD = "password";
    private static final String PASSWORD_VERIFICATION = "passwordVerification";
    private static final String TRIGGER = "trigger";

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void passwordsHaveNotValidationTest() {
        PasswordsNotEmptyDTO notValidated = PasswordsNotEmptyDTO.getBuilder()
                .trigger(TRIGGER)
                .build();

        assertThat(validator.validate(notValidated)).hasNoValidationErrors();
    }

    @Test
    public void emptyBothPasswordsFieldsTest() {
        PasswordsNotEmptyDTO failsValidation = PasswordsNotEmptyDTO.getBuilder()
                .passwordVerification(PASSWORD_VERIFICATION)
                .build();

            assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(1)
                .hasValidationErrorForField("password");
    }

    @Test
    public void emptyPasswordFieldTest() {
        PasswordsNotEmptyDTO failsValidation = PasswordsNotEmptyDTO.getBuilder()
                .password("")
                .passwordVerification(PASSWORD_VERIFICATION)
                .build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(1)
                .hasValidationErrorForField("password");
    }

    @Test
    public void emptyPasswordVerificationFieldTest() {
        PasswordsNotEmptyDTO failsValidation = PasswordsNotEmptyDTO.getBuilder()
                .password(PASSWORD)
                .passwordVerification("")
                .build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(1)
                .hasValidationErrorForField("passwordVerification");
    }

    @Test
    public void everythingEmptyTest() {
        PasswordsNotEmptyDTO failsValidation = PasswordsNotEmptyDTO.getBuilder().build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(2)
                .hasValidationErrorForField("password")
                .hasValidationErrorForField("passwordVerification");
    }

    @Test(expected = ValidationException.class)
    public void invalidTriggerTest() {
        InvalidTriggerFieldDTO invalid = new InvalidTriggerFieldDTO();

        validator.validate(invalid);
    }


    @PasswordsNotEmpty(
            triggerFieldName = "trigger",
            passwordFieldName = "password",
            passwordVerificationFieldName = "passwordVerification"
    )
    private class InvalidTriggerFieldDTO {
        private String password;
        private String passwordVerification;
    }

    @PasswordsNotEmpty(
            triggerFieldName = "trigger",
            passwordFieldName = "password",
            passwordVerificationFieldName = "passwordVerification"
    )
    private class InvalidPasswordFieldDTO {

        private String trigger;
        private String passwordVerification;
    }

    @PasswordsNotEmpty(
            triggerFieldName = "trigger",
            passwordFieldName = "password",
            passwordVerificationFieldName = "passwordVerification"
    )
    private class InvalidPasswordVerificationFieldDTO {

        private String trigger;
        private String password;
    }
}
