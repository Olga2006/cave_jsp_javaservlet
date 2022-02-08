
package com.cave.rest;

import com.cave.beans.Erreur;
import com.cave.beans.UtilisateurAuth;
import com.cave.beans.UtilisateurShort;
import com.cave.dao.DAOException;
import com.cave.dao.DAOFactory;
import com.cave.dao.UtilisateurDao;
import com.cave.tools.Const;
import com.cave.tools.MailUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Path("/AuthMenagement")
public class AuthMenagement extends HttpServlet {

    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String PAGE = "AuthMenagement";

    private DAOFactory dao_Factory = new DAOFactory();
    private DAOFactory daoFactory = dao_Factory.getInstance();

    //http://www.caveweb.net/rest/AuthMenagement/signup
    @POST
    @Path("signup")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response signupUser(String user) throws IOException {
        UtilisateurDao utilisateurDao = daoFactory.getUtilisateurDao();
        UtilisateurAuth utilisateurAuth = new ObjectMapper().readValue(user, UtilisateurAuth.class);
        Long userExistId = utilisateurDao.trouverIdParEmail(utilisateurAuth.getEmail());
        if (userExistId != null) {
            return Response.ok("{\"status\":\"ko\", \"errorId\":\"" + Const.ERR_EMAIL_EXISTS + "\"}").build();
        } else {
            try {
                Long userId = daoFactory.getUtilisateurDao().creer(utilisateurAuth, false, null);
                return Response.ok("{\"status\":\"ok\",\"userId\":" + userId + ",\"expiresIn\":\"10\"}").build();
            } catch (DAOException e) {
                daoFactory.getErreurDao().creer(new Erreur(null, PAGE, e.getMessage()));
                return Response.ok("{\"status\":\"ko\"}").build();
            }
        }
    }

    //http://www.caveweb.net/rest/AuthMenagement/login
    @POST
    @Path("login")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response loginUser(String user) throws IOException {
        UtilisateurDao utilisateurDao = daoFactory.getUtilisateurDao();
        UtilisateurAuth utilisateurAuth = new ObjectMapper().readValue(user, UtilisateurAuth.class);
        UtilisateurShort utilisateur;
        try {
            utilisateur = utilisateurDao.trouverShort(utilisateurAuth.getEmail());
        } catch (DAOException e) {
            daoFactory.getErreurDao().creer(new Erreur(null, PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
        if (utilisateur == null) {
            return Response.ok("{\"status\":\"ko\", \"errorId\":\"" + Const.ERR_EMAIL_NOT_FOUND + "\"}").build();
        } else if (!utilisateur.getMotDePasse().equals(utilisateurAuth.getMotDePasse())) {
            return Response.ok("{\"status\":\"ko\", \"errorId\":\"" + Const.ERR_INVALID_PASSWORD + "\"}").build();
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String userJson;
        try {
            userJson = mapper.writeValueAsString(utilisateur);
        } catch (JsonProcessingException e) {
            daoFactory.getErreurDao().creer(new Erreur(utilisateur.getId(), PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
        return Response.ok("{\"status\":\"ok\",\"user\":" + userJson + ",\"expiresIn\":\"10\"}").build();
    }

    //http://localhost:8080/webCave/rest/AuthMenagement/sendPassword?email=olga20reba@gmail.com
    @POST
    @Path("sendPassword")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response sendPassword(@QueryParam("email") String email) throws IOException {

        boolean emailSended = false;
        Map<String, String> nomMDP = new HashMap<>();
        UtilisateurDao utilisateurDao = daoFactory.getUtilisateurDao();
        try {
            nomMDP = utilisateurDao.trouverNomMDP(email);
            if (nomMDP.isEmpty()) {
                return Response.ok("{\"status\":\"ko\", \"errorId\":\"" + Const.ERR_EMAIL_NOT_FOUND + "\"}").build();
            }

        } catch (DAOException e) {
            daoFactory.getErreurDao().creer(new Erreur(null, PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
        Map.Entry<String, String> entry = nomMDP.entrySet().iterator().next();
        String[] recipients = new String[]{email};
        String[] bccRecipients = new String[]{"olga20reba@gmail.com"};
        String subject = "Mail from, caveWeb support mail ";
        String messageBody = "Bonjour " + entry.getKey() + "\n" + "\n" +
                "Vous avez demandé à vous envoyer  du mot de passe de votre compte CaveWeb." + "\n" + "\n" +
                "Votre mot de passe : " + entry.getValue() + "\n" + "\n" +
                "Lorsque vous êtes connecté à votre compte, vous pouvez modifier votre mot de passe en cliquant sur le bouton «Mettre à jour» dans le menu principal." + "\n" + "\n" +
                "À très bientôt. \n" +
                "L’équipe CaveWeb.";
        emailSended = new MailUtils().sendMail(daoFactory.getErreurDao(), null, recipients, bccRecipients, subject, messageBody, PAGE);

        if (!emailSended) {
            return Response.ok("{\"status\":\"ko\"}").build();
        }
        return Response.ok("{\"status\":\"ok\"}").build();
    }


    //http://www.caveweb.net/rest/AuthMenagement/user/delete?userId=20
    @DELETE
    @Path("user/delete")
    public Response deleteUser(
            @QueryParam("userId") int userId
    ) {
        try {
            daoFactory.getUtilisateurDao().supprimer(Long.valueOf(userId));
            return Response.ok("{\"status\":\"ok\"}").build();
        } catch (DAOException e) {
            daoFactory.getErreurDao().creer(new Erreur(Long.valueOf(userId), PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
    }


    // http://localhost:8080/webCave/rest/AuthMenagement/user/edit
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("user/edit")
    public Response editProd(String user) throws IOException {
        UtilisateurDao utilisateurDao = daoFactory.getUtilisateurDao();
        UtilisateurAuth utilisateurAuth = new ObjectMapper().readValue(user, UtilisateurAuth.class);
        Long userExistId = utilisateurDao.trouverIdParEmail(utilisateurAuth.getEmail());
        if (userExistId != utilisateurAuth.getId() && userExistId != null) {
            return Response.ok("{\"status\":\"ko\", \"errId\":" + Const.ERR_EMAIL_EXISTS + "}").build();
        } else {
            try {
                daoFactory.getUtilisateurDao().update(utilisateurAuth, false, null);
                return Response.ok("{\"status\":\"ok\"}").build();
            } catch (DAOException e) {
                daoFactory.getErreurDao().creer(new Erreur(utilisateurAuth.getId(), PAGE, e.getMessage()));
                return Response.ok("{\"status\":\"ko\"}").build();
            }
        }

    }
}
