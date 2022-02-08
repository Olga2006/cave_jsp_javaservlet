package com.cave.servlets;

import com.cave.beans.Utilisateur;
import com.cave.dao.DAOFactory;
import com.cave.dao.ErreurDao;
import com.cave.tools.MailUtils;
import com.cave.tools.ServiceUtils;
import com.cave.tools.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/support")
public class Support extends HttpServlet {

    public static final String PARAM_SESSION_USER = "sessionUtilisateur";
    private static final String CHAMP_EMAIL = "email";
    private static final String CHAMP_NOM = "nom";
    private static final String CHAMP_MESSAGE = "messageSupport";
    public static final String VUE = "/WEB-INF/jsp/public/support.jsp";
    public static final String ATT_RESULTAT = "resultat";
    public static final String PAGE = "support";
    public static final String CONF_DAO_FACTORY = "daofactory";

    private ErreurDao erreurDao;

    public void init() throws ServletException {
        this.erreurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getErreurDao();
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Utilisateur sessionUtilisateur = sessionUtils.getPersDataSessionUtilisateur();

        String email = sessionUtils.getValeurChampString(request, CHAMP_EMAIL);
        String nom = sessionUtils.getValeurChampString(request, CHAMP_NOM);
        String message = sessionUtils.getValeurChampString(request, CHAMP_MESSAGE);

        String[] recipients = new String[]{"olga20reba@gmail.com"};
        String[] bccRecipients = new String[]{"olga20reba@gmail.com"};
        String subject = "Mail from, caveWeb support mail ";
        String messageBody = "from: " + nom + "\n" +
                "email: " + email + "\n" + "\n" +
                "message: \n" + message;

        Boolean emailSended = new MailUtils().sendMail(erreurDao, sessionUtilisateur, recipients, bccRecipients, subject, messageBody, PAGE);
        String resultat = emailSended ? "true" : "false";
        request.setAttribute(ATT_RESULTAT, resultat);
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

}