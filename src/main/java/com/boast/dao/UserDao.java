package com.boast.dao;

import com.boast.transferobject.User;

import java.sql.SQLException;

public interface UserDao extends GenericDao<User> {
    User getByEmail(String email) throws SQLException;
}
