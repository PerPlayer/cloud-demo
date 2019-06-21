package com.cloud.zuul.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/main/*", servletNames = "testFilter")
public class TestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("******* TestFilter Init ******");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("******* TestFilter: " + ((HttpServletRequest)request).getRequestURI() + " *******");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
