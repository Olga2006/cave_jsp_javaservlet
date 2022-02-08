package com.cave.servlets;

import com.cave.beans.Producteur;
import com.cave.beans.Utilisateur;
import com.cave.dao.DAOFactory;
import com.cave.dao.ProducteurDao;
import com.cave.dao.UtilisateurDao;
import com.cave.forms.CreationProducteurForm;
import com.cave.tools.ServiceUtils;
import com.cave.tools.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/creationProducteur")
public class CreationProducteur extends HttpServlet {

    public static final String VUE_SUCCES = "/listeProducteurs";
    public static final String VUE_FORM = "/WEB-INF/jsp/restreint/afficherProducteurs.jsp";
    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_ID_PRODUCTEUR = "idProducteur";
    public static final String PARAM_NOM_PRODUCTEUR = "nomProducteur";
    public static final String PARAM_IS_COPY_FROM_PRODUCER = "isCopyFromProducer";


    public static final String ATT_PRODUCTER = "producteur";
    public static final String ATT_FORM = "form";
    public static final String ATT_SUCCES_COPY = "successCopy";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private Producteur producteur;

    private ProducteurDao producteurDao;
    private UtilisateurDao utilisateurDao;
    private ServiceUtils serviceUtils;

    public void init() throws ServletException {
        this.producteurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getProducteurDao();
        this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Utilisateur sessionUtilisateur = sessionUtils.getFullSessionUtilisateur();
        if (sessionUtilisateur != null) {
            Long idProducteur = serviceUtils.getValeurChampLong(request, PARAM_ID_PRODUCTEUR);
            if (idProducteur != null) {
                Boolean isCopyFromProducer = serviceUtils.getValeurChampBool(request, PARAM_IS_COPY_FROM_PRODUCER);
                if (isCopyFromProducer != null && isCopyFromProducer) {
                    CreationProducteurForm form = new CreationProducteurForm(producteurDao, sessionUtils);
                    producteur = form.copyProducteurPourUtilisateur(sessionUtilisateur.getId(), idProducteur);
                    if (form.getErreurs().isEmpty() && producteur != null) {
                        sessionUtils.updateProducteurs();
                    } else {
                        producteur.setId(null);
                        request.setAttribute(ATT_PRODUCTER, producteur);
                    }
                    request.setAttribute(ATT_FORM, form);
                } else {
                    Producteur producteur = producteurDao.trouver(idProducteur);
                    request.setAttribute(ATT_PRODUCTER, producteur);
                }
                this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
            }
        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(
                    request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Utilisateur sessionUtilisateur = sessionUtils.getFullSessionUtilisateur();
        if (sessionUtilisateur != null) {
            CreationProducteurForm form = new CreationProducteurForm(producteurDao, sessionUtils);
            if (request.getParameter(PARAM_ID_PRODUCTEUR) != null
                    && !request.getParameter(PARAM_ID_PRODUCTEUR).isEmpty()) {
                producteur = form.updateProducteur(request, sessionUtilisateur);
            } else {
                producteur = form.creerProducteurPourUtilisateur(request, sessionUtilisateur);
            }

            if (form.getErreurs().isEmpty()) {
                sessionUtils.updateProducteurs();
            } else {
                request.setAttribute(ATT_PRODUCTER, producteur);
            }
            request.setAttribute(ATT_FORM, form);
            this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
        } else

            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);

    }
}