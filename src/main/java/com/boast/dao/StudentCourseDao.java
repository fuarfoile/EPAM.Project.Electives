package com.boast.dao;

import com.boast.transferobject.StudentCourse;

import java.sql.SQLException;
import java.util.List;

public interface StudentCourseDao extends GenericDao<StudentCourse> {
    public StudentCourse getByIds(int student_id, int course_id) throws SQLException;
    public List<StudentCourse> getAllCourses(int student_id) throws SQLException;
    public List<StudentCourse> getAllStudents(int course_id) throws SQLException;
    public int getStudentsCount(int course_id) throws SQLException;
    public boolean deleteByIds(int student_id, int course_id) throws SQLException;
}