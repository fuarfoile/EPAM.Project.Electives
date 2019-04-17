package com.boast.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter( filterName = "AuthorizationFilter",
        urlPatterns = { "/index.jsp" })
public class AuthorizationFilter implements Filter {
    private static Logger logger = LogManager.getLogger(AuthorizationFilter.class);

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

        logger.debug("log = " + log + ", pass = " + pass);

        if (log != null && pass != null) {
            res.sendRedirect(contextPath + "/controller");
        } else {
            chain.doFilter(request, response);
        }
    }
    public void init(FilterConfig config) throws ServletException { }
    public void destroy() { }
}
