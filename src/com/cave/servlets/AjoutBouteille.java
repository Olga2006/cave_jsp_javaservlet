package com.cave.servlets;

import com.cave.beans.Utilisateur;
import com.cave.dao.DAOFactory;
import com.cave.dao.PositionDao;
import com.cave.dao.UtilisateurDao;
import com.cave.tools.ServiceUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ajouterBouteille")
public class AjoutBouteille extends HttpServlet {
    public static final String VUE_SUCCES = "/redigerCave";
    public static final String VUE_FORM = "/WEB-INF/jsp/restreint/afficherRedacteurCave.jsp";
    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_SESSION_USER = "sessionUtilisateur";
    public static final String PARAM_ID_LAST_POSITION = "idLastPosition";
    public static final String PARAM_ID_POSITION = "idPosition";
    public static final String PARAM_LIST_ID_POSITIONS = "listIdPositions";
    public static final String PARAM_ID_BOUTEILLE = "idBouteille";
    public static final String PARAM_TAB = "tab";

    public static final String ATT_FORM = "form";
    public static final String ATT_TAB = "tab";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_ID_POSITION = "idPositionAficher";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private PositionDao positionDao;
    private UtilisateurDao utilisateurDao;
    private ServiceUtils serviceUtils;

    public void init() throws ServletException {
        /* Récupération d'une instance de nos DAO */
        this.positionDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPositionDao();
        this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);
        if (sessionUtilisateur != null) {
            String tab = serviceUtils.getValeurChampString(request, PARAM_TAB);
            Long idLastPosition = serviceUtils.getValeurChampLong(request, PARAM_ID_LAST_POSITION);
            String listIdPositions = serviceUtils.getValeurChampString(request, PARAM_LIST_ID_POSITIONS);
            Long idPosition = serviceUtils.getValeurChampLong(request, PARAM_ID_POSITION);
            Long idBouteille = serviceUtils.getValeurChampLong(request, PARAM_ID_BOUTEILLE);

            if (idLastPosition != null && idBouteille != null) {
                positionDao.changer_la_position_bouteille(idBouteille, idPosition, idLastPosition);
            } else {
                positionDao.ajouter_la_bouteille(idBouteille, listIdPositions);
            }

            if (tab != null) {
                session.setAttribute(ATT_TAB, tab);
            }
            Long id_sessionUtilisateur = sessionUtilisateur.getId();
            Utilisateur sessionUtilisateurMAJ = utilisateurDao.trouver(id_sessionUtilisateur);
            session.setAttribute(ATT_SESSION_USER, sessionUtilisateurMAJ);
            session.setAttribute(ATT_ID_POSITION, idPosition);
            response.sendRedirect(request.getContextPath() + VUE_SUCCES + "#aficher");

        } else

            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }

}