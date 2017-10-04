package com.boast.model.dao;

import com.boast.model.dao.impl.MySqlCourseDao;
import com.boast.model.dao.impl.MySqlLoginDao;
import com.boast.model.dao.impl.MySqlStudentCourseDao;
import com.boast.model.dao.impl.MySqlUserDao;

import java.sql.Connection;

/** Интерфейс для реализация шаблона DAO фабрики*/
public interface DaoFactory {

    /** Возвращает объект для управления состоянием объекта Course */
    public MySqlCourseDao getCourseDao(Connection connection);

    /** Возвращает объект для управления состоянием объекта User */
    public MySqlUserDao getUserDao(Connection connection);

    /** Возвращает объект для реализации системы авторизации */
    public MySqlLoginDao getLoginDao(Connection connection);

    /** Возвращает объект для управления состоянием объекта StudentCourse */
    public MySqlStudentCourseDao getStudentCourseDao(Connection connection);
}