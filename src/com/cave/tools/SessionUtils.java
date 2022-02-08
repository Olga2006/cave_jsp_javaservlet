package com.cave.tools;

import com.cave.beans.*;
import com.cave.dao.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class SessionUtils extends ServiceUtils {

    //private Map<String, Boolean> mapParams = new HashMap<String, Boolean>();

    private static DAOFactory daoFactory;

    private static HttpServletRequest request;

    public static final String PARAM_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";

    public static final String PARAM_PRODUCTEURS_AFFICHE = "producteursAffiche";
    public static final String PARAM_BOUTEILLES_AFFICHE = "bouteillesAffiche";

    public static final String PARAM_MY_OBJECT_LOADER_PRODUCTEUR = "myObjectsLoaderProducteur";
    public static final String PARAM_COMMON_OBJECT_LOADER_PRODUCTEUR = "commonObjectsLoaderProducteur";

    public static final String PARAM_SESSION_BLOGS = "sessionBlogs";
    public static final String ATT_BLOG = "blogs";

    public static final String PARAM_MY_OBJECT_LOADER_BOUTEILLE = "myObjectsLoaderBouteille";
    public static final String PARAM_COMMON_OBJECT_LOADER_BOUTEILLE = "commonObjectsLoaderBouteille";
    public static final String PARAM_TRI_BOUTEILLE_CONSOMER = "triBouteillesConsomer";
    public static final String ATT_TRI_BOUTEILLE_CONSOMER = "triBouteillesConsomer";
    public static final String PARAM_TRI_BOUTEILLE_AFFICHER = "triBouteillesAfficher";
    public static final String ATT_TRI_BOUTEILLE_AFFICHER = "triBouteillesAfficher";

    public static final String ATT_BOUTEILLES_CONSOMER = "bouteillesConsomer";
    public static final String PARAM_MAX_ANEE = "maxAnee";
    public static final String ATT_MAX_ANEE = "maxAnee";

    public static final String ATT_MY_BOUTEILLES_IS_EMPTY = "myBouteillesIsEmpty";

    public static final String ATT_ID_POSITION = "idPositionAficher";

    public static final String ATT_IS_ADMIN = "isAdmin";
    public static final String ATT_IS_ALLOWED_AD = "isAllowedAd";
    public static final String ATT_IN_ALLOWED_AD = "inAllowedAd";
    public static final String ATT_IN_COMMON_LIST = "inCommonList";
    public static final String ATT_IN_FIRST_AD = "inFirstAD";
    public static final String ATT_IN_SECOND_AD = "inSecondAD";
    public static final String ATT_IS_FIRST_AD_FREE = "isFirstADFree";
    public static final String ATT_IS_SECOND_AD_FREE = "isSecondADFree";

    public static final String ATT_LIST_FIRST_AD = "listFirstAD";
    public static final String ATT_LIST_SECOND_AD = "listSecondAD";

    public static final String EMAIL_ADMIN = "olga20reba@gmail.com";
    public static final String ATT_LIST_USERS = "listUsers";

    public SessionUtils(DAOFactory daoFactory, HttpServletRequest request) {
        this.daoFactory = daoFactory;
        this.request = request;
    }

    public void setFullUtilisateur(Utilisateur utilisateur) {
        HttpSession session = request.getSession();
        session.setAttribute(ATT_SESSION_USER, utilisateur);
    }

    public void updateFullUtilisateur() {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getAttribute(PARAM_SESSION_USER);
        Long id_sessionUtilisateur = sessionUtilisateur.getId();
        UtilisateurDao utilisateurDao = daoFactory.getUtilisateurDao();
        Utilisateur sessionUtilisateurMAJ = utilisateurDao.trouver(id_sessionUtilisateur);
        session.setAttribute(ATT_SESSION_USER, sessionUtilisateurMAJ);
    }

    public Utilisateur getFullSessionUtilisateur() {
        HttpSession session = request.getSession();
        return (Utilisateur) session.getAttribute(PARAM_SESSION_USER);
    }

    public void setPersDataUtilisateur(Utilisateur utilisateur) {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);
        if (sessionUtilisateur == null) {
            sessionUtilisateur = new Utilisateur();
            sessionUtilisateur.setBouteilles(new ArrayList<>());
            sessionUtilisateur.setProducteurs(new ArrayList<Producteur>());
            sessionUtilisateur.setCaves(new ArrayList<>());
            sessionUtilisateur.setPubs(new ArrayList<>());
        }
        sessionUtilisateur.setId(utilisateur.getId());
        sessionUtilisateur.setNom(utilisateur.getNom());
        sessionUtilisateur.setEmail(utilisateur.getEmail());
        sessionUtilisateur.setIsWineproducer(utilisateur.getIsWineproducer());
        sessionUtilisateur.setMotDePasse(utilisateur.getMotDePasse());
        sessionUtilisateur.setIsAllowedAD(getNNBoolean(utilisateur.getIsAllowedAD()));
        sessionUtilisateur.setIsPayed(utilisateur.getIsPayed());
        session.setAttribute(ATT_IS_ALLOWED_AD, sessionUtilisateur.getIsAllowedAD());
        session.setAttribute(ATT_SESSION_USER, sessionUtilisateur);
    }

    public Utilisateur getPersDataSessionUtilisateur() {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(sessionUtilisateur.getNom());
        utilisateur.setEmail(sessionUtilisateur.getEmail());
        utilisateur.setIsWineproducer(sessionUtilisateur.getIsWineproducer());
        utilisateur.setMotDePasse(sessionUtilisateur.getMotDePasse());
        utilisateur.setIsAllowedAD(sessionUtilisateur.getIsAllowedAD());
        utilisateur.setIsPayed(sessionUtilisateur.getIsPayed());
        return utilisateur;
    }

    public void updateMyPayedDrois() {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getAttribute(PARAM_SESSION_USER);
        session.setAttribute(ATT_IS_ADMIN, EMAIL_ADMIN.equals(sessionUtilisateur.getEmail()));
        session.setAttribute(ATT_IN_ALLOWED_AD, sessionUtilisateur.getInAllowedAD());
        session.setAttribute(ATT_IN_COMMON_LIST, sessionUtilisateur.getInCommonList());
        session.setAttribute(ATT_IN_FIRST_AD, sessionUtilisateur.getInFirstAD());
        session.setAttribute(ATT_IN_SECOND_AD, sessionUtilisateur.getInSecondAD());

    }

    public void updateMyDroisAD() {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getAttribute(PARAM_SESSION_USER);
        if (sessionUtilisateur.getIsWineproducer()) {
            List<Pub> listPub = sessionUtilisateur.getPubs();
            Boolean isFirstADFree = false;
            Boolean isSecondADFree = false;
            if (sessionUtilisateur.getInFirstAD() != null && sessionUtilisateur.getInFirstAD()) {
                isFirstADFree = listPub.stream().filter(pub -> pub.getIsFirst().equals(true)).collect(Collectors.toList()).isEmpty();
            }
            if (sessionUtilisateur.getInFirstAD() != null && sessionUtilisateur.getInSecondAD()) {
                isSecondADFree = listPub.stream().filter(pub -> pub.getIsSecond().equals(true)).collect(Collectors.toList()).isEmpty();
            }
            session.setAttribute(ATT_IS_FIRST_AD_FREE, isFirstADFree);
            session.setAttribute(ATT_IS_SECOND_AD_FREE, isSecondADFree);
        }
    }

    public Boolean getIsAdmin() {
        return getNNBoolean((Boolean) request.getSession().getAttribute(ATT_IS_ADMIN));
    }

    public Boolean getInAllowedAD() {
        return getNNBoolean((Boolean) request.getSession().getAttribute(ATT_IN_ALLOWED_AD));
    }

    public List<Bouteille> getBouteillesSessionUtilisateur() {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);
        return sessionUtilisateur.getBouteilles();
    }

    public void updateBouteilles() {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);
        Long idSessionUtilisateur = sessionUtilisateur.getId();
        BouteilleDao bouteilleDao = daoFactory.getBouteilleDao();
        List<Bouteille> listBouteille = bouteilleDao.listerPourUtilisateur(idSessionUtilisateur);
        sessionUtilisateur.setBouteilles(listBouteille);
        session.setAttribute(ATT_SESSION_USER, sessionUtilisateur);
    }

    public void updateOnlySessionBouteille(Bouteille bouteille) {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);
        List<Bouteille> listBouteille = sessionUtilisateur.getBouteilles();
        for (Bouteille b : listBouteille) {
            if (b.getId().equals(bouteille.getId()) && bouteille.getCommentaire() != null) {
                b.setCommentaire(bouteille.getCommentaire());
                return;
            } else if (b.getId().equals(bouteille.getId()) && bouteille.getNbrListCourses() != null) {
                b.setNbrListCourses(bouteille.getNbrListCourses());
                return;
            } else if (b.getId().equals(bouteille.getId()) && bouteille.getEvaluation() != null) {
                b.setEvaluation(bouteille.getEvaluation());
                return;
            }
        }

        sessionUtilisateur.setBouteilles(listBouteille);
        session.setAttribute(ATT_SESSION_USER, sessionUtilisateur);
    }

    public void updateBouteillesAffiche() {
        HttpSession session = request.getSession();
        //ServiceUtils serviceUtils = new ServiceUtils();
        Boolean myObjectsLoader = getValeurChampBool(request, PARAM_MY_OBJECT_LOADER_BOUTEILLE);
        Boolean commonObjectsLoader = getValeurChampBool(request, PARAM_COMMON_OBJECT_LOADER_BOUTEILLE);

        Boolean myObjectsLoaderSession = (Boolean) session.getValue(PARAM_MY_OBJECT_LOADER_BOUTEILLE);
        Boolean commonObjectsLoaderSession = (Boolean) session.getValue(PARAM_COMMON_OBJECT_LOADER_BOUTEILLE);

        if (myObjectsLoader == null && myObjectsLoaderSession == null) {
            myObjectsLoader = true;
        } else if (myObjectsLoader == null && myObjectsLoaderSession != null) {
            myObjectsLoader = myObjectsLoaderSession;
        }
        if (!myObjectsLoader.equals(myObjectsLoaderSession)) {
            session.setAttribute(PARAM_MY_OBJECT_LOADER_BOUTEILLE, myObjectsLoader);
        }

        if (commonObjectsLoader == null && commonObjectsLoaderSession == null) {
            commonObjectsLoader = false;
        } else if (commonObjectsLoader == null && commonObjectsLoaderSession != null) {
            commonObjectsLoader = commonObjectsLoaderSession;
        }
        if (!commonObjectsLoader.equals(commonObjectsLoaderSession)) {
            session.setAttribute(PARAM_COMMON_OBJECT_LOADER_BOUTEILLE, commonObjectsLoader);
        }
        Utilisateur sessionUtilisateur = (Utilisateur) session.getAttribute(PARAM_SESSION_USER);
        List<Bouteille> myBouteilles = sessionUtilisateur.getBouteilles();
        if (myBouteilles.isEmpty()) {
            request.setAttribute(ATT_MY_BOUTEILLES_IS_EMPTY, true);
        }
        List<Bouteille> bouteilles = new ArrayList<>();

        if (myObjectsLoader && myBouteilles != null) {
            bouteilles.addAll(myBouteilles);
        }
        if (commonObjectsLoader) {
            BouteilleDao bouteilleDao = daoFactory.getBouteilleDao();
            List<Bouteille> commonBouteilles = bouteilleDao.listerCommon(Const.PAID_TO_COMMON_LIST, sessionUtilisateur.getId());
            commonBouteilles.removeIf(b -> b.getIdUtilisateur().equals(sessionUtilisateur.getId()));

            if (commonBouteilles != null) {
                bouteilles.addAll(commonBouteilles);
            }
        }
        String tri = getValeurChampString(request, PARAM_TRI_BOUTEILLE_AFFICHER);
        if (tri == null) {
            tri = (String) session.getAttribute(PARAM_TRI_BOUTEILLE_AFFICHER);
        }
        if (tri != null && !bouteilles.isEmpty()) {
            session.setAttribute(ATT_TRI_BOUTEILLE_AFFICHER, tri);
            bouteilles = getSortedBouteilles(bouteilles, tri);
        }
        session.setAttribute(PARAM_BOUTEILLES_AFFICHE, bouteilles);
    }

    public void updateBouteillesConsomer() {
        HttpSession session = request.getSession();
        //ServiceUtils serviceUtils = new ServiceUtils();
        List<Bouteille> bouteilles = getBouteillesSessionUtilisateur();
        List<Bouteille> bouteillesConsomer = new ArrayList<>();
        String max_anee;
        Integer maxAnee;
        max_anee = request.getParameter(PARAM_MAX_ANEE);
        if (max_anee == null && session.getAttribute(ATT_MAX_ANEE) != null) {
            maxAnee = (Integer) session.getAttribute(ATT_MAX_ANEE);
        } else if (max_anee != null) {
            try {
                maxAnee = Integer.parseInt(max_anee);
                if (maxAnee < 0) {
                    maxAnee = 0;
                }
            } catch (NumberFormatException e) {
                maxAnee = 3;
            }
        } else {
            maxAnee = 3;
        }

        for (Bouteille bouteilleCurr : bouteilles) {
            if (bouteilleCurr.getNbrAneeABoir() != null && bouteilleCurr.getNbrAneeABoir() <= maxAnee && bouteilleCurr.getPositions() != null) {
                bouteillesConsomer.add(bouteilleCurr);
            }
        }
        session.setAttribute(ATT_MAX_ANEE, maxAnee);

        String tri = getValeurChampString(request, PARAM_TRI_BOUTEILLE_CONSOMER);
        if (tri == null) {
            tri = (String) session.getAttribute(PARAM_TRI_BOUTEILLE_CONSOMER);
        }
        if (tri != null && !bouteilles.isEmpty()) {
            session.setAttribute(ATT_TRI_BOUTEILLE_CONSOMER, tri);
            bouteillesConsomer = getSortedBouteilles(bouteillesConsomer, tri);
        }
        session.setAttribute(ATT_BOUTEILLES_CONSOMER, bouteillesConsomer);
    }

    public List<Bouteille> getSortedBouteilles(List<Bouteille> bouteilles, String tri) {
        switch (tri) {
            case "parNom":
                Collections.sort(bouteilles, Bouteille.ComparNom);
                break;
            case "parPays":
                Collections.sort(bouteilles, Bouteille.ComparPays);
                break;
            case "parRegion":
                Collections.sort(bouteilles, Bouteille.ComparRegion);
                break;
            case "parAppelation":

                Collections.sort(bouteilles, Bouteille.ComparAppelation);
                break;
            case "parCru":
                Collections.sort(bouteilles, Bouteille.ComparCru);
                break;
            case "parCouleur":
                Collections.sort(bouteilles, Bouteille.ComparCouleur);
                break;
            case "parTaille":
                Collections.sort(bouteilles, Bouteille.ComparTaille);
                break;
            case "parPrixAchat":
                Collections.sort(bouteilles, Bouteille.ComparPrixAchat);

                break;
            case "parPrixActuelle":
                Collections.sort(bouteilles, Bouteille.ComparPrixActuelle);
                break;
            case "parDateDeProduction":
                Collections.sort(bouteilles, Bouteille.ComparDateDeProduction);
                break;
            case "parDateGarder":
                Collections.sort(bouteilles, Bouteille.ComparDateGarder);
                break;
            case "triNbrTotal":
                Collections.sort(bouteilles, Bouteille.ComparNbrTotal);
                break;

            default:
                Collections.sort(bouteilles, Bouteille.ComparNom);
                break;
        }
        return bouteilles;
    }

    public void updateProducteurs() {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getAttribute(PARAM_SESSION_USER);
        Long idSessionUtilisateur = sessionUtilisateur.getId();
        ProducteurDao producteurDao = daoFactory.getProducteurDao();
        List<Producteur> listProducteur = producteurDao.listerPourUtilisateur(idSessionUtilisateur);
        sessionUtilisateur.setProducteurs(listProducteur);
        session.setAttribute(ATT_SESSION_USER, sessionUtilisateur);
    }


    public void updateBlogs() {
        HttpSession session = request.getSession();
        BlogDao blogDao = daoFactory.getBlogDao();
        List<Blog> blogs = blogDao.lister();
        session.setAttribute(ATT_BLOG, blogs);
    }

    public List<Producteur> getSessionProducteurs() {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getAttribute(PARAM_SESSION_USER);
        return sessionUtilisateur.getProducteurs();
    }

    public void setSessionProducteurs(List<Producteur> listProducteurs) {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getAttribute(PARAM_SESSION_USER);
        sessionUtilisateur.setProducteurs(listProducteurs);
        session.setAttribute(ATT_SESSION_USER, sessionUtilisateur);
    }

    public void updateProducteursAffiche() {
        HttpSession session = request.getSession();
        //ServiceUtils serviceUtils = new ServiceUtils();
        Boolean myObjectsLoader = getValeurChampBool(request, PARAM_MY_OBJECT_LOADER_PRODUCTEUR);
        Boolean commonObjectsLoader = getValeurChampBool(request, PARAM_COMMON_OBJECT_LOADER_PRODUCTEUR);

        Boolean myObjectsLoaderSession = (Boolean) session.getValue(PARAM_MY_OBJECT_LOADER_PRODUCTEUR);
        Boolean commonObjectsLoaderSession = (Boolean) session.getValue(PARAM_COMMON_OBJECT_LOADER_PRODUCTEUR);

        if (myObjectsLoader == null && myObjectsLoaderSession == null) {
            myObjectsLoader = true;
        } else if (myObjectsLoader == null && myObjectsLoaderSession != null) {
            myObjectsLoader = myObjectsLoaderSession;
        }
        if (!myObjectsLoader.equals(myObjectsLoaderSession)) {
            session.setAttribute(PARAM_MY_OBJECT_LOADER_PRODUCTEUR, myObjectsLoader);
        }

        if (commonObjectsLoader == null && commonObjectsLoaderSession == null) {
            commonObjectsLoader = false;
        } else if (commonObjectsLoader == null && commonObjectsLoaderSession != null) {
            commonObjectsLoader = commonObjectsLoaderSession;
        }
        if (!commonObjectsLoader.equals(commonObjectsLoaderSession)) {
            session.setAttribute(PARAM_COMMON_OBJECT_LOADER_PRODUCTEUR, commonObjectsLoader);
        }
        Utilisateur sessionUtilisateur = (Utilisateur) session.getAttribute(PARAM_SESSION_USER);
        List<Producteur> myProducteurs = sessionUtilisateur.getProducteurs();

        List<Producteur> producteurs = new ArrayList<>();

        if (myObjectsLoader && myProducteurs != null) {
            producteurs.addAll(myProducteurs);
        }
        if (commonObjectsLoader) {
            ProducteurDao producteurDao = daoFactory.getProducteurDao();
            List<Producteur> commonProducteurs = producteurDao.listerCommon(Const.PAID_TO_COMMON_LIST, sessionUtilisateur.getId());
            //commonProducteurs.removeIf(p -> p.getIdUtilisateur().equals(sessionUtilisateur.getId()));
            if (commonProducteurs != null) {
                producteurs.addAll(commonProducteurs);
            }

        }
        session.setAttribute(PARAM_PRODUCTEURS_AFFICHE, producteurs);
    }

    public List<Producteur> getProducteursSessionAffiche() {
        HttpSession session = request.getSession();
        return (List<Producteur>) session.getAttribute(PARAM_PRODUCTEURS_AFFICHE);
    }

    public void updateCaves() {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getValue(PARAM_SESSION_USER);
        Long idSessionUtilisateur = sessionUtilisateur.getId();
        CaveDao caveDao = daoFactory.getCaveDao();
        List<Cave> listCave = new ArrayList<Cave>();
        List<Long> id_caves = caveDao.listerIdCavesPourUtilisateur(idSessionUtilisateur);
        for (Long currIdCave : id_caves) {
            listCave.add(caveDao.trouver(currIdCave));
        }
        sessionUtilisateur.setCaves(listCave);
        session.setAttribute(ATT_SESSION_USER, sessionUtilisateur);
    }

    public void updatePositionAffiche(Long idPosition) {
        HttpSession session = request.getSession();
        session.setAttribute(ATT_ID_POSITION, idPosition);
    }

    //************************For Admin*************************************************

    public void updateListUtilisateurs() {
        HttpSession session = request.getSession();
        UtilisateurDao utilisateurDao = daoFactory.getUtilisateurDao();
        List<UtilisateurShort> listUsers = utilisateurDao.listerPersDataUsers();
        session.setAttribute(ATT_LIST_USERS, listUsers);
    }

    public Utilisateur getUtilisateurFromList(Long id) {
        HttpSession session = request.getSession();
        List<Utilisateur> listUsers = (List<Utilisateur>) session.getValue(ATT_LIST_USERS);
        Utilisateur utilisateur = null;
        if (!listUsers.isEmpty()) {
            for (Utilisateur utilisateurCurr : listUsers) {
                if (utilisateurCurr.getId().equals(id)) {
                    utilisateur = utilisateurCurr;
                    return utilisateur;
                }
            }
        }
        return utilisateur;
    }

    //***************************For Pub***********************************************************
    public void updateUtilisateurPubs() {
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getAttribute(PARAM_SESSION_USER);
        PubDao pubDao = daoFactory.getPubDao();
        List<Pub> listPub = pubDao.listerPourUtilisateur(sessionUtilisateur.getId());
        sessionUtilisateur.setPubs(listPub);
        session.setAttribute(ATT_SESSION_USER, sessionUtilisateur);
    }


    public void updateFirstPubs() {
        HttpSession session = request.getSession();
        PubDao pubDao = daoFactory.getPubDao();
        List<Pub> listPub = pubDao.listerFirst();
        session.setAttribute(ATT_LIST_FIRST_AD, listPub);
    }

    public void updateSecondPubs() {
        HttpSession session = request.getSession();
        PubDao pubDao = daoFactory.getPubDao();
        List<Pub> listPub = pubDao.listerSecond();
        session.setAttribute(ATT_LIST_SECOND_AD, listPub);
    }
}