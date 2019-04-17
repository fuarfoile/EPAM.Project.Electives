package com.boast.dao;

import com.boast.transferobject.Course;
import com.boast.transferobject.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlCourseDao implements CourseDao {
    private final Connection connection;

    private static Logger logger = LogManager.getLogger(MySqlCourseDao.class);

    public MySqlCourseDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Course course)  throws SQLException {
        String sql = "INSERT INTO electives_db.course (id, name, teacher_id, max_students_count, deployed)" +
                " VALUES (" + course.getId() +
                ", '" + course.getName() +
                "', '" + course.getTeacherId() +
                "', '" + course.getMaxStudentsCount() +
                "', " + (course.isDeployed()?1:0) + ");";
        Statement stm = connection.createStatement();

        logger.info("create: " + course);
        return stm.executeUpdate(sql) > 0;
    }

    @Override
    public Course getById(int id) throws SQLException {
        String sql = "SELECT * FROM electives_db.Course WHERE id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setInt(1, id);

        ResultSet rs = stm.executeQuery();
        if(!rs.next()) {
            return null;
        }
        Course course = new Course();
        course.setId(rs.getInt("id"));
        course.setName(rs.getString("name"));
        course.setTeacherId(rs.getInt("teacher_id"));
        course.setDeployed(rs.getBoolean("deployed"));
        course.setMaxStudentsCount(rs.getInt("max_students_count"));

        DaoFactory daoFactory = new MySqlDaoFactory();
        course.setStudentsCount(
                daoFactory.getMySqlStudentCourseDao(connection).getStudentsCount(course.getId()));

        if (course.getTeacherId() > 0) {
            User teacher = daoFactory.getUserDao(connection).getById(course.getTeacherId());
            course.setTeacherName(teacher.getName());
            course.setTeacherSurname(teacher.getSurname());
        }

        return course;
    }

    @Override
    public boolean update(Course course) throws SQLException{
        String sql = "UPDATE electives_db.course SET name = '" + course.getName() +
                "', teacher_id = '" + course.getTeacherId() +
                "', max_students_count = '" + course.getMaxStudentsCount() +
                "', deployed = '" + (course.isDeployed()?1:0) +
                "' WHERE id = " + course.getId() + ";";
        Statement stm = connection.createStatement();

        logger.info("update: " + course);
        return stm.executeUpdate(sql) > 0;
    }

    @Override
    public boolean delete(Course course) throws SQLException{
        String sql = "DELETE FROM electives_db.course" +
                " WHERE id = " + course.getId() + ";";
        Statement stm = connection.createStatement();

        logger.info("delete: " + course);
        return stm.executeUpdate(sql) > 0;
    }

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
}