package com.cave.servlets;

import com.cave.beans.Utilisateur;
import com.cave.dao.CompartimentDao;
import com.cave.dao.DAOFactory;
import com.cave.tools.ServiceUtils;
import com.cave.tools.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/managerCompartiment")
public class ManagerCompartiment extends HttpServlet {

    public static final String VUE = "/redigerCave";
    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_ID_COMPARTIMENT = "idCompartiment";
    public static final String PARAM_TAB = "tab";
    public static final String PARAM_NEW_NAME_COMPARTIMENT = "newNameCompartiment";

    public static final String ATT_TAB = "tab";
    public static final String ATT_ID_COMPARTIMENT = "idCompartimentAficher";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private CompartimentDao compartimentDao;
    private ServiceUtils serviceUtils;

    public void init() throws ServletException {
        this.compartimentDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCompartimentDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Utilisateur sessionUtilisateur = sessionUtils.getFullSessionUtilisateur();
        if (sessionUtilisateur != null) {
            String tab = serviceUtils.getValeurChampString(request, PARAM_TAB);
            /* Récupération du paramètre */
            Long idCompartiment = serviceUtils.getValeurChampLong(request, PARAM_ID_COMPARTIMENT);
            String newNameCompartiment = serviceUtils.getValeurChampString(request, PARAM_NEW_NAME_COMPARTIMENT);

            compartimentDao.updateNom(idCompartiment, newNameCompartiment);
            sessionUtils.updateCaves();

            session.setAttribute(ATT_ID_COMPARTIMENT, idCompartiment);
            if (tab != null) {
                session.setAttribute(ATT_TAB, tab);
            }
            /* Redirection vers la fiche récapitulative */
            response.sendRedirect(request.getContextPath() + VUE + "#aficherCompartiment");
        } else

            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }

}