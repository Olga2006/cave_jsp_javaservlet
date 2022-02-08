package com.cave.servlets;

import com.cave.dao.DAOFactory;
import com.cave.tools.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/listeUsers")
public class ListeUsers extends HttpServlet {

    public static final String VUE = "/WEB-INF/jsp/restreint/afficherUsers.jsp";
    public static final String ACCES_CONNEXION = "/connection";

    public static final String CONF_DAO_FACTORY = "daofactory";

    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        if (sessionUtils.getIsAdmin()) {
            sessionUtils.updateListUtilisateurs();
            this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }
}