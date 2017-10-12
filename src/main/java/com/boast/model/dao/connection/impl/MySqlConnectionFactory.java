package com.boast.model.dao.connection.impl;

import com.boast.model.dao.connection.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

/** MySql реализация фабрики соединений с базой данных */
public class MySqlConnectionFactory implements ConnectionFactory {

    private static Logger logger = LogManager.getLogger(MySqlConnectionFactory.class);
    private static MySqlConnectionFactory instance;

    private Connection connection;
    private DataSource dataSource;

    private MySqlConnectionFactory(){
        /*try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Fail in driver registration: " + e);
        }

        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url      = resource.getString("url");
        String user     = resource.getString("user");
        String password = resource.getString("password");

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            logger.error("Fail to get connection: " + e);
        }*/

        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/electives_db");
            logger.debug("dataSource: " + dataSource);
        } catch (NamingException e) {
            logger.debug("Fail to get connection: " + e);
        }
    }

    public static MySqlConnectionFactory getInstance(){
        if (instance == null) {
            instance = new MySqlConnectionFactory();
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        //return connection;
        try {
            if (connection == null || connection.isClosed()) {
                connection = dataSource.getConnection();
                logger.debug("getConnection: " + connection);
            }
            return connection;
        } catch (SQLException e) {
            logger.error("Can't get a connection: " + e);
            return null;
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }catch (SQLException e) {
            logger.error("Can't close a connection: " + e);
        }
    }
}
