package com.cave.servlets;

import com.cave.beans.Utilisateur;
import com.cave.dao.BlogDao;
import com.cave.dao.DAOException;
import com.cave.dao.DAOFactory;
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

@WebServlet("/suppressionBlog")
public class SuppressionBlog extends HttpServlet {
    public static final String VUE_FORM = "/WEB-INF/jsp/public/afficherBlog.jsp";
    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_ID_BLOG = "idBlog";
    public static final String PARAM_NOM_BLOG = "nomBlog";
    public static final String PARAM_SESSION_USER = "sessionUtilisateur";

    public static final String ATT_SUCCES_DEL = "successDel";
    public static final String ATT_ERREURS = "erreurs";
    private static final String CHAMP_ERREUR_DAO = "erreurDao";
    private static final String CHAMP_ECHEC_DEL = "echecDel";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private BlogDao blogDao;
    private ServiceUtils serviceUtils;

    private String successDel;
    private Map<String, String> erreurs = new HashMap<String, String>();

    public void init() throws ServletException {
        this.blogDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getBlogDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);

        if (sessionUtilisateur != null && sessionUtilisateur.getEmail().equals("olga20reba@gmail.com")) {
            Long idBlog = serviceUtils.getValeurChampLong(request, PARAM_ID_BLOG);
            String nomBlog = serviceUtils.getValeurChampString(request, PARAM_NOM_BLOG);
            if (idBlog != null) {
                try {
                    blogDao.supprimer(idBlog);
                } catch (DAOException e) {
                    e.printStackTrace();
                    erreurs.put(CHAMP_ERREUR_DAO, e.getMessage());
                    erreurs.put(CHAMP_ECHEC_DEL, nomBlog);
                }
            }

            if (erreurs.isEmpty()) {
                successDel = nomBlog;
                request.setAttribute(ATT_SUCCES_DEL, nomBlog);
            } else {
                request.setAttribute(ATT_ERREURS, erreurs);
            }

            this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);

        } else

            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }
}