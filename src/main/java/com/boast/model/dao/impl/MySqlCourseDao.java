package com.boast.model.dao.impl;

import com.boast.domain.CourseStatus;
import com.boast.domain.builder.impl.CourseBuilder;
import com.boast.model.dao.CourseDao;
import com.boast.model.dao.DaoFactory;
import com.boast.domain.Course;
import com.boast.domain.User;
import com.boast.model.dao.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** MySql реализация шаблона DAO для сущьности Course*/
public class MySqlCourseDao implements CourseDao {
    private final Connection connection;

    private static Logger logger = LogManager.getLogger(MySqlCourseDao.class);

    public MySqlCourseDao(Connection connection) {
        this.connection = connection;
    }

    /** @see GenericDao#create(Object) */
    @Override
    public boolean create(Course course)  throws SQLException {
        String sql = "INSERT INTO electives_db.course (name, teacher_id, max_students_count, description, course_status)" +
                " VALUES ('" + course.getName() +
                "', ?" +
                ", '" + course.getMaxStudentsCount() +
                "', ?" +
                ", " + (course.getStatus().ordinal() + 1) + ");";

        PreparedStatement stm = connection.prepareStatement(sql);

        if (course.getTeacherId() > 0) {
            stm.setInt(1, course.getTeacherId());
        } else {
            stm.setNull(1, Types.INTEGER);
        }

        if (course.getDescription() != null && course.getDescription().length() > 0) {
            stm.setString(2, course.getDescription());
        } else {
            stm.setNull(2, Types.VARCHAR);
        }

        logger.info("create: " + course);
        return stm.executeUpdate() > 0;
    }

    /** @see GenericDao#getById(int id) */
    @Override
    public Course getById(int id) throws SQLException {
        String sql = "SELECT * FROM electives_db.Course WHERE id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setInt(1, id);

        ResultSet rs = stm.executeQuery();
        if(!rs.next()) {
            return null;
        }
        Course course = new CourseBuilder()
                .setId(rs.getInt("id"))
                .setName(rs.getString("name"))
                .setDescription(rs.getString("description"))
                .setTeacherId(rs.getInt("teacher_id"))
                .setStatus(CourseStatus.values()[rs.getInt("course_status") - 1])
                .setMaxStudentsCount(rs.getInt("max_students_count")).build();

        DaoFactory daoFactory = MySqlDaoFactory.getInstance();
        course.setStudentsCount(
                daoFactory.getStudentCourseDao(connection).getStudentsCount(course.getId()));

        if (course.getTeacherId() > 0) {
            User teacher = daoFactory.getUserDao(connection).getById(course.getTeacherId());
            course.setTeacherName(teacher.getName());
            course.setTeacherSurname(teacher.getSurname());
        }

        return course;
    }

    /** @see GenericDao#update(Object) */
    @Override
    public boolean update(Course course) throws SQLException{
        String sql = "UPDATE electives_db.course SET name = '" + course.getName() +
                "', description = '" + course.getDescription() +
                "', teacher_id = ?" +
                ", max_students_count = '" + course.getMaxStudentsCount() +
                "', course_status = '" + (course.getStatus().ordinal() + 1) +
                "' WHERE id = " + course.getId() + ";";

        PreparedStatement stm = connection.prepareStatement(sql);

        if (course.getTeacherId() > 0) {
            stm.setInt(1, course.getTeacherId());
        } else {
            stm.setNull(1, Types.INTEGER);
        }

        logger.info("update: " + course);
        return stm.executeUpdate() > 0;
    }

    /** @see GenericDao#delete(Object) */
    @Override
    public boolean delete(Course course) throws SQLException{
        String sql = "DELETE FROM electives_db.course" +
                " WHERE id = " + course.getId() + ";";
        Statement stm = connection.createStatement();

        logger.info("delete: " + course);
        return stm.executeUpdate(sql) > 0;
    }

    /** @see GenericDao#getAll() */
    @Override
    public List<Course> getAll() throws SQLException {
        String sql = "SELECT * FROM electives_db.Course;";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        List<Course> list = new ArrayList<>();

        while (rs.next()) {
            list.add(getById(rs.getInt("id")));
        }

        return list;
    }

    /** @see CourseDao#getAllByTeacherId(int) */
    @Override
    public List<Course> getAllByTeacherId(int teacherId) throws SQLException {
        String sql = "SELECT * FROM electives_db.Course WHERE teacher_id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setInt(1, teacherId);

        ResultSet rs = stm.executeQuery();
        List<Course> list = new ArrayList<>();

        while (rs.next()) {
            list.add(getById(rs.getInt("id")));
        }

        return list;
    }
}