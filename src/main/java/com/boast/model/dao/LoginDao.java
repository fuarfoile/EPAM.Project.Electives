package com.boast.model.dao;

import com.boast.domain.User;

import java.sql.SQLException;

public interface LoginDao {

    /** Возвращает пользователя с указаным логином (email) и паролем или null при отсутствии в базе */
    public User getUser(String login, String password);

    /** Возвращает true при наличии заданого email, false в противном случае */
    public boolean isEmailInBase(String email);
}