package com.cave.servlets;

import com.cave.beans.Utilisateur;
import com.cave.dao.DAOFactory;
import com.cave.dao.UtilisateurDao;
import com.cave.forms.InscriptionForm;
import com.cave.tools.ServiceUtils;
import com.cave.tools.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/creationUser")
public class CreationUserByAdmin extends HttpServlet {

    public static final String VUE_SUCCES = "/listeUsers";
    public static final String VUE_FORM_ADMIN = "/WEB-INF/jsp/restreint/afficherUsers.jsp";

    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_SESSION_USER = "sessionUtilisateur";
    public static final String PARAM_ID_USER = "idUser";

    public static final String ATT_USER = "utilisateur";
    public static final String ATT_FORM = "form";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private Utilisateur user;

    private UtilisateurDao utilisateurDao;

    private ServiceUtils serviceUtils;

    public void init() throws ServletException {
        this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long idUser = serviceUtils.getValeurChampLong(request, PARAM_ID_USER);
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        if (sessionUtils.getIsAdmin() && idUser != null) {
            Utilisateur utilisateur = sessionUtils.getUtilisateurFromList(idUser);
            request.setAttribute(ATT_USER, utilisateur);
            this.getServletContext().getRequestDispatcher(VUE_FORM_ADMIN).forward(request, response);
        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(
                    request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Long idUser = serviceUtils.getValeurChampLong(request, PARAM_ID_USER);
        if (sessionUtils.getIsAdmin()) {
            InscriptionForm form = new InscriptionForm(utilisateurDao, serviceUtils);
            if (idUser != null) {
                user = form.updateUtilisateur(request, true, true);
            } else {
                user = form.inscrireUtilisateur(request, true, true);
            }
            if (!form.getErreurs().isEmpty()) {
                request.setAttribute(ATT_USER, user);
            } else {
                sessionUtils.updateListUtilisateurs();
            }
            request.setAttribute(ATT_FORM, form);
            this.getServletContext().getRequestDispatcher(VUE_FORM_ADMIN).forward(request, response);
        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }
}