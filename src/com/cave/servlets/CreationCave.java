package com.cave.servlets;

import com.cave.beans.Cave;
import com.cave.beans.Utilisateur;
import com.cave.dao.CaveDao;
import com.cave.dao.DAOFactory;
import com.cave.dao.UtilisateurDao;
import com.cave.forms.CreationCaveForm;
import com.cave.tools.ServiceUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/creationCave")
public class CreationCave extends HttpServlet {
    public static final String VUE_SUCCES = "/listeCaves";
    public static final String VUE_FORM = "/WEB-INF/jsp/restreint/afficherCaves.jsp";

    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_SESSION_USER = "sessionUtilisateur";
    public static final String PARAM_ID_CAVE = "idCave";
    public static final String PARAM_IS_CREATION = "isCreation";

    public static final String ATT_CAVE = "cave";
    public static final String ATT_IS_CREATION = "isCreation";
    public static final String ATT_FORM = "form";

    public static final String ATT_SESSION_USER = "sessionUtilisateur";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private Cave cave;

    private CaveDao caveDao;
    private UtilisateurDao utilisateurDao;
    private ServiceUtils serviceUtils;
    /* String controlCorrection; */

    public void init() throws ServletException {
        this.caveDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCaveDao();
        this.serviceUtils = new ServiceUtils();
        this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);
        if (sessionUtilisateur != null) {
            Long idCave = serviceUtils.getValeurChampLong(request, PARAM_ID_CAVE);
            String isCreation = serviceUtils.getValeurChampString(request, PARAM_IS_CREATION);
            if (idCave != null) {
                Cave cave = caveDao.trouver(idCave);
                request.setAttribute(ATT_CAVE, cave);
            }
            if (isCreation != null) {
                request.setAttribute(ATT_IS_CREATION, isCreation);
            }
            this.getServletContext().getRequestDispatcher(VUE_FORM).forward(
                    request, response);
        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(
                    request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);
        if (sessionUtilisateur != null) {
            CreationCaveForm form = new CreationCaveForm(caveDao, serviceUtils);
            if (request.getParameter(PARAM_ID_CAVE) != null
                    && !request.getParameter(PARAM_ID_CAVE).isEmpty()) {
                cave = form.updateCave(request, sessionUtilisateur);
            } else {
                cave = form.creerCave(request, sessionUtilisateur);
            }

            if (form.getErreurs().isEmpty()) {
                request.setAttribute(ATT_FORM, form);
                Long id_sessionUtilisateur = sessionUtilisateur.getId();
                Utilisateur sessionUtilisateurMAJ = utilisateurDao.trouver(id_sessionUtilisateur);
                session.setAttribute(ATT_SESSION_USER, sessionUtilisateurMAJ);
                this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
            } else {
                request.setAttribute(ATT_CAVE, cave);
                request.setAttribute(ATT_FORM, form);
                this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
            }

        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }
}