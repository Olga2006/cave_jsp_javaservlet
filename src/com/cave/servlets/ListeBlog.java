package com.cave.servlets;

import com.cave.dao.BlogDao;
import com.cave.dao.DAOFactory;
import com.cave.tools.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/blog")
public class ListeBlog extends HttpServlet {

    public static final String VUE = "/WEB-INF/jsp/public/afficherBlog.jsp";


    public static final String CONF_DAO_FACTORY = "daofactory";
    private BlogDao blogDao;

    public void init() throws ServletException {
        this.blogDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getBlogDao();

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        sessionUtils.updateBlogs();
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);

    }
}