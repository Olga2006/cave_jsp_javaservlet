package com.cave.servlets;

import com.cave.beans.Utilisateur;
import com.cave.tools.ServiceUtils;
import com.cave.tools.WriteExcel;
import jxl.write.WriteException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URISyntaxException;

@WebServlet("/creationXml")
public class CreationXml extends HttpServlet {
    public static final String VUE_FORM_PRODUCTEURS = "/listeProducteurs";
    public static final String VUE_FORM_BOUTEILLES = "/listeBouteilles";
    public static final String VUE_FORM_CAVES = "/WEB-INF/jsp/restreint/afficherCaves.jsp";
    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_OBJ_XML = "objectxml";
    public static final String PARAM_HEADER = "header";

    public static final String PARAM_SESSION_USER = "sessionUtilisateur";

    public static final String ATT_PATH = "path";
    private ServiceUtils serviceUtils;


    public void init() throws ServletException {
        this.serviceUtils = new ServiceUtils();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);
        String objectxml = serviceUtils.getValeurChampString(request, PARAM_OBJ_XML);
        String header = serviceUtils.getValeurChampString(request, PARAM_HEADER);
        String[] headerArray = header.split(";");
        String path = null;
        if (sessionUtilisateur != null) {
            WriteExcel writeExcel = new WriteExcel();
            try {
                path = writeExcel.write(sessionUtilisateur, objectxml, headerArray);
            } catch (WriteException | URISyntaxException e) {
                e.printStackTrace();
            }
            request.setAttribute(ATT_PATH, path);
            switch (objectxml) {
                case "Producteurs":
                    this.getServletContext().getRequestDispatcher(VUE_FORM_PRODUCTEURS).forward(request, response);
                    break;
                case "Bouteilles":
                    this.getServletContext().getRequestDispatcher(VUE_FORM_BOUTEILLES).forward(request, response);
                    break;
                case "Caves":
                case "All":
                default:
                    this.getServletContext().getRequestDispatcher(VUE_FORM_CAVES).forward(request, response);
                    break;
            }
        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }
}