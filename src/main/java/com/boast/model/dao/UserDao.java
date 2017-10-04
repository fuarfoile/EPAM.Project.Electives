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
}
