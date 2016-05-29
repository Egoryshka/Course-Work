package com.romanovich.user.model;

import org.springframework.test.util.ReflectionTestUtils;


public class UserBuilder {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    public UserBuilder() {

    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        User user = User.getBuilder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .build();

        ReflectionTestUtils.setField(user, "id", id);

        return user;
    }
}
