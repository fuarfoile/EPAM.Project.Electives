package com.boast.domain.builder.impl;

import com.boast.domain.Position;
import com.boast.domain.User;
import com.boast.domain.builder.Builder;

public class UserBuilder implements Builder<User> {

    private User user;

    public UserBuilder() {
        user = new User();
    }

    public UserBuilder setId(int id){
        user.setId(id);
        return this;
    }

    public UserBuilder setPosition(Position position) {
        user.setPosition(position);
        return this;
    }

    public UserBuilder setName(String name) {
        user.setName(name);
        return this;
    }

    public UserBuilder setSurname(String surname) {
        user.setSurname(surname);
        return this;
    }

    public UserBuilder setEmail(String email) {
        user.setEmail(email);
        return this;
    }

    public UserBuilder setPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder setPhoneNumber(String phoneNumber) {
        user.setPhoneNumber(phoneNumber);
        return this;
    }

    public UserBuilder setPassRecoveryKey(String passRecoveryKey) {
        user.setPassRecoveryKey(passRecoveryKey);
        return this;
    }

    @Override
    public User build() {
        return user;
    }
}
