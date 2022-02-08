package com.cave.servlets;

import com.cave.beans.Bouteille;
import com.cave.beans.Utilisateur;
import com.cave.dao.BouteilleDao;
import com.cave.dao.DAOFactory;
import com.cave.dao.ProducteurDao;
import com.cave.dao.UtilisateurDao;
import com.cave.forms.CreationBouteilleForm;
import com.cave.tools.ServiceUtils;
import com.cave.tools.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ajouterDansLC")
public class AjouterInShopList extends HttpServlet {
    public static final String VUE = "/listeBouteilles";
    public static final String VUE_FORM = "/WEB-INF/jsp/restreint/afficherBouteilles.jsp";

    public static final String VUE_SHOP_LIST = "/listeCourses";
    public static final String VUE_FORM_SHOP_LIST = "/WEB-INF/jsp/restreint/afficherLC.jsp";

    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_SESSION_USER = "sessionUtilisateur";
    public static final String PARAM_IS_SHOP_LIST = "isShopList";

    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_BOUTEILLE = "bouteilleLC";
    public static final String ATT_FORM = "form";
    public static final String ATT_BOUTEILLES_LC = "bouteillesLC";
    private static final String ATT_BOUTEILLES = "bouteilles";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private Bouteille bouteille;

    private BouteilleDao bouteilleDao;
    private ProducteurDao producteurDao;
    private UtilisateurDao utilisateurDao;
    private ServiceUtils serviceUtils;

    public void init() throws ServletException {
        this.bouteilleDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getBouteilleDao();
        this.producteurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getProducteurDao();
        this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Utilisateur sessionUtilisateur = sessionUtils.getFullSessionUtilisateur();
        if (sessionUtilisateur != null) {
            CreationBouteilleForm form = new CreationBouteilleForm(producteurDao, bouteilleDao, sessionUtils);
            bouteille = form.updateQuantiteLC(request);
            sessionUtils.updateOnlySessionBouteille(bouteille);
            String isShopList = serviceUtils.getValeurChampString(request, PARAM_IS_SHOP_LIST);
            request.setAttribute(ATT_FORM, form);
            if (!form.getErreurs().isEmpty()) {
                request.setAttribute(ATT_BOUTEILLE, bouteille);
            }
            if (isShopList != null) {
                Utilisateur sessionUtilisateurMAJ = sessionUtils.getFullSessionUtilisateur();
                List<Bouteille> bouteilles = sessionUtilisateurMAJ.getBouteilles();
                List<Bouteille> bouteillesLC = new ArrayList<>();
                for (Bouteille bouteilleCurr : bouteilles) {
                    if (bouteilleCurr.getNbrListCourses() > 0) {
                        bouteillesLC.add(bouteilleCurr);
                    }
                }
                request.setAttribute(ATT_BOUTEILLES_LC, bouteillesLC);
                this.getServletContext().getRequestDispatcher(VUE_FORM_SHOP_LIST).forward(request, response);
            } else {
                this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
            }
/*            if (form.getErreurs().isEmpty()) {


            } else {
                request.setAttribute(ATT_BOUTEILLE, bouteille);
                if (isShopList != null) {
                    List<Bouteille> bouteilles = sessionUtilisateur.getBouteilles();
                    List<Bouteille> bouteillesLC = new ArrayList<>();
                    for (Bouteille bouteilleCurr : bouteilles) {
                        if (bouteilleCurr.getNbrListCourses() > 0) {
                            bouteillesLC.add(bouteilleCurr);
                        }
                    }
                    request.setAttribute(ATT_BOUTEILLES_LC, bouteillesLC);
                    this.getServletContext().getRequestDispatcher(VUE_FORM_SHOP_LIST).forward(request, response);
                } else {
                    this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
                }

            }*/

        } else

            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }

}