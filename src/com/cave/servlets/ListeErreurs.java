package com.cave.servlets;

import com.cave.beans.Erreur;
import com.cave.beans.Utilisateur;
import com.cave.dao.DAOFactory;
import com.cave.dao.ErreurDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/listeErreurs")
public class ListeErreurs extends HttpServlet {

    public static final String VUE = "/WEB-INF/jsp/restreint/afficherErreurs.jsp";
    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_SESSION_USER = "sessionUtilisateur";
    public static final String LIST_ERREURS = "listErreurs";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private ErreurDao erreurDao;


    public void init() throws ServletException {
        this.erreurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getErreurDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);
        if (sessionUtilisateur != null && sessionUtilisateur.getEmail().equals("olga20reba@gmail.com")) {
            List<Erreur> listErreurs = erreurDao.listerErreurs();
            session.setAttribute(LIST_ERREURS, listErreurs);
            this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }
}