package com.boast.controller.util.filter;

import com.boast.controller.command.TypeCommand;
import com.boast.controller.util.constant.Link;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/** Фильтр перенаправления на страницу логина при потере сессии*/
@WebFilter( filterName = "SessionFilter",
        urlPatterns = { "/*" })
public class SessionFilter implements Filter {
    private static Logger logger = LogManager.getLogger(SessionFilter.class);

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList("", "/css/style.css", Link.WELCOME.getLink())));

    private static final Set<TypeCommand> ALLOWED_COMMANDS = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(TypeCommand.LOGIN,
                    TypeCommand.SIGNUP,
                    TypeCommand.FORGOT,
                    TypeCommand.LOGOUT,
                    TypeCommand.LOCALIZATION,
                    TypeCommand.PASS_RESET,
                    TypeCommand.R_FORGOT,
                    TypeCommand.R_LOGIN,
                    TypeCommand.R_PASS_RESET,
                    TypeCommand.R_SIGNUP)));

    /**
     * @see Filter#doFilter(ServletRequest request,
     *      ServletResponse response, FilterChain chain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");

        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        boolean allowedPath = ALLOWED_PATHS.contains(path);
        boolean allowedCommand = request.getParameter("TypeCommand") == null;
        if (!allowedCommand) {
            TypeCommand typeCommand = TypeCommand.valueOf(request.getParameter("TypeCommand"));
            allowedCommand = ALLOWED_COMMANDS.contains(typeCommand);
        }

        if (loggedIn || allowedPath || allowedCommand) {
            logger.info(path + ", chain");
            chain.doFilter(request, response);
        }
        else {
            logger.info(path + ", sendRedirect to " + req.getContextPath() + Link.WELCOME.getLink());
            res.sendRedirect(req.getContextPath() + Link.WELCOME.getLink());
        }
    }
}