package com.boast.model.dao.impl;

import com.boast.model.dao.DaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

/** MySql реализация DAO фабрики*/
public class MySqlDaoFactory implements DaoFactory {

    private static Logger logger = LogManager.getLogger(MySqlDaoFactory.class);
    private static MySqlDaoFactory instance;

    public static MySqlDaoFactory getInstance() {
        if (instance == null) {
            instance = new MySqlDaoFactory();
        }
        return instance;
    }

    @Override
    public MySqlCourseDao getCourseDao(Connection connection) {
        return new MySqlCourseDao(connection);
    }

    @Override
    public MySqlUserDao getUserDao(Connection connection) {
        return new MySqlUserDao(connection);
    }

    @Override
    public MySqlStudentCourseDao getStudentCourseDao(Connection connection){
        return new MySqlStudentCourseDao(connection);
    }
}