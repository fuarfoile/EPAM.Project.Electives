package com.boast.controller.util.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/** Тег реализующий шапку страницы*/
public class HeaderTag extends TagSupport {

    private String text;

    public void setText(String text){
        this.text = text;
    }

    /**
     * @see TagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {

        Locale locale = new Locale((String) pageContext.getSession().getAttribute("language"));
        ResourceBundle resource = ResourceBundle.getBundle("localization/translation", locale);

        String searchText = (String) pageContext.getSession().getAttribute("searchText");

        String str = "<header>\n\t\t<h1>" + text + "</h1>";
        str += "\n<div class=\"search-box\">\n" +
                "<input form=\"searchForm\" type=\"text\" name=\"searchText\" placeholder=\"" +
                resource.getString("tag.header.search.placeholder") +
                "\" value=" + (searchText == null ? "" : searchText) + ">\n" +
                "<button form=\"searchForm\" type=\"submit\">></button>\n" +
                "</div>\n" +
                "<nav>\n";

        if (!"ACCOUNT_UPDATE".equals(pageContext.getRequest().getParameter("fromPage"))) {
            str += "<input form=\"accountForm\" type=\"hidden\" name=\"fromPage\" value=\"ACCOUNT_UPDATE\"/>" +
                    "<button form=\"accountForm\" type=\"submit\">" +
                    resource.getString("tag.header.nav.account") +
                    "</button>\n";
        }

        str += "<button form=\"logoutForm\" type=\"submit\">" +
                resource.getString("tag.header.nav.exit") +
                "</button>\n" +
                "</nav>\n</header>";

        try {
            JspWriter out = pageContext.getOut();
            out.write(str);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }

        return SKIP_BODY;
    }
}
