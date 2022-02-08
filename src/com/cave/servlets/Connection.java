package com.cave.servlets;

import com.cave.beans.Utilisateur;
import com.cave.dao.DAOFactory;
import com.cave.dao.UtilisateurDao;
import com.cave.forms.ConnectionForm;
import com.cave.tools.Const;
import com.cave.tools.ServiceUtils;
import com.cave.tools.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/connection")
public class Connection extends HttpServlet {
    public static final String VUE = "/WEB-INF/jsp/public/connection.jsp";
    //public static final String VUE_SUCCES = "/listeCaves";
    public static final String VUE_SUCCES = "/WEB-INF/jsp/restreint/afficherCaves.jsp";

    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_USER = "utilisateur";
    public static final String ATT_FORM = "form";
    public static final String PARAM_USER = "utilisateur";
    public static final String CONF_DAO_FACTORY = "daofactory";
    private UtilisateurDao utilisateurDao;
    private ServiceUtils serviceUtils;

    public void init() throws ServletException {
        this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ConnectionForm form = new ConnectionForm(utilisateurDao, serviceUtils);
        Utilisateur utilisateur = form.connecterUtilisateur(request);
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        if (form.getErreurs().isEmpty()) {
            sessionUtils.setFullUtilisateur(utilisateur);
            sessionUtils.updateMyPayedDrois();
            sessionUtils.updateFirstPubs();
            sessionUtils.updateSecondPubs();
            //request.setAttribute(Const.ATT_IS_FIRST_CONNECTION, true);
            request.setAttribute(Const.ATT_IS_FIRST_CONNECTION, false);
            this.getServletContext().getRequestDispatcher(VUE_SUCCES).forward(request, response);
        } else {
            sessionUtils.setFullUtilisateur(null);
            request.setAttribute(ATT_FORM, form);
            request.setAttribute(ATT_USER, utilisateur);
            this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
        }
    }

    /*
     * private static void setCookie( HttpServletResponse response, String nom,
     * String valeur, int maxAge ) { Cookie cookie = new Cookie( nom, valeur );
     * cookie.setMaxAge( maxAge ); response.addCookie( cookie ); }
     */

}