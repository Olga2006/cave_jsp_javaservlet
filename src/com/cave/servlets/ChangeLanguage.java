package com.cave.servlets;

import com.cave.tools.ServiceUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/changerLanguage")
public class ChangeLanguage extends HttpServlet {

    public static final String PARAM_LANGUAGE = "language";
    public static final String PARAM_LOCATION_HREF = "locationhref";
    public static final String ATT_LANGUAGE = "language";

    private ServiceUtils serviceUtils;

    public void init() throws ServletException {
        this.serviceUtils = new ServiceUtils();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String language = serviceUtils.getValeurChampString(request, PARAM_LANGUAGE);
        String locationhref = serviceUtils.getValeurChampString(request, PARAM_LOCATION_HREF);
        HttpSession session = request.getSession();
        session.setAttribute(ATT_LANGUAGE, language);
        response.sendRedirect(locationhref);
    }
}