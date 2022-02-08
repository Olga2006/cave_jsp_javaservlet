package com.cave.servlets;

import com.cave.beans.Utilisateur;
import com.cave.dao.DAOFactory;
import com.cave.dao.UtilisateurDao;
import com.cave.forms.InscriptionForm;
import com.cave.tools.Const;
import com.cave.tools.ServiceUtils;
import com.cave.tools.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/inscription")
public class Inscription extends HttpServlet {
    public static final String VUE = "/WEB-INF/jsp/public/inscription.jsp";
    //public static final String VUE_SUCCES = "/listeCaves";
    public static final String VUE_SUCCES = "/WEB-INF/jsp/restreint/afficherCaves.jsp";

    public static final String PARAM_ID_USER = "idUser";
    public static final String PARAM_SESSION_USER = "sessionUtilisateur";

    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_USER = "utilisateur";
    public static final String ATT_FORM = "form";

    public static final String CONF_DAO_FACTORY = "daofactory";
    private UtilisateurDao utilisateurDao;
    private ServiceUtils serviceUtils;
    private Utilisateur utilisateur;

    public Inscription() {

    }

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long idUser = serviceUtils.getValeurChampLong(request, PARAM_ID_USER);
        if (idUser != null) {
            SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
            request.setAttribute(ATT_USER, sessionUtils.getPersDataSessionUtilisateur());
        }
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        InscriptionForm form = new InscriptionForm(utilisateurDao, serviceUtils);
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Utilisateur sessionUtilisateur = sessionUtils.getFullSessionUtilisateur();
        Long idUser = serviceUtils.getValeurChampLong(request, PARAM_ID_USER);

        if (sessionUtilisateur != null && idUser != null && sessionUtilisateur.getId().equals(idUser)) {
            utilisateur = form.updateUtilisateur(request, sessionUtils.getInAllowedAD(), sessionUtils.getIsAdmin());
        } else {
            utilisateur = form.inscrireUtilisateur(request, sessionUtils.getInAllowedAD(), sessionUtils.getIsAdmin());
            sessionUtils.updateFirstPubs();
            sessionUtils.updateSecondPubs();
            //request.setAttribute(Const.ATT_IS_FIRST_CONNECTION, true);
            request.setAttribute(Const.ATT_IS_FIRST_CONNECTION, false);
        }
        if (form.getErreurs().isEmpty()) {
            sessionUtils.setPersDataUtilisateur(utilisateur);
            sessionUtils.updateMyPayedDrois();
            this.getServletContext().getRequestDispatcher(VUE_SUCCES).forward(request, response);
            //response.sendRedirect(request.getContextPath() + VUE_SUCCES);
        } else {
            request.setAttribute(ATT_FORM, form);
            request.setAttribute(ATT_USER, utilisateur);
            this.getServletContext().getRequestDispatcher(VUE).forward(request, response);


        }
    }
}