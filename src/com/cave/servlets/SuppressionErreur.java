package com.cave.servlets;

import com.cave.beans.Utilisateur;
import com.cave.dao.DAOException;
import com.cave.dao.DAOFactory;
import com.cave.dao.ErreurDao;
import com.cave.tools.ServiceUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/suppressionErreur")
public class SuppressionErreur extends HttpServlet {
    public static final String VUE = "/listeErreurs";
    public static final String VUE_FORM = "/WEB-INF/jsp/restreint/afficherErreurs.jsp";
    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_ID_ERREUR = "idErreur";
    public static final String PARAM_NOM_USER = "nomUser";
    public static final String PARAM_SESSION_USER = "sessionUtilisateur";

    public static final String ATT_SUCCES_DEL = "successDel";
    public static final String ATT_ERREURS = "erreurs";
    private static final String CHAMP_ERREUR_DAO = "erreurDao";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private ErreurDao erreurDao;
    private ServiceUtils serviceUtils;

    private String successDel;
    private Map<String, String> erreurs = new HashMap<String, String>();

    public void init() throws ServletException {
        this.erreurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getErreurDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);

        if (sessionUtilisateur != null && sessionUtilisateur.getEmail().equals("olga20reba@gmail.com")) {
            /* Récupération du paramètre */
            Long idErreur = serviceUtils.getValeurChampLong(request, PARAM_ID_ERREUR);
            if (idErreur != null) {
                try {
                    erreurDao.supprimer(idErreur);
                } catch (DAOException e) {
                    e.printStackTrace();
                    erreurs.put(CHAMP_ERREUR_DAO, e.getMessage());
                }
            }

            if (erreurs.isEmpty()) {
                successDel = idErreur.toString();
                request.setAttribute(ATT_SUCCES_DEL, successDel);
            } else {
                request.setAttribute(ATT_ERREURS, erreurs);
            }

            this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);

        } else

            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }
}