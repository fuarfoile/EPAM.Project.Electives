package com.boast.controller.util.tag;

import com.boast.domain.*;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlDaoFactory;
import com.boast.model.dao.impl.MySqlStudentCourseDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/** Тег реализующий таблицу курсов сгенерированый на основании поискового запроса*/
public class CoursesTableTag extends TagSupport {

    private static Logger logger = LogManager.getLogger(CoursesTableTag.class);
    private List<Course> courses;

    public void setCourses(List<Course> courses){
        this.courses = courses;
    }

    /**
     * @see TagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {

        //Locale locale = new Locale((String) pageContext.getSession().getAttribute("language"));
        Locale locale = (Locale) pageContext.getSession().getAttribute("language");
        ResourceBundle resource = ResourceBundle.getBundle("localization/translation", locale);

        User user = (User) pageContext.getSession().getAttribute("user");
        MySqlStudentCourseDao dao;
        try {
            Connection connection = MySqlConnectionFactory.getInstance().getConnection();
            dao = MySqlDaoFactory.getInstance().getStudentCourseDao(connection);

            StringBuilder str = new StringBuilder();
            str.append("<table>\n<thead>\n<tr>\n<th>")
                    .append(resource.getString("tag.courses.table.name"))
                    .append("</th>\n<th>")
                    .append(resource.getString("tag.courses.table.teacher"))
                    .append("</th>\n<th>")
                    .append(resource.getString("tag.courses.table.group.size"))
                    .append("</th>\n</tr>\n</thead>\n");

            for (Course course : courses) {
                if (course.getStatus() == CourseStatus.REGISTRATION) {
                    str.append("<tr>\n<td>")
                            .append("<form action =\"controller\" method=POST>\n")
                            .append("<input type=\"hidden\" name=\"courseId\" value=\"")
                            .append(course.getId())
                            .append("\"/>\n")
                            .append("<input type=\"hidden\" name=\"TypeCommand\" value=\"R_COURSE\"/>\n")
                            .append("<input type=\"hidden\" name=\"fromPage\" value=\"COURSES\"/>")
                            .append("<button class=\"button\" type=\"submit\">\n")
                            .append(course.getName())
                            .append("</button>\n")
                            .append("</form>\n")
                            .append("</td>\n<td>");
                    try {
                        if (dao.getByIds(user.getId(), course.getId()) != null) {
                            str.append("<div style=\"color:forestgreen;\">")
                                    .append(course.getTeacherSurname())
                                    .append(" ")
                                    .append(course.getTeacherName())
                                    .append("</div></td>\n<td><div style=\"color:forestgreen;\">")
                                    .append(course.getStudentsCount())
                                    .append(" / ")
                                    .append(course.getMaxStudentsCount())
                                    .append("</div>");
                        } else {
                            str.append(course.getTeacherSurname())
                                    .append(" ")
                                    .append(course.getTeacherName())
                                    .append("</td>\n<td>")
                                    .append(course.getStudentsCount())
                                    .append(" / ")
                                    .append(course.getMaxStudentsCount());
                        }
                    } catch (SQLException e) {
                        logger.error("doStartTag, StudentCourseDao.getByIds fail: " + e);
                        str.append(course.getTeacherSurname())
                                .append(" ")
                                .append(course.getTeacherName())
                                .append("</td>\n<td>")
                                .append(course.getStudentsCount())
                                .append(" / ")
                                .append(course.getMaxStudentsCount());
                    }

                    str.append("</td>\n</tr>\n");
                }
            }

            str.append("</table>");

            JspWriter out = pageContext.getOut();
            out.write(str.toString());
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }

        return SKIP_BODY;
    }
}
