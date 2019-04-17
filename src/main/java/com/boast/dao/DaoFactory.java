package com.boast.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface DaoFactory {

    /** Возвращает подключение к базе данных */
    public Connection getConnection() throws SQLException;

    /** Возвращает объект для управления персистентным состоянием объекта Course */
    public MySqlCourseDao getCourseDao(Connection connection);

    /** Возвращает объект для управления персистентным состоянием объекта User */
    public MySqlUserDao getUserDao(Connection connection);

    public MySqlLoginDao getMySqlLoginDao(Connection connection);

    public MySqlStudentCourseDao getMySqlStudentCourseDao(Connection connection);
}