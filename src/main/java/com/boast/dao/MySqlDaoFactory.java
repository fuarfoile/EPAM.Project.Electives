package com.boast.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MySqlDaoFactory implements DaoFactory {

    private String driver = "com.mysql.jdbc.Driver";//Имя драйвера

    public MySqlDaoFactory() {
        try {
            Class.forName(driver);//Регистрируем драйвер
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url      = resource.getString("url");        //URL адрес
        String user     = resource.getString("user");       //Логин пользователя
        String password = resource.getString("password");   //Пароль пользователя

        return DriverManager.getConnection(url, user, password);
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
    public MySqlLoginDao getMySqlLoginDao(Connection connection){
        return new MySqlLoginDao(connection);
    }

    @Override
    public MySqlStudentCourseDao getMySqlStudentCourseDao(Connection connection){
        return new MySqlStudentCourseDao(connection);
    }
}