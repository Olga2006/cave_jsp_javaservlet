package com.cave.servlets;

import com.cave.dao.DAOFactory;
import com.cave.dao.ErreurDao;
import com.cave.dao.UtilisateurDao;
import com.cave.forms.ConnectionForm;
import com.cave.tools.MailUtils;
import com.cave.tools.ServiceUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/envoerMailMDP")
public class MDPOubliee extends HttpServlet {
    public static final String VUE = "/WEB-INF/jsp/public/connection.jsp";


    public static final String ATT_FORM = "form";
    public static final String PARAM_USER = "utilisateur";

    public static final String CONF_DAO_FACTORY = "daofactory";

    public static final String CHAMP_ERREUR_DAO_SEND_MAIL = "erreurDaoSendMail";
    private static final String CHAMP_EMAIL = "email";

    public static final String PAGE = "envoerMailMDP";

    private UtilisateurDao utilisateurDao;
    private ErreurDao erreurDao;
    private ServiceUtils serviceUtils;

    public void init() throws ServletException {
        this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
        this.erreurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getErreurDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ConnectionForm form = new ConnectionForm(utilisateurDao, serviceUtils);
        String email = serviceUtils.getValeurChampString(request, CHAMP_EMAIL);
        Map<String, String> nomMdp = form.trouverNomMDPParMail(email);
        boolean emailSended = false;
        if (!nomMdp.isEmpty() && form.getErreurs().isEmpty()) {
            Map.Entry<String, String> entry = nomMdp.entrySet().iterator().next();
            String[] recipients = new String[]{email};
            String[] bccRecipients = new String[]{"olga20reba@gmail.com"};
            String subject = "Mail from, caveWeb support mail ";
            String messageBody = "Bonjour " + entry.getKey() + "\n" + "\n" +
                    "Vous avez demandé à vous envoyer  du mot de passe de votre compte CaveWeb." + "\n" + "\n" +
                    "Votre mot de passe : " + entry.getValue() + "\n" + "\n" +
                    "Lorsque vous êtes connecté à votre compte, vous pouvez modifier votre mot de passe en cliquant sur le bouton «Mettre à jour» dans le menu principal." + "\n" + "\n" +
                    "À très bientôt. \n" +
                    "L’équipe CaveWeb.";
            emailSended = new MailUtils().sendMail(erreurDao, null, recipients, bccRecipients, subject, messageBody, PAGE);
        }

        if (!emailSended && form.getErreurs().isEmpty()) {
            form.setErreur(CHAMP_ERREUR_DAO_SEND_MAIL, "ERREUR_DAO");
            form.setSuccess("");
        }
        request.setAttribute(ATT_FORM, form);
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

}