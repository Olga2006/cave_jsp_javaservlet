package com.cave.servlets;

import com.cave.beans.Blog;
import com.cave.beans.Utilisateur;
import com.cave.dao.BlogDao;
import com.cave.dao.DAOFactory;
import com.cave.forms.BlogForm;
import com.cave.tools.ServiceUtils;
import com.cave.tools.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/creationBlog")
public class CreationBlogByAdmin extends HttpServlet {

    public static final String VUE_SUCCES = "/blog";
    public static final String VUE_FORM_ADMIN = "/WEB-INF/jsp/public/afficherBlog.jsp";

    public static final String ACCES_CONNEXION = "/connection";

    public static final String PARAM_SESSION_USER = "sessionUtilisateur";
    public static final String PARAM_ID_BLOG = "idBlog";

    public static final String ATT_BLOG = "blog";
    public static final String ATT_FORM = "form";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private BlogDao blogDao;

    private ServiceUtils serviceUtils;

    public void init() throws ServletException {
        this.blogDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getBlogDao();
        this.serviceUtils = new ServiceUtils();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);
        Utilisateur sessionUtilisateur = sessionUtils.getFullSessionUtilisateur();

        Long idBlog = serviceUtils.getValeurChampLong(request, PARAM_ID_BLOG);
        if (sessionUtilisateur.getEmail().equals("olga20reba@gmail.com") && idBlog != null && sessionUtilisateur != null) {
            Blog blog = blogDao.trouver(idBlog);
            request.setAttribute(ATT_BLOG, blog);
            this.getServletContext().getRequestDispatcher(VUE_FORM_ADMIN).forward(request, response);
        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(
                    request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUtils sessionUtils = new SessionUtils((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY), request);

        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);
        Long idBlog = serviceUtils.getValeurChampLong(request, PARAM_ID_BLOG);
        if (sessionUtilisateur != null && sessionUtilisateur.getEmail().equals("olga20reba@gmail.com")) {
            BlogForm form = new BlogForm(blogDao, serviceUtils);
            Blog blog;
            if (idBlog != null) {
                blog = form.updateBlog(request);
            } else {
                blog = form.creerBlog(request);
            }
            if (!form.getErreurs().isEmpty()) {
                request.setAttribute(ATT_BLOG, blog);
            } else {
                sessionUtils.updateBlogs();
            }
            request.setAttribute(ATT_FORM, form);
            this.getServletContext().getRequestDispatcher(VUE_FORM_ADMIN).forward(request, response);
        } else
            this.getServletContext().getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
    }
}