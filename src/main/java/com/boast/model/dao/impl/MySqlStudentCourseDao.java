package com.boast.model.dao.impl;

import com.boast.model.dao.DaoFactory;
import com.boast.model.dao.GenericDao;
import com.boast.model.dao.StudentCourseDao;
import com.boast.domain.StudentCourse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** MySql реализация шаблона DAO для сущьности StudentCourse*/
public class MySqlStudentCourseDao implements StudentCourseDao {
    private final Connection connection;

    private static Logger logger = LogManager.getLogger(MySqlStudentCourseDao.class);

    public MySqlStudentCourseDao(Connection connection) {
        this.connection = connection;
    }

    /** @see GenericDao#create(Object) */
    @Override
    public boolean create(StudentCourse studentCourse)  throws SQLException {
        String sql = "INSERT INTO electives_db.studentcourse (student_id, course_id, mark, review)" +
                " VALUES (" + studentCourse.getStudentId() +
                ", '" + studentCourse.getCourseId() +
                "', '" + studentCourse.getMark() +
                "', '" + studentCourse.getReview() + "');";

        Statement stm = connection.createStatement();

        logger.info("create: " + studentCourse);
        return stm.executeUpdate(sql) > 0;
    }

    /** @see GenericDao#getById(int) */
    @Override
    public StudentCourse getById(int id) throws SQLException {
        String sql = "SELECT * FROM electives_db.studentcourse WHERE id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setInt(1, id);

        ResultSet rs = stm.executeQuery();
        if(!rs.next()) {
            return null;
        }
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setId(id);
        studentCourse.setCourseId(rs.getInt("course_id"));
        studentCourse.setStudentId(rs.getInt("student_id"));
        studentCourse.setMark(rs.getInt("mark"));
        studentCourse.setReview(rs.getString("review"));

        DaoFactory daoFactory = MySqlDaoFactory.getInstance();
        studentCourse.setStudentName(
                daoFactory.getUserDao(connection).getById(studentCourse.getStudentId()).getName());
        studentCourse.setStudentSurname(
                daoFactory.getUserDao(connection).getById(studentCourse.getStudentId()).getSurname());
        studentCourse.setCourseName(
                daoFactory.getCourseDao(connection).getById(studentCourse.getCourseId()).getName());
        studentCourse.setTeacherId(
                daoFactory.getCourseDao(connection).getById(studentCourse.getCourseId()).getTeacherId());
        studentCourse.setStatus(
                daoFactory.getCourseDao(connection).getById(studentCourse.getCourseId()).getStatus());

        return studentCourse;
    }

    /** @see StudentCourseDao#getByIds(int, int)  */
    @Override
    public StudentCourse getByIds(int student_id, int course_id) throws SQLException {
        String sql = "SELECT * FROM electives_db.studentcourse WHERE student_id = ? and course_id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setInt(1, student_id);
        stm.setInt(2, course_id);

        ResultSet rs = stm.executeQuery();
        if(!rs.next()) {
            return null;
        }
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourseId(rs.getInt("course_id"));
        studentCourse.setStudentId(rs.getInt("student_id"));
        studentCourse.setMark(rs.getInt("mark"));
        studentCourse.setReview(rs.getString("review"));

        return studentCourse;
    }

    /** @see GenericDao#update(Object) */
    @Override
    public boolean update(StudentCourse studentCourse) throws SQLException{
        String sql = "UPDATE electives_db.studentcourse SET mark = ?" +
                ", review = ?" +
                " WHERE id = " + studentCourse.getId() + ";";

        PreparedStatement stm = connection.prepareStatement(sql);

        if (studentCourse.getMark() > 0) {
            stm.setInt(1, studentCourse.getMark());
        } else {
            stm.setNull(1, Types.INTEGER);
        }

        if (studentCourse.getReview() != null && studentCourse.getReview().length() > 0) {
            stm.setString(2, studentCourse.getReview());
        } else {
            stm.setNull(2, Types.VARCHAR);
        }

        logger.info("update: " + studentCourse);
        return stm.executeUpdate() > 0;
    }

    /** @see GenericDao#delete(Object) */
    @Override
    public boolean delete(StudentCourse studentCourse) throws SQLException{
        String sql = "DELETE FROM electives_db.studentcourse" +
                " WHERE id = " + studentCourse.getId() + ";";
        Statement stm = connection.createStatement();

        logger.info("delete: " + studentCourse);
        return stm.executeUpdate(sql) > 0;
    }

    /** @see StudentCourseDao#getByIds(int, int)  */
    @Override
    public boolean deleteByIds(int student_id, int course_id) throws SQLException{
        String sql = "DELETE FROM electives_db.studentcourse" +
                " WHERE student_id = " + student_id +
                " and course_id = " + course_id + ";";
        Statement stm = connection.createStatement();

        logger.info("deleteByIds: student_id = " + student_id + ", course_id = " + course_id);
        return stm.executeUpdate(sql) > 0;
    }

    /** @see GenericDao#getAll() */
    @Override
    public List<StudentCourse> getAll() throws SQLException {
        String sql = "SELECT * FROM electives_db.studentcourse;";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        List<StudentCourse> list = new ArrayList<>();

        while (rs.next()) {
            list.add(getById(rs.getInt("id")));
        }

        return list;
    }

    /** @see StudentCourseDao#getAllCourses(int) */
    @Override
    public List<StudentCourse> getAllCourses(int student_id) throws SQLException {
        String sql = "SELECT * FROM electives_db.studentcourse WHERE student_id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, student_id);
        ResultSet rs = stm.executeQuery();
        List<StudentCourse> list = new ArrayList<>();

        while (rs.next()) {
            list.add(getById(rs.getInt("id")));
        }

        return list;
    }

    /** @see StudentCourseDao#getAllStudents(int) */
    @Override
    public List<StudentCourse> getAllStudents(int course_id) throws SQLException {
        String sql = "SELECT * FROM electives_db.studentcourse WHERE course_id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, course_id);
        ResultSet rs = stm.executeQuery();
        List<StudentCourse> list = new ArrayList<>();

        while (rs.next()) {
            list.add(getById(rs.getInt("id")));
        }

        return list;
    }

    /** @see StudentCourseDao#getStudentsCount(int) */
    @Override
    public int getStudentsCount(int course_id) throws SQLException {
        String sql = "SELECT * FROM electives_db.studentcourse WHERE course_id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, course_id);
        ResultSet rs = stm.executeQuery();

        int count = 0;
        while (rs.next()) {
            count++;
        }

        return count;
    }
}