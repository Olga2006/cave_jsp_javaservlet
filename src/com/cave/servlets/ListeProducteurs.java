package com.cave.servlets;

import com.cave.beans.Producteur;
import com.cave.beans.Utilisateur;
import com.cave.dao.DAOFactory;
import com.cave.tools.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/listeProducteurs")
public class ListeProducteurs extends HttpServlet {

    public static final String VUE = "/WEB-INF/jsp/restreint/afficherProducteurs.jsp";
    public static final String ACCES_CONNEXION = "/connection";

    public static final String ATT_MY_PRODUCTEURS_IS_EMPTY = "myProducteursIsEmpty";

    public static final String CONF_DAO_FACTORY = "daofactory";

    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Utilisateur sessionUtilisateur = sessionUtils.getFullSessionUtilisateur();
        if (sessionUtilisateur != null) {
            sessionUtils.updateProducteursAffiche();
            List<Producteur> myProducteurs = sessionUtilisateur.getProducteurs();
            if (myProducteurs.isEmpty()) {
                request.setAttribute(ATT_MY_PRODUCTEURS_IS_EMPTY, true);
            }
            this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }
}