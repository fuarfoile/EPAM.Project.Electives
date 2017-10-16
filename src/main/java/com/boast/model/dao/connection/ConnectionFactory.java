package com.boast.model.dao.connection;

import java.sql.Connection;

/** Интерфейс для реализации соединения с базой данных*/
public interface ConnectionFactory {

    /** Возвращает подключение к базе данных */
    public Connection getConnection();

    /** Освобождает занятые соединения*/
    public void closeConnection();
}
