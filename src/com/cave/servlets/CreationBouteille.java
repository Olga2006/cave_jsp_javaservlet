package com.cave.servlets;

import com.cave.beans.Bouteille;
import com.cave.beans.Producteur;
import com.cave.beans.Utilisateur;
import com.cave.dao.*;
import com.cave.forms.CreationBouteilleForm;
import com.cave.tools.AWSUtils;
import com.cave.tools.ServiceUtils;
import com.cave.tools.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/creationBouteille")
public class CreationBouteille extends HttpServlet {

    public static final String VUE_SUCCES = "/listeBouteilles";
    public static final String VUE_FORM = "/WEB-INF/jsp/restreint/afficherBouteilles.jsp";
    public static final String ACCES_CONNEXION = "/connection";

    public static final String VUE_SUCCES_REDACTEUR = "/redigerCave";
    public static final String VUE_FORM_REDACTEUR = "/WEB-INF/jsp/restreint/afficherRedacteurCave.jsp";

    public static final String PARAM_SESSION_USER = "sessionUtilisateur";
    public static final String PARAM_ID_BOUTEILLE = "idBouteille";
    public static final String PARAM_ID_PRODUCTEUR = "idProducteur";
    public static final String PARAM_LIST_ID_POSITION = "listIdPositions";
    public static final String PARAM_ID_POSITION = "idPosition";
    public static final String PARAM_IS_COPY_FROM_PRODUCER = "isCopyFromProducer";
    public static final String PARAM_NOM_BOUTEILLE = "nomBouteille";

    private static final String ATT_BOUTEILLES = "bouteilles";
    public static final String ATT_BOUTEILLE = "bouteille";
    public static final String ATT_PRODUCTER = "producteur";
    public static final String ATT_FORM = "form";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String PAGE = "copyBouteille";

    public static final String ATT_SUCCES_COPY = "successCopy";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private Bouteille bouteille;
    private Producteur producteur;

    private ProducteurDao producteurDao;
    private BouteilleDao bouteilleDao;
    private PositionDao positionDao;
    private ErreurDao erreurDao;

    private ServiceUtils serviceUtils;

    public void init() throws ServletException {
        this.producteurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getProducteurDao();
        this.bouteilleDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getBouteilleDao();
        this.positionDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPositionDao();
        this.erreurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getErreurDao();

        this.serviceUtils = new ServiceUtils();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Utilisateur sessionUtilisateur = sessionUtils.getFullSessionUtilisateur();
        if (sessionUtilisateur != null) {
            Long idBouteille = serviceUtils.getValeurChampLong(request, PARAM_ID_BOUTEILLE);
            String nomBouteille = serviceUtils.getValeurChampString(request, PARAM_NOM_BOUTEILLE);
            Long idProducteurPro = serviceUtils.getValeurChampLong(request, PARAM_ID_PRODUCTEUR);
            Long idProducteur = null;
            Boolean isCopyFromProducer = serviceUtils.getValeurChampBool(request, PARAM_IS_COPY_FROM_PRODUCER);
            if (isCopyFromProducer != null && isCopyFromProducer) {
                CreationBouteilleForm form = new CreationBouteilleForm(producteurDao, bouteilleDao, sessionUtils);
                bouteille = form.copyBouteillePourUtilisateur(sessionUtilisateur, idBouteille, erreurDao, PAGE);


                if (form.getErreurs().isEmpty() && bouteille != null) {
                    sessionUtils.updateBouteilles();
                    sessionUtils.updateProducteurs();
                } else {
                    bouteille.setId(null);
                    request.setAttribute(ATT_BOUTEILLE, bouteille);
                    request.setAttribute(ATT_PRODUCTER, bouteille.getProducteur());
                }
                request.setAttribute(ATT_FORM, form);
                this.getServletContext().getRequestDispatcher(VUE_SUCCES).forward(request, response);
            } else if (idBouteille != null) {
                bouteille = bouteilleDao.trouver(idBouteille);
                Long idProd = bouteille.getIdProducteur();
                producteur = producteurDao.trouver(idProd);
                request.setAttribute(ATT_BOUTEILLE, bouteille);
                request.setAttribute(ATT_PRODUCTER, producteur);
                this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
            }
        } else {
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Utilisateur sessionUtilisateur = sessionUtils.getFullSessionUtilisateur();
        if (sessionUtilisateur != null) {
            String listIdPosition = serviceUtils.getValeurChampString(request, PARAM_LIST_ID_POSITION);
            Long idPosition = serviceUtils.getValeurChampLong(request, PARAM_ID_POSITION);
            CreationBouteilleForm form = new CreationBouteilleForm(producteurDao, bouteilleDao, sessionUtils);
            String idBouteille = serviceUtils.getValeurChampString(request, PARAM_ID_BOUTEILLE);
            if (idBouteille != null) {
                bouteille = form.updateBouteille(request, sessionUtilisateur);
                sessionUtils.updateCaves();
            } else {
                bouteille = form.creerBouteillePourUtilisateur(request, sessionUtilisateur);
            }
            if (form.getErreurs().isEmpty()) {
                sessionUtils.updateBouteilles();
                sessionUtils.updateProducteurs();

                if (listIdPosition != null) {
                    try {
                        positionDao.ajouter_la_bouteille(bouteille.getId(), listIdPosition);
                        sessionUtils.updatePositionAffiche(idPosition);
                        sessionUtils.updateCaves();
                    } catch (DAOException e) {
                        e.printStackTrace();
                    }
                    response.sendRedirect(request.getContextPath() + VUE_SUCCES_REDACTEUR + "#aficher");
                } else {
                    request.setAttribute(ATT_FORM, form);
                    this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
                }

            } else {
                request.setAttribute(ATT_PRODUCTER, bouteille.getProducteur());
                request.setAttribute(ATT_BOUTEILLE, bouteille);
                request.setAttribute(ATT_FORM, form);
                if (idPosition != null) {
                    this.getServletContext().getRequestDispatcher(VUE_FORM_REDACTEUR).forward(request,
                            response);
                } else
                    this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
            }


        } else

            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }
}