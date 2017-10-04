package com.boast.model.dao;

import com.boast.domain.StudentCourse;

import java.sql.SQLException;
import java.util.List;

/** Интерфейс для реализации шаблона DAO сущности StudentCourse */
public interface StudentCourseDao extends GenericDao<StudentCourse>{

    /** Возвращает сущьность StudentCourse с задаными student_id и course_id */
    public StudentCourse getByIds(int student_id, int course_id) throws SQLException;

    /** Возвращает список из StudentCourse для студента с заданым student_id */
    public List<StudentCourse> getAllCourses(int student_id) throws SQLException;

    /** Возвращает список из StudentCourse для курса с заданым course_id */
    public List<StudentCourse> getAllStudents(int course_id) throws SQLException;

    /** Возвращает количество студентов на курсе с идентификатором course_id */
    public int getStudentsCount(int course_id) throws SQLException;

    /** Удаляет из базы сущьность StudentCourse с задаными student_id и course_id
     * и возвращает false при неудачи и true при утачном удалении */
    public boolean deleteByIds(int student_id, int course_id) throws SQLException;
}