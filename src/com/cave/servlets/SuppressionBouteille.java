package com.cave.servlets;

import com.cave.beans.Utilisateur;
import com.cave.dao.BouteilleDao;
import com.cave.dao.DAOException;
import com.cave.dao.DAOFactory;
import com.cave.dao.ErreurDao;
import com.cave.tools.AWSUtils;
import com.cave.tools.ServiceUtils;
import com.cave.tools.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/suppressionBouteille")
public class SuppressionBouteille extends HttpServlet {
    public static final String VUE = "/listeBouteilles";
    public static final String VUE_FORM = "/WEB-INF/jsp/restreint/afficherBouteilles.jsp";
    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_ID_BOUTEILLE = "idBouteille";
    public static final String PARAM_NOM_BOUTEILLE = "nomBouteille";
    public static final String PARAM_IMAGE_BOUTEILLE = "imageBouteille";

    public static final String ATT_SUCCES_DEL = "successDel";
    public static final String ATT_ERREURS = "erreurs";
    public static final String CHAMP_ERREUR_DAO = "erreurDao";
    public static final String CHAMP_ECHEC_DEL = "echecDel";

    public static final String PAGE = "suppressionBouteille";

    public static final String CONF_DAO_FACTORY = "daofactory";


    private BouteilleDao bouteilleDao;
    private ErreurDao erreurDao;
    private ServiceUtils serviceUtils;

    private String successDel;

    private Map<String, String> erreurs = new HashMap<String, String>();

    public void init() throws ServletException {
        /* Récupération d'une instance de nos DAO Client et Commande */
        this.bouteilleDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getBouteilleDao();
        this.erreurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getErreurDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Utilisateur sessionUtilisateur = sessionUtils.getFullSessionUtilisateur();
        if (sessionUtilisateur != null) {
            Long idBouteille = serviceUtils.getValeurChampLong(request, PARAM_ID_BOUTEILLE);
            String nomBouteille = serviceUtils.getValeurChampString(request, PARAM_NOM_BOUTEILLE);
            String imageBouteille = serviceUtils.getValeurChampString(request, PARAM_IMAGE_BOUTEILLE);

            if (idBouteille != null) {
                try {
                    bouteilleDao.supprimer(idBouteille);
                    if (imageBouteille != null) {
                        new AWSUtils().deleteImage(imageBouteille, erreurDao, sessionUtilisateur.getId(), PAGE);
                    }

                } catch (DAOException e) {
                    e.printStackTrace();
                    erreurs.put(CHAMP_ERREUR_DAO, e.getMessage());
                    erreurs.put(CHAMP_ECHEC_DEL, nomBouteille);
                }
            }
            if (erreurs.isEmpty()) {
                successDel = nomBouteille;
                request.setAttribute(ATT_SUCCES_DEL, successDel);
                sessionUtils.updateBouteilles();

            } else {
                request.setAttribute(ATT_ERREURS, erreurs);
            }
            this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }
}