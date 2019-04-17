package com.boast.dao;

import com.boast.transferobject.Position;
import com.boast.transferobject.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDao implements UserDao {
    private final Connection connection;

    private static Logger logger = LogManager.getLogger(MySqlUserDao.class);

    public MySqlUserDao(Connection connection) {
        this.connection = connection;
    }

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
            return stm.executeUpdate(sql) > 0;
        } catch (NullPointerException e) {
            logger.error("create: " + e);
            return false;
        }
    }

    @Override
    public User getById(int id) throws SQLException {
        String sql = "SELECT * FROM electives_db.user WHERE id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setInt(1, id);

        return getByHelper(stm);
    }

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
        User user = new User();
        user.setPosition(Position.values()[rs.getInt("position") - 1]);
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setEmail(rs.getString("email"));
        user.setPassword(null);
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setPassRecoveryKey(rs.getString("pass_recovery_key"));
        return user;
    }

    @Override
    public boolean update(User user) {
        try {
            String sql = "UPDATE electives_db.user SET position = '" + (user.getPosition().ordinal() + 1) +
                    "', name = '" + user.getName() +
                    "', surname = '" + user.getSurname() +
                    "', email = '" + user.getEmail().toLowerCase().trim().replaceAll("\\.","") +
                    (user.getPassword() != null ? ("', password = '" + user.getPassword()) : "") +
                    "', phone_number = '" + user.getPhoneNumber() +
                    "', pass_recovery_key = '" + user.getPassRecoveryKey() +
                    "' WHERE id = " + user.getId() + ";";
            Statement stm = connection.createStatement();
            logger.info("update: " + user);
            return stm.executeUpdate(sql) > 0;
        } catch (SQLException | NullPointerException e) {
            logger.info("update: " + e);
            return false;
        }
    }

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
}