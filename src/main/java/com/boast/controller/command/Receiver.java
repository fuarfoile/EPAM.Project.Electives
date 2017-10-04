package com.boast.controller.command;

import com.boast.controller.util.constant.Link;
import com.boast.model.dao.CourseDao;
import com.boast.model.dao.DaoFactory;
import com.boast.model.dao.StudentCourseDao;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlDaoFactory;
import com.boast.domain.Course;
import com.boast.domain.Position;
import com.boast.domain.StudentCourse;
import com.boast.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Контейнер общих ресурсов и методов для комманд*/
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

    /**
     * Установка Cookies для автоматического входа
     * @param login Логин пользователя (e-mail)
     * @param password Пароль пользователя
     */
    public void addCookie(String login, String password){
        Cookie logCookie = new Cookie("login", login);
        Cookie passCookie = new Cookie("password", password);
        response.addCookie(logCookie);
        response.addCookie(passCookie);
    }

    /**
     * Перенаправление на страницу списка курсов по поисковому запросу
     * @param comparator Comparator для сортировки списка полученых курсов
     */
    public String rCourseSearch(Comparator<Course> comparator){
        String searchText = request.getParameter("searchText");
        if (searchText == null) {
            searchText = (String) session.getAttribute("searchText");
        } else {
            session.setAttribute("searchText", searchText);
        }

        MySqlDaoFactory daoFactory = MySqlDaoFactory.getInstance();
        Connection connection;
        List<Course> courses;

        try {
            connection = MySqlConnectionFactory.getInstance().getConnection();
            courses = daoFactory.getCourseDao(connection).getAll();
        } catch (SQLException | NullPointerException e) {
            logger.error("courseSearch: " + e);
            return Link.ERROR.getLink();
        }

        if (courses != null) {
            if (searchText.length() > 0) {
                request.setAttribute("searchText", searchText);

                for (int i = 0; i < courses.size(); i++) {
                    if (!courses.get(i).getName().toLowerCase().contains(searchText.toLowerCase())) {
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
                    public int compare(Course c1, Course c2) {
                        return c1.getName().compareTo(c2.getName());
                    }
                });
            }
        }

        request.setAttribute("courses", courses);

        return Link.COURSES.getLink();
    }

    /**
     * Перенаправление на страницу курса
     * @param courseId Идентификатор курса
     */
    public String rCource(int courseId){

        Course course = null;
        logger.debug("courseId = " + courseId);

        try {
            Connection connection = MySqlConnectionFactory.getInstance().getConnection();
            DaoFactory daoFactory = MySqlDaoFactory.getInstance();
            CourseDao courseDao = daoFactory.getCourseDao(connection);

            course = courseDao.getById(courseId);
            logger.debug("course = " + course);

            request.setAttribute("course", course);

            StudentCourseDao studentCourseDao = daoFactory.getStudentCourseDao(connection);
            List<StudentCourse> studentCourses = studentCourseDao.getAllStudents(course.getId());

            studentCourses.sort(new Comparator<StudentCourse>() {
                @Override
                public int compare(StudentCourse sc1, StudentCourse sc2) {
                    return (sc1.getStudentSurname() + sc1.getStudentName())
                            .compareTo(sc2.getStudentSurname() + sc2.getStudentName());
                }
            });

            request.setAttribute("studentCourses", studentCourses);
            request.setAttribute("fromPage", request.getParameter("fromPage"));
            return Link.COURSE.getLink();
        } catch (NullPointerException | SQLException e) {
            logger.error("Redirect to course page fail: " + e);
        }

        return Link.ERROR.getLink();
    }

    /**
     * Перенаправление на страницу профиля
     * @param user Пользователь профиля
     */
    public String rProfile(User user){
        MySqlDaoFactory daoFactory = MySqlDaoFactory.getInstance();
        Connection connection;

        if (user == null) {
            user = (User) session.getAttribute("user");
        }

        if (user.getPosition() != Position.STUDENT) {
            List<Course> courses;

            try {
                connection = MySqlConnectionFactory.getInstance().getConnection();
                if (user.getPosition() == Position.ADMIN){
                    courses = daoFactory.getCourseDao(connection).getAll();
                } else {
                    courses = daoFactory.getCourseDao(connection).getAllByTeacherId(user.getId());
                }

                courses.sort(new Comparator<Course>() {
                    @Override
                    public int compare(Course c1, Course c2) {
                        return c1.getName().compareTo(c2.getName());
                    }
                });

            } catch (SQLException e) {
                logger.error(e);
                return Link.ERROR.getLink();
            }

            request.setAttribute("courses", courses);
            logger.debug("rProfile: " + courses);
            return Link.TEACHER_PROFILE.getLink();
        } else {
            List<StudentCourse> studentCourses;

            try {
                connection = MySqlConnectionFactory.getInstance().getConnection();

                if (user.getPosition() == Position.STUDENT) {
                    studentCourses = daoFactory.getStudentCourseDao(connection).getAllCourses(user.getId());
                } else {
                    studentCourses = daoFactory.getStudentCourseDao(connection).getAll();
                }
            } catch (SQLException e) {
                logger.error(e);
                return Link.ERROR.getLink();
            }

            studentCourses.sort(new Comparator<StudentCourse>() {
                @Override
                public int compare(StudentCourse sc1, StudentCourse sc2) {
                    return sc1.getCourseName().compareTo(sc2.getCourseName());
                }
            });

            request.setAttribute("studentCourses", studentCourses);
            logger.debug("rProfile: " + studentCourses);
            return Link.PROFILE.getLink();
        }
    }

    /**
     * Перенаправление на страницу регистрации
     */
    public String rSignup(){
        ArrayList<Position> list = new ArrayList<>();

        Collections.addAll(list, Position.values());
        list.remove(Position.ADMIN);

        request.setAttribute("positions", list);
        return Link.SIGNUP.getLink();
    }

    /**
     * Перенаправление на страницу обновления данных аккаунта
     */
    public String rAccountUpdate(){
        ArrayList<Position> list = new ArrayList<>();

        Collections.addAll(list, Position.values());
        list.remove(Position.ADMIN);

        request.setAttribute("positions", list);
        return Link.ACCOUNT_UPDATE.getLink();
    }
}
