package com.boast.controller.util.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Фильтр для попытки автоматического всхода в аккаун при наличии соответствующих Cookies*/
@WebFilter( filterName = "AuthorizationFilter",
        urlPatterns = { "/index.jsp" })
public class AuthorizationFilter implements Filter{
    private static Logger logger = LogManager.getLogger(AuthorizationFilter.class);

    /**
     * @see Filter#doFilter(ServletRequest request,
     *      ServletResponse response, FilterChain chain)
     */
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String contextPath = req.getContextPath();

        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        String log = null;
        String pass = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("login")) {
                    log = cookie.getValue();
                }
                if (cookie.getName().equals("password")) {
                    pass = cookie.getValue();
                }
            }
        }

        if (log != null && pass != null) {
            logger.info("log = " + log + " sendRedirect");
            res.sendRedirect(contextPath + "/controller");
        } else {
            logger.info("log = " + log + " chain");
            chain.doFilter(request, response);
        }
    }
}
