package com.boast.controller.util.tag;

import com.boast.domain.Course;
import com.boast.domain.Position;
import com.boast.domain.User;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlDaoFactory;
import com.boast.model.dao.impl.MySqlStudentCourseDao;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/** Тег реализующий динамическую кнопку регистрации/отмены регистрации на курс*/
public class ApplyButtonTag extends TagSupport {

    private Course course;

    public void setCourse(Course course){
        this.course = course;
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

            if (user.getPosition() == Position.STUDENT) {
                if (dao.getByIds(user.getId(), course.getId()) != null) {
                    str.append("<input type=\"hidden\" name=\"TypeCommand\" value=\"CANCEL_COURSE\"/>\n")
                            .append("<input type=\"hidden\" name=\"fromPage\" value=\"")
                            .append(pageContext.getRequest().getParameter("fromPage"))
                            .append("\"/>\n")
                            .append("<button class=\"button\" type=\"submit\">")
                            .append(resource.getString("tag.button.registration.cancel"))
                            .append("</button>");
                } else if (course.getStudentsCount() >= course.getMaxStudentsCount()) {
                    str.append("<input type=\"hidden\" name=\"TypeCommand\" value=\"APPLY_COURSE\"/>\n")
                            .append("<input type=\"hidden\" name=\"fromPage\" value=\"")
                            .append(pageContext.getRequest().getParameter("fromPage"))
                            .append("\"/>\n")
                            .append("<button class=\"button-disabled\" type=\"submit\" disabled>")
                            .append(resource.getString("tag.button.registration.full"))
                            .append("</button>");
                } else {
                    str.append("<input type=\"hidden\" name=\"TypeCommand\" value=\"APPLY_COURSE\"/>\n")
                            .append("<input type=\"hidden\" name=\"fromPage\" value=\"")
                            .append(pageContext.getRequest().getParameter("fromPage"))
                            .append("\"/>\n")
                            .append("<button class=\"button\" type=\"submit\">")
                            .append(resource.getString("tag.button.registration.apply"))
                            .append("</button>");
                }
            }

            JspWriter out = pageContext.getOut();
            out.write(str.toString());
        } catch (SQLException | IOException e) {
            throw new JspException(e.getMessage());
        }

        return SKIP_BODY;
    }
}
