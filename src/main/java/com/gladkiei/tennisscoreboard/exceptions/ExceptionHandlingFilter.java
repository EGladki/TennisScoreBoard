package com.gladkiei.tennisscoreboard.exceptions;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class ExceptionHandlingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        try {
            chain.doFilter(req, resp);
        } catch (BadRequestException e) {
            ((HttpServletResponse) resp).setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("text/plain");
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        } catch (DatabaseOperationException e) {
            ((HttpServletResponse) resp).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("text/plain");
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        } catch (NotFoundException e) {
            ((HttpServletResponse) resp).setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.setContentType("text/plain");
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
