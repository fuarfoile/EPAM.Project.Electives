package com.boast.controller.util.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/** Фильтр устаналвивающий кодировку*/
@WebFilter( filterName = "EncodingFilter",
        urlPatterns = { "/*" },
        initParams = {@WebInitParam(name = "encoding",
                                    value = "UTF-8" ) } )
public class EncodingFilter implements Filter{
    private static Logger logger = LogManager.getLogger(EncodingFilter.class);
    private FilterConfig filterConfig = null;

    /**
     * @see Filter#doFilter(ServletRequest request,
     *      ServletResponse response, FilterChain chain)
     */
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String encoding = request.getCharacterEncoding();
        String defaultEncoding = filterConfig.getInitParameter("encoding");
        if (!defaultEncoding.equalsIgnoreCase(encoding)) {
            response.setCharacterEncoding(defaultEncoding);
            request.setCharacterEncoding(defaultEncoding);
        }

        logger.debug("EncodingFilter");
        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig config)
     */
    public void init(FilterConfig config) throws ServletException {
        this.filterConfig = config;
    }
}
