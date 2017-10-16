package com.boast.model.dao;

import com.boast.domain.User;

import java.sql.SQLException;
import java.util.List;

/** Интерфейс для реализации шаблона DAO сущности User */
public interface UserDao extends GenericDao<User> {

    /** Возвращает пользователя с электронной почтой email */
    User getByEmail(String email) throws SQLException;

    /** Возвращает весь список преподавателей */
    List<User> getAllTeachers() throws SQLException;

    /** Возвращает пользователя с указаным логином (email) и паролем или null при отсутствии в базе */
    public User getByLogPass(String login, String password);

    /** Возвращает true при наличии заданого email, false в противном случае */
    public boolean isEmailInBase(String email);
}
