package com.cave.servlets;

import com.cave.beans.Bouteille;
import com.cave.beans.Utilisateur;
import com.cave.dao.BouteilleDao;
import com.cave.dao.DAOFactory;
import com.cave.dao.ProducteurDao;
import com.cave.forms.CreationBouteilleForm;
import com.cave.tools.ServiceUtils;
import com.cave.tools.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ajouterEvaluation")
public class AjouterEvaluation extends HttpServlet {
    public static final String VUE = "/listeBouteilles";
    public static final String VUE_FORM = "/WEB-INF/jsp/restreint/afficherBouteilles.jsp";
    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_SESSION_USER = "sessionUtilisateur";

    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_BOUTEILLE = "bouteilleEvaluation";
    public static final String ATT_FORM = "form";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private Bouteille bouteille;

    private BouteilleDao bouteilleDao;
    private ProducteurDao producteurDao;
    private ServiceUtils serviceUtils;

    public void init() throws ServletException {
        this.bouteilleDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getBouteilleDao();
        this.producteurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getProducteurDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Utilisateur sessionUtilisateur = sessionUtils.getFullSessionUtilisateur();
        if (sessionUtilisateur != null) {
            CreationBouteilleForm form = new CreationBouteilleForm(producteurDao, bouteilleDao, sessionUtils);
            bouteille = form.updateEvaluation(request, sessionUtilisateur);
            if (form.getErreurs().isEmpty()) {
                sessionUtils.updateOnlySessionBouteille(bouteille);
            } else {
                request.setAttribute(ATT_BOUTEILLE, bouteille);
            }
            request.setAttribute(ATT_FORM, form);
            this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }

}