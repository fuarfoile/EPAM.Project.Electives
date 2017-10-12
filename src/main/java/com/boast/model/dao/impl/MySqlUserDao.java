package com.boast.model.dao.impl;

import com.boast.domain.builder.impl.UserBuilder;
import com.boast.model.dao.GenericDao;
import com.boast.model.dao.UserDao;
import com.boast.domain.Position;
import com.boast.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** MySql реализация шаблона DAO для сущьности User*/
public class MySqlUserDao implements UserDao {
    private final Connection connection;

    private static Logger logger = LogManager.getLogger(MySqlUserDao.class);

    public MySqlUserDao(Connection connection) {
        this.connection = connection;
    }

    /** @see GenericDao#create(Object) */
    @Override
    public boolean create(User user)  throws SQLException {
        try {
            String sql = "INSERT INTO electives_db.user (position, name, surname, email, password, phone_number, pass_recovery_key)" +
                    " VALUES (" + (user.getPosition().ordinal() + 1) +
                    ", '" + user.getName() +
                    "', '" + user.getSurname() +
                    "', '" + user.getEmail().toLowerCase().trim().replaceAll("\\.","") +
                    "', '" + user.getPassword() +
                    "', '" + user.getPhoneNumber() +
                    "', '" + user.getPassRecoveryKey() +"');";
            Statement stm = connection.createStatement();
            logger.info("create: " + user);
            boolean succes = stm.executeUpdate(sql) > 0;

            if (succes) {
                user.setId(getByEmail(user.getEmail()).getId());
            }

            return succes;
        } catch (NullPointerException e) {
            logger.error("create: " + e);
            return false;
        }
    }

    /** @see GenericDao#getById(int) */
    @Override
    public User getById(int id) throws SQLException {
        String sql = "SELECT * FROM electives_db.user WHERE id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setInt(1, id);

        return getByHelper(stm);
    }

    /** @see UserDao#getByEmail(String) */
    @Override
    public User getByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM electives_db.user WHERE email = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, email.toLowerCase().trim().replaceAll("\\.",""));

        return getByHelper(stm);
    }

    private User getByHelper(PreparedStatement stm) throws SQLException{
        ResultSet rs = stm.executeQuery();
        rs.next();
        return new UserBuilder()
                .setPosition(Position.values()[rs.getInt("position") - 1])
                .setId(rs.getInt("id"))
                .setName(rs.getString("name"))
                .setSurname(rs.getString("surname"))
                .setEmail(rs.getString("email"))
                .setPassword(null)
                .setPhoneNumber(rs.getString("phone_number"))
                .setPassRecoveryKey(rs.getString("pass_recovery_key")).build();
    }

    /** @see GenericDao#update(Object) */
    @Override
    public boolean update(User user) {
        try {
            String sql = "UPDATE electives_db.user SET position = '" + (user.getPosition().ordinal() + 1) +
                    "', name = '" + user.getName() +
                    "', surname = '" + user.getSurname() +
                    "', email = '" + user.getEmail().toLowerCase().trim().replaceAll("\\.","") +
                    (user.getPassword() != null && user.getPassword().length() > 0 ? ("', password = '" + user.getPassword()) : "") +
                    "', phone_number = ?" +
                    ", pass_recovery_key = ?" +
                    " WHERE id = " + user.getId() + ";";
            PreparedStatement stm = connection.prepareStatement(sql);
            logger.info("update: " + user);

            if (user.getPhoneNumber() != null && user.getPhoneNumber().length() > 0) {
                stm.setString(1, user.getPhoneNumber());
            } else {
                stm.setNull(1, Types.VARCHAR);
            }

            if (user.getPassRecoveryKey() != null && user.getPassRecoveryKey().length() > 0) {
                stm.setString(2, user.getPassRecoveryKey());
            } else {
                stm.setNull(2, Types.VARCHAR);
            }

            return stm.executeUpdate() > 0;
        } catch (SQLException | NullPointerException e) {
            logger.info("update: " + e);
            return false;
        }
    }

    /** @see GenericDao#delete(Object) */
    @Override
    public boolean delete(User user) {
        try {
            String sql = "DELETE FROM electives_db.user" +
                    " WHERE id = " + user.getId() + ";";
            Statement stm = connection.createStatement();

            logger.info("delete: " + user);
            return stm.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            logger.info("delete: " + e);
            return false;
        }
    }

    /** @see GenericDao#getAll() */
    @Override
    public List<User> getAll() throws SQLException {
        String sql = "SELECT * FROM electives_db.user;";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        List<User> list = new ArrayList<>();
        while (rs.next()) {
            list.add(getById(rs.getInt("id")));
        }
        return list;
    }

    /** @see UserDao#getAllTeachers() */
    @Override
    public List<User> getAllTeachers() throws SQLException {
        String sql = "SELECT * FROM electives_db.user" +
                " WHERE position = " + (Position.TEACHER.ordinal() + 1) + ";";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        List<User> list = new ArrayList<>();

        while (rs.next()) {
            list.add(getById(rs.getInt("id")));
        }

        return list;
    }
}