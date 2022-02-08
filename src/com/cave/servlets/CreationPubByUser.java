package com.cave.servlets;

import com.cave.beans.Pub;
import com.cave.beans.Utilisateur;
import com.cave.dao.DAOFactory;
import com.cave.dao.PubDao;
import com.cave.forms.PubForm;
import com.cave.tools.ServiceUtils;
import com.cave.tools.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/creationPub")
public class CreationPubByUser extends HttpServlet {

    public static final String VUE_SUCCES = "/managerAD";
    public static final String VUE_FORM = "/WEB-INF/jsp/restreint/afficherPub.jsp";

    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_SESSION_USER = "sessionUtilisateur";
    public static final String PARAM_ID_PUB = "idPub";
    public static final String ATT_PUB = "pub";
    public static final String ATT_FORM = "form";


    public static final String ATT_SESSION_USER = "sessionUtilisateur";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private PubDao pubDao;

    private ServiceUtils serviceUtils;

    public void init() throws ServletException {
        this.pubDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPubDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Utilisateur sessionUtilisateur = sessionUtils.getFullSessionUtilisateur();

        Long idPub = serviceUtils.getValeurChampLong(request, PARAM_ID_PUB);
        if (idPub != null && sessionUtilisateur != null) {
            Pub pub = pubDao.trouver(idPub);
            request.setAttribute(ATT_PUB, pub);
            this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(
                    request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Utilisateur sessionUtilisateur = sessionUtils.getFullSessionUtilisateur();

        Long idPub = serviceUtils.getValeurChampLong(request, PARAM_ID_PUB);
        if (sessionUtilisateur != null && sessionUtilisateur.getIsWineproducer()) {
            PubForm form = new PubForm(pubDao, serviceUtils);
            Pub pub;
            if (idPub != null) {
                pub = form.updatePub(request, sessionUtilisateur);
            } else {
                pub = form.creerPub(request, sessionUtilisateur);
            }
            if (!form.getErreurs().isEmpty()) {
                request.setAttribute(ATT_PUB, pub);

            } else {
                sessionUtils.updateUtilisateurPubs();
                sessionUtils.updateMyDroisAD();
            }
            request.setAttribute(ATT_FORM, form);
            this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);

        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }
}