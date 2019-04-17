package com.boast.dao;

import com.boast.transferobject.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlLoginDao {
    private final Connection connection;

    private static Logger logger = LogManager.getLogger(MySqlLoginDao.class);

    public MySqlLoginDao(Connection connection) {
        this.connection = connection;
    }

    public User getUser(String login, String password){
        User user = null;
        try{
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM electives_db.user WHERE email=? and password=?");

            ps.setString(1, login.toLowerCase().trim().replaceAll("\\.",""));
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new MySqlDaoFactory().getUserDao(connection).getById(rs.getInt("id"));
            }

        }catch(SQLException e){
            logger.error(e);
        }

        logger.debug("login = " + login + ", validation succes = " + (user != null));
        return user;
    }

    public boolean isEmailInBase(String email){
        try{
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM electives_db.user WHERE email=?");

            ps.setString(1, email.toLowerCase().trim().replaceAll("\\.",""));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        }catch(SQLException e){
            logger.error(e);
        }
        return false;
    }
}
