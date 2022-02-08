package com.cave.servlets;

import com.cave.beans.Utilisateur;
import com.cave.dao.DAOException;
import com.cave.dao.DAOFactory;
import com.cave.dao.ErreurDao;
import com.cave.dao.PubDao;
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

@WebServlet("/suppressionPub")
public class SuppressionPub extends HttpServlet {
    public static final String VUE_FORM = "/WEB-INF/jsp/restreint/afficherPub.jsp";
    public static final String VUE_SUCCES = "/managerAD";
    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_ID_PUB = "idPub";
    public static final String PARAM_SESSION_USER = "sessionUtilisateur";
    public static final String PARAM_IMAGE_PUB = "imagePub";

    public static final String ATT_SUCCES_DEL = "successDel";
    public static final String ATT_ERREURS = "erreurs";
    private static final String CHAMP_ERREUR_DAO = "erreurDao";
    private static final String CHAMP_ECHEC_DEL = "echecDel";

    public static final String CONF_DAO_FACTORY = "daofactory";

    public static final String PAGE = "suppressionPub";

    private PubDao pubDao;
    private ErreurDao erreurDao;
    private ServiceUtils serviceUtils;

    private String successDel;
    private Map<String, String> erreurs = new HashMap<String, String>();

    public void init() throws ServletException {
        this.pubDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPubDao();
        this.erreurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getErreurDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Utilisateur sessionUtilisateur = sessionUtils.getFullSessionUtilisateur();
        if (sessionUtilisateur != null) {
            Long idPub = serviceUtils.getValeurChampLong(request, PARAM_ID_PUB);
            String imagePub = serviceUtils.getValeurChampString(request, PARAM_IMAGE_PUB);
            if (idPub != null) {
                try {
                    pubDao.supprimer(idPub);
                    new AWSUtils().deleteImage(imagePub, erreurDao, sessionUtilisateur.getId(), PAGE);
                } catch (DAOException e) {
                    e.printStackTrace();
                    erreurs.put(CHAMP_ERREUR_DAO, e.getMessage());
                    erreurs.put(CHAMP_ECHEC_DEL, idPub.toString());
                }
            }

            if (erreurs.isEmpty()) {
                sessionUtils.updateUtilisateurPubs();
                sessionUtils.updateMyDroisAD();
                successDel = idPub.toString();
                request.setAttribute(ATT_SUCCES_DEL, idPub.toString());
            } else {
                request.setAttribute(ATT_ERREURS, erreurs);
            }
            this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);


        } else

            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }
}