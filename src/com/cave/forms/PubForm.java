package com.cave.forms;

import com.cave.beans.Pub;
import com.cave.beans.Utilisateur;
import com.cave.dao.DAOException;
import com.cave.dao.PubDao;
import com.cave.tools.ServiceUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public final class PubForm {


    private static final String CHAMP_ID = "idPub";
    private static final String CHAMP_CODE_HTML = "codeHtml";
    private static final String CHAMP_URL = "url";
    private static final String CHAMP_IS_FIRST = "isFirst";
    private static final String CHAMP_IS_SECOND = "isSecond";

    private static final String PARAM_IMAGE = "image";
    private static final String CHAMP_ERREUR_DAO = "erreurDao";

    private String successMaj;
    private String successCreation;
    private Map<String, String> erreurs = new HashMap<String, String>();

    private PubDao pubDao;
    private ServiceUtils serviceUtils;

    public PubForm(PubDao pubDao, ServiceUtils serviceUtils) {
        this.pubDao = pubDao;
        this.serviceUtils = serviceUtils;
    }

    public PubForm() {
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

    public Pub mapPub(HttpServletRequest request, Utilisateur sessionUtilisateur) {
        Long idPub = serviceUtils.getValeurChampLong(request, CHAMP_ID);
        String codeHtml = serviceUtils.getValeurChampString(request, CHAMP_CODE_HTML);
        String url = serviceUtils.getValeurChampString(request, CHAMP_URL);
        Boolean isFirst = serviceUtils.getValeurChampBoolNoNullable(request, CHAMP_IS_FIRST);
        Boolean isSecond = serviceUtils.getValeurChampBoolNoNullable(request, CHAMP_IS_SECOND);
        String image = serviceUtils.getValeurChampString(request, PARAM_IMAGE);
        //isFirst = isFirst == null || isFirst == true ? false : true;
        //isSecond = isSecond == null || isSecond == true ? false : true;
        Pub pub = new Pub(idPub, sessionUtilisateur.getId(), codeHtml, image, url, isFirst, isSecond);

        return pub;
    }

    public Pub creerPub(HttpServletRequest request, Utilisateur sessionUtilisateur) {
        Pub pub = mapPub(request, sessionUtilisateur);
        try {
            pubDao.creer(pub);
            successCreation = "Succès de creation pub.";
        } catch (DAOException e) {
            setErreur(CHAMP_ERREUR_DAO, e.getMessage());
            e.printStackTrace();
        }
        return pub;
    }

    public Pub updatePub(HttpServletRequest request, Utilisateur sessionUtilisateur) {

        Pub pub = mapPub(request, sessionUtilisateur);
        try {
            pubDao.update(pub);
            successMaj = "Succès de maj pub.";
        } catch (DAOException e) {
            setErreur(CHAMP_ERREUR_DAO, e.getMessage());
            e.printStackTrace();
        }
        return pub;
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }

}