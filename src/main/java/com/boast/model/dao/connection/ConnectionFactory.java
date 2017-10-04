package com.boast.model.dao.connection;

import java.sql.Connection;

/** Интерфейс для реализации шаблона фабрики для соединения с базой данных*/
public interface ConnectionFactory {

    /** Возвращает подключение к базе данных */
    public Connection getConnection();
}
