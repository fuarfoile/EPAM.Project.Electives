package com.boast.model.dao;

import com.boast.domain.Course;

import java.sql.SQLException;
import java.util.List;

/** Интерфейс для реализации шаблона DAO сущности Course */
public interface CourseDao extends GenericDao<Course>{

    /** Возвращает список курсов преподавателя с идентефикатором teacherId */
    public List<Course> getAllByTeacherId(int teacherId) throws SQLException;
}