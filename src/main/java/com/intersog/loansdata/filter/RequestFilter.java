package com.intersog.loansdata.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@WebFilter("/*")
public class RequestFilter implements Filter {

    private static Logger LOGGER = Logger.getLogger(RequestFilter.class.getSimpleName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            FileHandler fileHandler = new FileHandler("new-file.log");
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.FINE);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.setUseParentHandlers(false);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage() + "; Stacktrace: " + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Begin Request -> ")
                .append("Path: ")
                .append(request.getRequestURI())
                .append(" | \n")
                .append("Headers: \n");
        Enumeration<String> headersName = request.getHeaderNames();
        while (headersName.hasMoreElements()) {
            String headerName = headersName.nextElement();
            String headerValue = request.getHeader(headerName);
            stringBuilder.append(headerName).append(":").append(headerValue).append("\n");
        }
        LOGGER.info(stringBuilder.toString());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
