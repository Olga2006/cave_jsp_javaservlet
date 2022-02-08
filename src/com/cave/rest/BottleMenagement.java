
package com.cave.rest;

import com.cave.beans.*;
import com.cave.dao.DAOException;
import com.cave.dao.DAOFactory;
import com.cave.tools.AWSUtils;
import com.cave.tools.Const;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.List;


@Path("/BottleMenagement")
public class BottleMenagement extends HttpServlet {

    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String PAGE = "BottleMenagement";

    private DAOFactory dao_Factory = new DAOFactory();
    private DAOFactory daoFactory = dao_Factory.getInstance();

    // http://localhost:8080/webCave/rest/BottleMenagement/bottles?userId=20
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("bottles")
    public Response getBottlesOfUser(
            @QueryParam("userId") int userId
    ) {
        List<Bouteille> listBouteilles;
        try {
            listBouteilles = daoFactory.getBouteilleDao().listerPourUtilisateur(Long.valueOf(userId));
        } catch (DAOException e) {
            daoFactory.getErreurDao().creer(new Erreur(Long.valueOf(userId), PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String listBouteillesJson;
        try {
            listBouteillesJson = mapper.writeValueAsString(listBouteilles);
        } catch (JsonProcessingException e) {
            daoFactory.getErreurDao().creer(new Erreur(Long.valueOf(userId), PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
        return Response.ok("{\"status\":\"ok\",\"listBouteilles\":" + listBouteillesJson + "}").build();
    }

    // http://localhost:8080/webCave/rest/BottleMenagement/commonBottles?userId=20
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("commonBottles")
    public Response getCommonBottles(
            @QueryParam("userId") int userId
    ) {
        List<Bouteille> commonBouteilles;
        try {
            commonBouteilles = daoFactory.getBouteilleDao().listerCommon(Const.PAID_TO_COMMON_LIST, Long.valueOf(userId));
        } catch (DAOException e) {
            daoFactory.getErreurDao().creer(new Erreur(Long.valueOf(userId), PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String listBouteillesJson;
        try {
            listBouteillesJson = mapper.writeValueAsString(commonBouteilles);
        } catch (JsonProcessingException e) {
            daoFactory.getErreurDao().creer(new Erreur(Long.valueOf(userId), PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
        return Response.ok("{\"status\":\"ok\",\"listBouteilles\":" + listBouteillesJson + "}").build();
    }

    // http://localhost:8080/webCave/rest/BottleMenagement/bottlesofprod?userId=20&prodId=72
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("bottlesofprod")
    public Response getBottlesOfProducer(
            @QueryParam("userId") int userId,
            @QueryParam("prodId") int prodId
    ) {
        List<BouteilleOfProducer> bouteilles;
        try {
            bouteilles = daoFactory.getBouteilleDao().listerPourProducteurShort(Long.valueOf(prodId));
        } catch (DAOException e) {
            daoFactory.getErreurDao().creer(new Erreur(Long.valueOf(userId), PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String listBouteillesJson;
        try {
            listBouteillesJson = mapper.writeValueAsString(bouteilles);
        } catch (JsonProcessingException e) {
            daoFactory.getErreurDao().creer(new Erreur(Long.valueOf(userId), PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
        return Response.ok("{\"status\":\"ok\",\"listBouteilles\":" + listBouteillesJson + "}").build();
    }

    // http://localhost:8080/webCave/rest/BottleMenagement/bout/delete?userId=20&boutId=72
    @DELETE
    @Path("bottle/delete")
    public Response deleteBout(
            @QueryParam("userId") int userId,
            @QueryParam("bottleId") int bottleId,
            @QueryParam("imgToDel") String imgToDel
    ) {
        try {
            if (imgToDel != null) {
                new AWSUtils().deleteImage(imgToDel, daoFactory.getErreurDao(), Long.valueOf(userId), PAGE);
            }
            daoFactory.getBouteilleDao().supprimer(Long.valueOf(bottleId));
            return Response.ok("{\"status\":\"ok\"}").build();
        } catch (DAOException e) {
            daoFactory.getErreurDao().creer(new Erreur(Long.valueOf(userId), PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
    }

    // http://localhost:8080/webCave/rest/BottleMenagement/bottle/post?userId=20
    @POST
    @Path("bottle/post")
    @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
    public Response addBout(
            @QueryParam("userId") int userId,
            String bottle) throws IOException {

        String bottleObj = bottle.replaceFirst("BottlePost", "").replace(",\n}", "\n}");

        BouteillePost bouteillePost = new ObjectMapper().readValue(bottleObj, BouteillePost.class);
        try {
            Long bouteilleId = daoFactory.getBouteilleDao().creerPourUtilisateur(bouteillePost);
            return Response.ok("{\"status\":\"ok\",\"bottleId\":" + bouteilleId + "}").build();
        } catch (DAOException e) {
            daoFactory.getErreurDao().creer(new Erreur(Long.valueOf(userId), PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
    }

    // http://www.caveweb.net/rest/BottleMenagement/bottle/copy?userId=${userId}&fromId=${fromId}
    @POST
    @Path("bottle/copy")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response copyBouteille(
            @QueryParam("userId") Long userId,
            @QueryParam("fromId") Long fromId
    ) throws IOException {

        try {
            Bouteille bouteilleForCopy = daoFactory.getBouteilleDao().trouver(Long.valueOf(fromId));
            bouteilleForCopy.setPrixAchat(null);
            bouteilleForCopy.setIdUtilisateur(Long.valueOf(userId));
            bouteilleForCopy.setNbrListCourses(0);
            bouteilleForCopy.setCommentaire(null);
            bouteilleForCopy.setIsAllowedCL(true);

            ProducteurShort newProducteur = null;
            if (bouteilleForCopy.getIdProducteur() != null) {
                bouteilleForCopy.setProducteur(daoFactory.getProducteurDao().trouver(bouteilleForCopy.getIdProducteur()));
            }
            List<Bouteille> listTheSameBouteillesOfUser = daoFactory.getBouteilleDao().listerTheSameBouteillesOfUser(Long.valueOf(userId), bouteilleForCopy);
            if (listTheSameBouteillesOfUser.isEmpty()) {
                if (bouteilleForCopy.getProducteur() != null) {
                    Long idTheSameProducteur = daoFactory.getProducteurDao().getIdTheSameProdOfUser(Long.valueOf(userId), bouteilleForCopy.getProducteur());
                    if (idTheSameProducteur != null) {
                        bouteilleForCopy.setIdProducteur(idTheSameProducteur);
                    } else if (bouteilleForCopy.getProducteur().getIsAllowedCL()) {
                        Long idNewProducteur = daoFactory.getProducteurDao().copyFromProducteur(Long.valueOf(userId), bouteilleForCopy.getProducteur().getId());
                        bouteilleForCopy.setIdProducteur(idNewProducteur);
                        newProducteur = new ProducteurShort(idNewProducteur,
                                bouteilleForCopy.getProducteur().getNom(),
                                bouteilleForCopy.getProducteur().getAdresse(),
                                bouteilleForCopy.getProducteur().getContact(),
                                Long.valueOf(userId),
                                bouteilleForCopy.getProducteur().getUrl(),
                                true
                        );
                    }
                    bouteilleForCopy.setNomProducteur(bouteilleForCopy.getProducteur().getNom());
                }
                bouteilleForCopy.setProducteur(null);
                if (bouteilleForCopy.getImage() != null) {
                    String newImage = bouteilleForCopy.getImage().split("/")[0] + "/" + userId.toString() + "_" + bouteilleForCopy.getImage().split("/")[1];
                    Boolean isCopyed = new AWSUtils().CopyImage(bouteilleForCopy.getImage(), newImage, daoFactory.getErreurDao(), Long.valueOf(userId), PAGE);
                    if (isCopyed) {
                        bouteilleForCopy.setImage(newImage);
                    }
                }


                Bouteille newBouteille = daoFactory.getBouteilleDao().creerPourUtilisateur(bouteilleForCopy);
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                String newBouteilleJson;
                String newProducteurJson;
                try {
                    newBouteilleJson = mapper.writeValueAsString(newBouteille);
                    newProducteurJson = mapper.writeValueAsString(newProducteur);
                } catch (JsonProcessingException e) {
                    daoFactory.getErreurDao().creer(new Erreur(Long.valueOf(userId), PAGE, e.getMessage()));
                    return Response.ok("{\"status\":\"ko\"}").build();
                }
                String entity = "{\"status\":\"ok\",\"producerData\":" + newProducteurJson + ",\"bottleData\":" + newBouteilleJson + "}";
                return Response.ok(entity).build();
            } else {
                return Response.ok("{\"status\":\"isExist\"}").build();
            }
        } catch (DAOException e) {
            daoFactory.getErreurDao().creer(new Erreur(Long.valueOf(userId), PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
    }

    // http://localhost:8080/webCave/rest/BottleMenagement/bout/edit?userId=20
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("bottle/edit")
    public Response editBout(@QueryParam("userId") int userId,
                             @QueryParam("imgToDel") String imgToDel,
                             String bottle) throws IOException {
        String bottleObj = bottle.replaceFirst("BottlePost", "").replace(",\n}", "\n}");
        BouteillePost bouteillePost = new ObjectMapper().readValue(bottleObj, BouteillePost.class);
        try {
            daoFactory.getBouteilleDao().update(bouteillePost);
            if (imgToDel != null) {
                new AWSUtils().deleteImage(imgToDel, daoFactory.getErreurDao(), Long.valueOf(userId), PAGE);
            }
            return Response.ok("{\"status\":\"ok\"}").build();
        } catch (DAOException e) {
            daoFactory.getErreurDao().creer(new Erreur(Long.valueOf(userId), PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
    }
}
