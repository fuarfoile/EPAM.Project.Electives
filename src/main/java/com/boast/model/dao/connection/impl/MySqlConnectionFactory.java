package com.boast.model.dao.connection.impl;

import com.boast.model.dao.connection.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** MySql реализация фабрики соединений с базой данных */
public class MySqlConnectionFactory implements ConnectionFactory {

    private static Logger logger = LogManager.getLogger(MySqlConnectionFactory.class);
    private static MySqlConnectionFactory instance;

    private Connection connection;

    private MySqlConnectionFactory(){
        try {
            Class.forName("com.mysql.jdbc.Driver");//Регистрируем драйвер
        } catch (ClassNotFoundException e) {
            logger.error("Fail in driver registration: " + e);
        }

        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url      = resource.getString("url");        //URL адрес
        String user     = resource.getString("user");       //Логин пользователя
        String password = resource.getString("password");   //Пароль пользователя

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            logger.error("Fail to get connection: " + e);
        }

        /*try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/electives_db");
            logger.debug("getConnection: " + ds);
            logger.debug("getConnection: " + ds.getConnection());
            return ds.getConnection();
        } catch (NamingException e) {
            logger.debug("Fail to get connection: " + e);
            return null;
        }*/
    }

    public static MySqlConnectionFactory getInstance(){
        if (instance == null) {
            instance = new MySqlConnectionFactory();
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
