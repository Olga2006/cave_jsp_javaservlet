package com.cave.servlets;

import com.cave.beans.Utilisateur;
import com.cave.dao.DAOException;
import com.cave.dao.DAOFactory;
import com.cave.dao.RangeeDao;
import com.cave.tools.ServiceUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/managerRangee")
public class ManagerRangee extends HttpServlet {

    public static final String VUE = "/redigerCave";
    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_ID_COMPARTIMENT = "idCompartiment";
    public static final String PARAM_SESSION_USER = "sessionUtilisateur";
    public static final String PARAM_TAB = "tab";
    public static final String PARAM_NBR_RANGEE = "nbrRangee";
    public static final String PARAM_LAST_RANGEE = "lastRangee";

    public static final String ATT_TAB = "tab";
    public static final String ATT_ID_COMPARTIMENT = "idCompartimentAficher";
    public static final String ATT_ID_RANGEE = "idRangeeAficher";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private RangeeDao rangeeDao;
    private ServiceUtils serviceUtils;

    public void init() throws ServletException {
        this.rangeeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getRangeeDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);
        //session.setAttribute(ATT_ID_RANGEE, null);
        if (sessionUtilisateur != null) {
            String tab = serviceUtils.getValeurChampString(request, PARAM_TAB);
            /* Récupération du paramètre */
            Long idCompartiment = serviceUtils.getValeurChampLong(request, PARAM_ID_COMPARTIMENT);
            Integer nbrRangee = serviceUtils.getValeurChampInt(request, PARAM_NBR_RANGEE);
            Integer lastRangee = serviceUtils.getValeurChampInt(request, PARAM_LAST_RANGEE);
            if (idCompartiment != null && lastRangee != nbrRangee) {
                session.setAttribute(ATT_ID_COMPARTIMENT, idCompartiment);
                try {
                    if (lastRangee < nbrRangee) {
                        rangeeDao.creer(idCompartiment, lastRangee, nbrRangee);
                    }
                    if (lastRangee > nbrRangee) {
                        rangeeDao.supprimerLastRangee(idCompartiment, nbrRangee);
                    }
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
            if (tab != null) {
                session.setAttribute(ATT_TAB, tab);
            }

            /* Redirection vers la fiche récapitulative */
            response.sendRedirect(request.getContextPath() + VUE + "#aficherCompartiment");
        } else

            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }

}