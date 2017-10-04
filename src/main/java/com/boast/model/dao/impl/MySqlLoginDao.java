package com.boast.model.dao.impl;

import com.boast.domain.User;
import com.boast.model.dao.LoginDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** MySql реализация шаблона DAO для системы авторизации*/
public class MySqlLoginDao implements LoginDao{
    private final Connection connection;

    private static Logger logger = LogManager.getLogger(MySqlLoginDao.class);

    public MySqlLoginDao(Connection connection) {
        this.connection = connection;
    }

    /** @see LoginDao#getUser(String, String)  */
    @Override
    public User getUser(String login, String password) {
        User user = null;

        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM electives_db.user WHERE email=? and password=?");

            ps.setString(1, login.toLowerCase().trim().replaceAll("\\.", ""));
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = MySqlDaoFactory.getInstance().getUserDao(connection).getById(rs.getInt("id"));
            }
        } catch (SQLException e){
            logger.error("getUser: SELECT from electives_db.user fail: " + e);
        }

        logger.debug("login = " + login + ", validation succes = " + (user != null));
        return user;
    }

    /** @see LoginDao#isEmailInBase(String)  */
    @Override
    public boolean isEmailInBase(String email) {

        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM electives_db.user WHERE email=?");

            ps.setString(1, email.toLowerCase().trim().replaceAll("\\.",""));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e){
            logger.error("isEmailInBase: SELECT from electives_db.user fail: " + e);
        }

        return false;
    }
}
