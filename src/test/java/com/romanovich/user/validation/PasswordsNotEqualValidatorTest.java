package com.romanovich.user.validation;

import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static com.romanovich.user.validation.PasswordsNotEqualAssert.assertThat;

public class PasswordsNotEqualValidatorTest {

    private static final String PASSWORD = "password";
    private static final String PASSWORD_VERIFICATION = "passwordVerification";

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void bothPasswordsAreNullTest() {
        PasswordsNotEqualDTO passesValidation = PasswordsNotEqualDTO.getBuilder().build();

        assertThat(validator.validate(passesValidation)).hasNoValidationErrors();
    }

    @Test
    public void passwordIsNullTest() {
        PasswordsNotEqualDTO failsValidation = PasswordsNotEqualDTO.getBuilder()
                .passwordVerification(PASSWORD_VERIFICATION)
                .build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(2)
                .hasValidationErrorForField("password")
                .hasValidationErrorForField("passwordVerification");
    }

    @Test
    public void passwordVerificationIsNullTest() {
        PasswordsNotEqualDTO failsValidation = PasswordsNotEqualDTO.getBuilder()
                .password(PASSWORD)
                .build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(2)
                .hasValidationErrorForField("password")
                .hasValidationErrorForField("passwordVerification");
    }

    @Test
    public void bothPasswordsAreEmptyTest() {
        PasswordsNotEqualDTO passesValidation = PasswordsNotEqualDTO.getBuilder()
                .password("")
                .passwordVerification("")
                .build();

        assertThat(validator.validate(passesValidation)).hasNoValidationErrors();
    }

    @Test
    public void passwordIsEmptyTest() {
        PasswordsNotEqualDTO failsValidation = PasswordsNotEqualDTO.getBuilder()
                .password("")
                .passwordVerification(PASSWORD_VERIFICATION)
                .build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(2)
                .hasValidationErrorForField("password")
                .hasValidationErrorForField("passwordVerification");
    }

    @Test
    public void passwordVerificationIsEmptyTest() {
        PasswordsNotEqualDTO failsValidation = PasswordsNotEqualDTO.getBuilder()
                .password(PASSWORD)
                .passwordVerification("")
                .build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(2)
                .hasValidationErrorForField("password")
                .hasValidationErrorForField("passwordVerification");
    }

    @PasswordsNotEqual(
            passwordFieldName = "password",
            passwordVerificationFieldName = "passwordVerification"
    )
    private class InvalidPasswordFieldDTO {
        private String passwordVerification;
    }

    @PasswordsNotEqual(
            passwordFieldName = "password",
            passwordVerificationFieldName = "passwordVerification"
    )
    private class InvalidPasswordVerificationFieldDTO {
        private String password;
    }
}
