package com.cave.tools;

import com.cave.beans.Utilisateur;
import com.cave.dao.DAOFactory;
import com.cave.dao.UtilisateurDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/sqlUpdate")
public class SqlUpdate extends HttpServlet {
    public static final String VUE_FORM_ADMIN = "/WEB-INF/jsp/restreint/sqlUpdate.jsp";

    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_SESSION_USER = "sessionUtilisateur";
    public static final String PARAM_REQUEST_SQL = "requestSql";

    public static final String ATT_RESULTAT = "resultat";

    public static final String CONF_DAO_FACTORY = "daofactory";


    private UtilisateurDao utilisateurDao;
    private ServiceUtils serviceUtils;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Producteur */
        this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);
        if (sessionUtilisateur.getEmail().equals("olga20reba@gmail.com")) {
            this.getServletContext().getRequestDispatcher(VUE_FORM_ADMIN).forward(request, response);
        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(
                    request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);
        String requestSql = serviceUtils.getValeurChampString(request, PARAM_REQUEST_SQL);
        if (sessionUtilisateur.getEmail().equals("olga20reba@gmail.com")) {
            String sqlResultat = utilisateurDao.setSqlUpdate(requestSql);
            String resultat = sqlResultat.equalsIgnoreCase("true") ? "true" : sqlResultat;
            request.setAttribute(ATT_RESULTAT, resultat);
            this.getServletContext().getRequestDispatcher(VUE_FORM_ADMIN).forward(request, response);
        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }
}