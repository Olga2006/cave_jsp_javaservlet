package com.cave.servlets;

import com.cave.beans.Utilisateur;
import com.cave.dao.DAOException;
import com.cave.dao.DAOFactory;
import com.cave.dao.ProducteurDao;
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

@WebServlet("/suppressionProducteur")
public class SuppressionProducteur extends HttpServlet {
    public static final String VUE_FORM = "/WEB-INF/jsp/restreint/afficherProducteurs.jsp";
    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_ID_PRODUCTEUR = "idProducteur";
    public static final String PARAM_NOM_PRODUCTEUR = "nomProducteur";

    public static final String ATT_SUCCES_DEL = "successDel";
    public static final String ATT_ERREURS = "erreurs";
    private static final String CHAMP_ERREUR_DAO = "erreurDao";
    private static final String CHAMP_ECHEC_DEL = "echecDel";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private ProducteurDao producteurDao;
    private ServiceUtils serviceUtils;

    private String successDel;
    private Map<String, String> erreurs = new HashMap<String, String>();

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Producteur */
        this.producteurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getProducteurDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Utilisateur sessionUtilisateur = sessionUtils.getFullSessionUtilisateur();
        if (sessionUtilisateur != null) {
            /* Récupération du paramètre */
            Long idProducteur = serviceUtils.getValeurChampLong(request, PARAM_ID_PRODUCTEUR);
            String nomProducteur = serviceUtils.getValeurChampString(request, PARAM_NOM_PRODUCTEUR);
            if (idProducteur != null) {
                try {
                    producteurDao.supprimer(idProducteur);
                } catch (DAOException e) {
                    e.printStackTrace();
                    erreurs.put(CHAMP_ERREUR_DAO, e.getMessage());
                    erreurs.put(CHAMP_ECHEC_DEL, nomProducteur);
                }
            }
            if (erreurs.isEmpty()) {
                successDel = nomProducteur;
                request.setAttribute(ATT_SUCCES_DEL, successDel);
                sessionUtils.updateProducteurs();
            } else {
                request.setAttribute(ATT_ERREURS, erreurs);
            }
            this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);

        } else

            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }

}