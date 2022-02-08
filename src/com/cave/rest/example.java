/*
package com.atolcd.atrium.task.suivivirement;

import com.atolcd.atrium.annotation.UserPortefeuille;
import com.atolcd.atrium.model.infoperso.FullExtranaute;
import com.atolcd.atrium.model.suivivirement.Benef;
import com.atolcd.atrium.model.suivivirement.PostBenef;
import com.atolcd.atrium.model.suivivirement.PostVirement;
import com.atolcd.atrium.model.suivivirement.VirementCsv;
import com.atolcd.atrium.repository.jooq.SuiviVirementRepository;
import com.atolcd.atrium.repository.jooq.UsersRepository;
import com.atolcd.atrium.service.CSVRenderer;
import com.atolcd.atrium.service.MediaTypeUtils;
import com.atolcd.atrium.service.RoleUtils;
import com.atolcd.atrium.util.ServiceUtils;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/suivivirement/{servId}")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(RoleUtils.CONSULT)
public class SuiviVirement {

    private final ServiceUtils serviceUtils;
    private final SuiviVirementRepository suiviVirementRepository;
    private final SuiviVirementTool suiviVirementTool;
    private final FullExtranaute user;

    @Inject
    public SuiviVirement(
            ServiceUtils serviceUtils,
            SuiviVirementRepository suiviVirementRepository, SuiviVirementTool suiviVirementTool, UsersRepository usersRepository,
            @UserPortefeuille Integer userId
    ) {
        this.serviceUtils = serviceUtils;
        this.suiviVirementRepository = suiviVirementRepository;
        this.suiviVirementTool = suiviVirementTool;
        this.user = usersRepository.getFullExtrById(userId);
    }

    @Path("")
    public Response getInitData(
            @PathParam("servId") int servId
    ) {
        return Response.ok(Map.of(
                "servParams", serviceUtils.getServiceInstanceParams(servId)
        )).build();
    }

    @Path("etatsVirement")
    public Response getEtatsVirement(
            @PathParam("servId") int servId
    ) {
        return Response.ok(
                Map.of("listEtatsVirement", suiviVirementRepository.getEtatsVirement())
        ).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("virements")
    public Response getVirements(
            @PathParam("servId") int servId,
            @QueryParam("etatId") int etatId
    ) {
        return Response.ok(Map.of("virements", suiviVirementRepository.getListVirementsByPersonne(servId, user, etatId)
        )).build();
    }

    @GET
    @Path("virements/export")
    @Produces(MediaTypeUtils.TEXT_CSV)
    public Response exportVirementsCsv(
            @PathParam("servId") int servId,
            @QueryParam("fromDate") LocalDateTime fromDate,
            @QueryParam("tillDate") LocalDateTime tillDate
    ) {
        List<VirementCsv> listVirementCSV = suiviVirementRepository.getVirementsCSVByPersonne(servId, user, fromDate.atOffset(ZoneId.of("Europe/Paris").getRules().getOffset(fromDate)), tillDate.atOffset(ZoneId.of("Europe/Paris").getRules().getOffset(tillDate)));
        return Response.ok(listVirementCSV.isEmpty() ? CSVRenderer.emptyForClass(VirementCsv.class) : listVirementCSV).build();
    }

    @GET
    @Path("benefsenefs")
    public Response getBenefs(
            @PathParam("servId") int servId
    ) {
        return Response.ok(Map.of("benefs", suiviVirementTool.getListBenefs(servId, user))).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("benef/edit")
    public Response editBenef(
            @PathParam("servId") Integer servId, Benef benef
    ) {
        return Response.ok(Map.of("message", suiviVirementTool.validateEditBenef(user, benef))).build();
    }

    @DELETE
    @Path("benef/delete/{benefId}")
    public Response deleteBenef(
            @PathParam("servId") int servId,
            @PathParam("benefId") UUID benefId
    ) {
        suiviVirementRepository.deactivateBenef(benefId);
        return Response.ok().build();
    }

    @POST
    @Path("benef/validate")
    public Response validateAddBenef(
            @PathParam("servId") int servId, PostBenef postBenef
    ) {
        PostVirement postVirement = null;
        String validMsg = suiviVirementTool.sendMailConfirm(servId, postVirement, 10, user) ? "withMail" : "noMail";
        return Response.ok(Map.of("validMsg", validMsg)).build();
    }

    @POST
    @Path("benef/post")
    public Response addValidatedBenef(
            @PathParam("servId") int servId, PostBenef postBenef
    ) {
        PostVirement postVirement = null;
        String validMsg = suiviVirementTool.sendMailConfirm(servId, postVirement, 10, user) ? "withMail" : "noMail";
        return Response.ok(Map.of("validMsg", validMsg)).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("comptes")
    public Response getComptes(
            @PathParam("servId") int servId
    ) {
        return Response.ok(Map.of("comptes", suiviVirementRepository.getListComptesByPersonne(servId, user),
                "comptesCible", suiviVirementRepository.getListComptesCibleByPersonne(servId, user)
        )).build();
    }

    @POST
    @Path("virement/post")
    public Response addVirement(
            @PathParam("servId") int servId,
            PostVirement postVirement
    ) {
        Integer virementId = suiviVirementRepository.insertVirement(postVirement, user);
        String validMsg = suiviVirementTool.sendMailConfirm(servId, postVirement, virementId, user) ? "withMail" : "noMail";
        return Response.ok(Map.of("validMsg", validMsg)).build();
    }

}*/
