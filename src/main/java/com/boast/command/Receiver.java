package com.boast.command;

import com.boast.dao.MySqlDaoFactory;
import com.boast.transferobject.Course;
import com.boast.transferobject.Position;
import com.boast.transferobject.StudentCourse;
import com.boast.transferobject.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Receiver {
    private static Logger logger = LogManager.getLogger(Receiver.class);

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    public Receiver(HttpServletRequest request,
                    HttpServletResponse response) {
        this.request = request;
        this.response = response;
        session = request.getSession(true);
    }

    public HttpServletRequest getRequest(){ return request;}
    public HttpServletResponse getResponse(){ return response;}
    public HttpSession getSession(){ return session;}

    public void addCookie(String login, String password){
        Cookie logCookie = new Cookie("login", login);
        Cookie passCookie = new Cookie("password", password);
        response.addCookie(logCookie);
        response.addCookie(passCookie);
    }

    public String rCourseSearch(Comparator<Course> comparator){
        String searchText = request.getParameter("searchText");
        if (searchText == null) {
            searchText = (String) session.getAttribute("searchText");
        } else {
            session.setAttribute("searchText", searchText);
        }

        MySqlDaoFactory daoFactory = new MySqlDaoFactory();
        Connection connection;
        List<Course> courses;

        try {
            connection = daoFactory.getConnection();
            courses = daoFactory.getCourseDao(connection).getAll();
        } catch (SQLException | NullPointerException e) {
            logger.error("courseSearch: " + e);
            return "/jsp/error.jsp";
        }

        if (courses != null) {
            if (searchText.length() > 0) {
                request.setAttribute("searchText", searchText);
                Pattern pattern = Pattern.compile(searchText.toLowerCase());

                for (int i = 0; i < courses.size(); i++) {
                    Matcher matcher = pattern.matcher(courses.get(i).getName().toLowerCase());

                    if (!matcher.find()) {
                        courses.remove(i);
                        i--;
                    }
                }
            }

            if (comparator != null) {
                courses.sort(comparator);
            } else {
                courses.sort(new Comparator<Course>() {
                    @Override
                    public int compare(Course course, Course t1) {
                        return course.getName().compareTo(t1.getName());
                    }
                });
            }
        }

        request.setAttribute("courses", courses);

        return "/jsp/courses.jsp";
    }

    public String rProfile(User user){
        MySqlDaoFactory daoFactory = new MySqlDaoFactory();
        Connection connection;
        List<StudentCourse> studentCourses;

        try {
            connection = daoFactory.getConnection();
            if (user == null) {
                user = (User) session.getAttribute("user");
            }
            studentCourses = daoFactory.getMySqlStudentCourseDao(connection).getAllCourses(user.getId());
        } catch (SQLException e) {
            logger.error(e);
            return "/jsp/error.jsp";
        }

        studentCourses.sort(new Comparator<StudentCourse>() {
            @Override
            public int compare(StudentCourse studentCourse, StudentCourse studentCourse2) {
                return studentCourse.getCourseName().compareTo(studentCourse2.getCourseName());
            }
        });


        request.setAttribute("studentCourses", studentCourses);
        logger.debug("rProfile: " + studentCourses);
        return "/jsp/profile.jsp";
    }

    public String rSignup(){
        ArrayList<Position> list = new ArrayList<>();

        Collections.addAll(list, Position.values());
        list.remove(Position.ADMIN);

        request.setAttribute("positions", list);
        return "/jsp/signup.jsp";
    }

    public String rAccountUpdate(){
        ArrayList<Position> list = new ArrayList<>();

        Collections.addAll(list, Position.values());
        list.remove(Position.ADMIN);

        request.setAttribute("positions", list);
        return "/jsp/accountupdate.jsp";
    }
}
