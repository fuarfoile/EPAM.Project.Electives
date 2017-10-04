package com.boast.controller.util.tag;

import com.boast.domain.User;
import com.boast.model.dao.DaoFactory;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlDaoFactory;
import com.boast.model.dao.impl.MySqlUserDao;
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

/** Тег реализующий выпадающий список учителей*/
public class TeacherSelectTag extends TagSupport {

    private static Logger logger = LogManager.getLogger(TeacherSelectTag.class);

    private int teacherId;
    private String onchange;

    public void setTeacherId(int teacherId){
        this.teacherId = teacherId;
    }
    public void setOnchange(String onchange){
        this.onchange = onchange;
    }

    /**
     * @see TagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {

        Locale locale = new Locale((String) pageContext.getSession().getAttribute("language"));
        ResourceBundle resource = ResourceBundle.getBundle("localization/translation", locale);

        List<User> teachers = null;
        try {
            DaoFactory daoFactory = MySqlDaoFactory.getInstance();
            Connection connection = MySqlConnectionFactory.getInstance().getConnection();
            teachers = daoFactory.getUserDao(connection).getAllTeachers();

        } catch (SQLException e) {
            logger.error("Can't get teachers list: " + e);
        }

        StringBuilder str = new StringBuilder("<select id=\"teacherId\" name=\"teacherId\"");

        if (onchange != null) {
            str.append(" onchange=\"submit()\"");
        }
        str.append(">\n");

        if (teacherId > 0) {
            str.append("<option value=\"0\">-</option>\n");
        } else {
            str.append("<option value=\"0\" selected>-</option>\n");
        }

        if (teachers != null) {
            for (User teacher : teachers) {
                str.append("<option value=\"")
                        .append(teacher.getId())
                        .append("\" ")
                        .append(teacherId == teacher.getId() ? "selected" : "")
                        .append(">")
                        .append(teacher.getSurname()).append(" ").append(teacher.getName())
                        .append("</option>\n");
            }
        }

        str.append("</select>");

        try {
            JspWriter out = pageContext.getOut();
            out.write(str.toString());
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }

        return SKIP_BODY;
    }
}
