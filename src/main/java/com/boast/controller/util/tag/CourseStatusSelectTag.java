package com.boast.controller.util.tag;

import com.boast.domain.CourseStatus;
import com.boast.domain.User;
import com.boast.model.dao.DaoFactory;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlDaoFactory;
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

/** Тег реализующий выпадающий список для статуса курса*/
public class CourseStatusSelectTag extends TagSupport {

    private static Logger logger = LogManager.getLogger(CourseStatusSelectTag.class);

    private String status;
    private String onchange;

    public void setStatus(String status){
        this.status = status;
    }
    public void setOnchange(String onchange){
        this.onchange = onchange;
    }

    /**
     * @see TagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {

        StringBuilder str = new StringBuilder("<select id=\"status\" name=\"status\"");

        if (onchange != null) {
            str.append(" onchange=\"submit()\"");
        }
        str.append(">\n");

        for (CourseStatus cs : CourseStatus.values()) {
            str.append("<option value=\"")
                    .append(cs)
                    .append("\" ")
                    .append(cs.toString().equalsIgnoreCase(status) ? "selected" : "")
                    .append(">")
                    .append(cs.getCourseStatus(pageContext.getSession().getAttribute("language").toString()))
                    .append("</option>\n");
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
