package com.boast.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Унифицированный интерфейс управления персистентным состоянием объектов
 * @param <T> тип объекта персистенции
 */
public interface GenericDao<T> {

    /** Создает новую запись, соответствующую объекту object */
    public boolean create(T object)  throws SQLException;

    /** Возвращает объект соответствующий записи по заданному id или null */
    public T getById(int id) throws SQLException;

    /** Сохраняет состояние объекта в базе данных */
    public boolean update(T object) throws SQLException;

    /** Удаляет запись об объекте из базы данных */
    public boolean delete(T object) throws SQLException;

    /** Возвращает список объектов соответствующих всем записям в базе данных */
    public List<T> getAll() throws SQLException;
}