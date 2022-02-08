package com.cave.servlets;

import com.cave.beans.Utilisateur;
import com.cave.dao.DAOException;
import com.cave.dao.DAOFactory;
import com.cave.dao.PositionDao;
import com.cave.tools.ServiceUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/managerPosition")
public class ManagerPosition extends HttpServlet {

    public static final String VUE = "/redigerCave";
    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_ID_RANGEE = "idRangee";
    public static final String PARAM_NBR_POSITION = "nbrPosition";
    public static final String PARAM_LAST_POSITION = "lastPosition";

    public static final String PARAM_TAB = "tab";
    public static final String PARAM_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_TAB = "tab";
    public static final String ATT_ID_RANGEE = "idRangeeAficher";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private PositionDao positionDao;
    private ServiceUtils serviceUtils;

    public void init() throws ServletException {
        this.positionDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPositionDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);
        if (sessionUtilisateur != null) {
            String tab = serviceUtils.getValeurChampString(request, PARAM_TAB);
            Long idRangee = serviceUtils.getValeurChampLong(request, PARAM_ID_RANGEE);
            Integer nbrPosition = serviceUtils.getValeurChampInt(request, PARAM_NBR_POSITION);
            Integer lastPosition = serviceUtils.getValeurChampInt(request, PARAM_LAST_POSITION);
            if (idRangee != null && lastPosition != nbrPosition) {
                session.setAttribute(ATT_ID_RANGEE, idRangee);
                try {
                    if (lastPosition < nbrPosition) {
                        positionDao.creer(idRangee, lastPosition, nbrPosition);
                    }
                    if (lastPosition > nbrPosition) {
                        positionDao.supprimerLastPosition(idRangee, nbrPosition);
                    }
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
            if (tab != null) {
                session.setAttribute(ATT_TAB, tab);
            }
            response.sendRedirect(request.getContextPath() + VUE + "#aficherRangee");
        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }


}