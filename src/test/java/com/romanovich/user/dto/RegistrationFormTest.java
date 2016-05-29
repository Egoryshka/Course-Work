package com.romanovich.user.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationFormTest {
    @Test
    public void RegistrationTest() {
        RegistrationForm dto = new RegistrationFormBuilder().build();

        boolean isNormalRegistration = dto.isNormalRegistration();

        assertThat(isNormalRegistration).isTrue();
    }
}
