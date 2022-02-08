package com.cave.forms;

import com.cave.beans.Blog;
import com.cave.dao.BlogDao;
import com.cave.dao.DAOException;
import com.cave.tools.ServiceUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public final class BlogForm {


    private static final String CHAMP_ID = "idBlog";
    private static final String CHAMP_NOM_ARTICLE = "nomArticle";
    private static final String CHAMP_SUBTHEME = "subtheme";
    private static final String CHAMP_DATE_EDITION = "dateEdition";
    private static final String CHAMP_NOM_AUTEUR = "nomAuteur";
    private static final String CHAMP_ARTICLE1 = "article1";
    private static final String CHAMP_ARTICLE2 = "article2";
    private static final String CHAMP_ARTICLE3 = "article3";

    private static final String PARAM_IMAGE = "image";
    private static final String CHAMP_ERREUR_DAO = "erreurDao";

    private String successMaj;
    private String successCreation;
    private Map<String, String> erreurs = new HashMap<String, String>();

    private BlogDao blogDao;
    private ServiceUtils serviceUtils;

    public BlogForm(BlogDao blogDao, ServiceUtils serviceUtils) {
        this.blogDao = blogDao;
        this.serviceUtils = serviceUtils;
    }

    public BlogForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getSuccessCreation() {
        return successCreation;
    }

    public String getSuccessMaj() {
        return successMaj;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public Blog mapBlog(HttpServletRequest request) {
        Long id = serviceUtils.getValeurChampLong(request, CHAMP_ID);
        String nomArticle = serviceUtils.getValeurChampString(request, CHAMP_NOM_ARTICLE);
        String subtheme = serviceUtils.getValeurChampString(request, CHAMP_SUBTHEME);
        Timestamp date_Edition = serviceUtils.getValeurChampTimestamp(request, CHAMP_DATE_EDITION);
        String nomAuteur = serviceUtils.getValeurChampString(request, CHAMP_NOM_AUTEUR);
        String article1 = serviceUtils.getValeurChampString(request, CHAMP_ARTICLE1);
        String article2 = serviceUtils.getValeurChampString(request, CHAMP_ARTICLE2);
        String article3 = serviceUtils.getValeurChampString(request, CHAMP_ARTICLE3);
        String image = serviceUtils.getValeurChampString(request, PARAM_IMAGE);
        Timestamp dateEdition;
        if (date_Edition == null) {
            dateEdition = new Timestamp(System.currentTimeMillis());
        } else dateEdition = date_Edition;

        Blog blog = new Blog(id, nomArticle, subtheme, dateEdition, nomAuteur, article1, article2, article3, image);
        return blog;
    }

    public Blog creerBlog(HttpServletRequest request) {
        Blog blog = mapBlog(request);
        try {
            blogDao.creer(blog);
            successCreation = "Succès de creation blog." + blog.getNomArticle();
        } catch (DAOException e) {
            setErreur(CHAMP_ERREUR_DAO, e.getMessage());
            e.printStackTrace();
        }

        return blog;
    }

    public Blog updateBlog(HttpServletRequest request) {
        Blog blog = mapBlog(request);
        try {
            blogDao.update(blog);
            successMaj = " " + blog.getNomArticle();
        } catch (DAOException e) {
            setErreur(CHAMP_ERREUR_DAO, e.getMessage());
            e.printStackTrace();
        }
        return blog;
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }

}