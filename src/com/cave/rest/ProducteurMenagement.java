
package com.cave.rest;

import com.cave.beans.Erreur;
import com.cave.beans.ProducteurShort;
import com.cave.dao.DAOException;
import com.cave.dao.DAOFactory;
import com.cave.tools.Const;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/ProducteurMenagement")
public class ProducteurMenagement extends HttpServlet {

    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String PAGE = "ProducteurMenagement";

    private DAOFactory dao_Factory = new DAOFactory();
    private DAOFactory daoFactory = dao_Factory.getInstance();

    // http://localhost:8080/webCave/rest/ProducteurMenagement/prods?userId=20
    // http://www.caveweb.net/rest/ProducteurMenagement/prods?userId=20

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("prods")
    public Response getProducteurs(
            @QueryParam("userId") Long userId
    ) {
        List<ProducteurShort> listProducteurs;
        try {
            listProducteurs = daoFactory.getProducteurDao().listerShortPourUtilisateur(userId);
        } catch (DAOException e) {
            daoFactory.getErreurDao().creer(new Erreur(userId, PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String listProducteursJson;
        try {
            listProducteursJson = mapper.writeValueAsString(listProducteurs);
        } catch (JsonProcessingException e) {
            daoFactory.getErreurDao().creer(new Erreur(userId, PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
        return Response
                .ok("{\"status\":\"ok\",\"listProducteurs\":" + listProducteursJson + "}")
                .build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("commonProds")
    public Response getCommonProducteurs(
            @QueryParam("userId") Long userId
    ) {
        List<ProducteurShort> listProducteurs;
        try {
            listProducteurs = daoFactory.getProducteurDao().listerCommonShort(Const.PAID_TO_COMMON_LIST, userId);
        } catch (DAOException e) {
            daoFactory.getErreurDao().creer(new Erreur(userId, PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String listProducteursJson;
        try {
            listProducteursJson = mapper.writeValueAsString(listProducteurs);
        } catch (JsonProcessingException e) {
            daoFactory.getErreurDao().creer(new Erreur(userId, PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
        return Response.ok("{\"status\":\"ok\",\"listProducteurs\":" + listProducteursJson + "}").build();
    }

    // http://localhost:8080/webCave/rest/ProducteurMenagement/prod/delete?userId=20&prodId=72
    @DELETE
    @Path("prod/delete")
    public Response deleteProd(
            @QueryParam("userId") Long userId,
            @QueryParam("prodId") Long prodId
    ) {
        try {
            daoFactory.getProducteurDao().supprimer(Long.valueOf(prodId));
            return Response.ok("{\"status\":\"ok\"}").build();
        } catch (DAOException e) {
            daoFactory.getErreurDao().creer(new Erreur(Long.valueOf(userId), PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
    }

    // http://localhost:8080/webCave/rest/ProducteurMenagement/prod/post?userId=20
    @POST
    @Path("prod/post")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addProd(
            @QueryParam("userId") int userId,
            String prod) throws IOException {
        try {
            Long producteurId = daoFactory.getProducteurDao().creerPourUtilisateur(new ObjectMapper().readValue(prod, ProducteurShort.class));
            return Response.ok("{\"status\":\"ok\",\"producteurId\":" + producteurId + "}").build();
        } catch (DAOException e) {
            daoFactory.getErreurDao().creer(new Erreur(Long.valueOf(userId), PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
    }

    // http://localhost:8080/webCave/rest/ProducteurMenagement/prod/copy?userId=20&fromId=14
    @POST
    @Path("prod/copy")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response copyProd(
            @QueryParam("userId") Long userId,
            @QueryParam("fromId") Long fromId
    ) throws IOException {
        try {
            Long producteurId = daoFactory.getProducteurDao().copyFromProducteur(userId, fromId);
            return Response.ok("{\"status\":\"ok\",\"producteurId\":" + producteurId + "}").build();
        } catch (DAOException e) {
            daoFactory.getErreurDao().creer(new Erreur(userId, PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
    }

    // http://localhost:8080/webCave/rest/ProducteurMenagement/prod/edit?userId=20
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("prod/edit")
    public Response editProd(@QueryParam("userId") Long userId, String producer) throws IOException {
        try {
            daoFactory.getProducteurDao().update(new ObjectMapper().readValue(producer, ProducteurShort.class));
            return Response.ok("{\"status\":\"ok\"}").build();
        } catch (DAOException e) {
            daoFactory.getErreurDao().creer(new Erreur(userId, PAGE, e.getMessage()));
            return Response.ok("{\"status\":\"ko\"}").build();
        }
    }
}
