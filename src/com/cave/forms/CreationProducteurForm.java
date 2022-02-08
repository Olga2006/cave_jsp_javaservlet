package com.cave.forms;

import com.cave.beans.Producteur;
import com.cave.beans.Utilisateur;
import com.cave.dao.DAOException;
import com.cave.dao.ProducteurDao;
import com.cave.tools.SessionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CreationProducteurForm {
    private static final String CHAMP_NOM = "nomP";
    private static final String CHAMP_PRODUCTEUR_EXISTE = "producteurExiste";
    private static final String CHAMP_ADRESSE = "adresse";
    private static final String CHAMP_CONTACT = "contact";
    private static final String CHAMP_IS_ALLOWED_CL = "isAllowedCLProd";
    private static final String CHAMP_URL = "url";
    private static final String CHAMP_ERREUR_DAO = "erreurDaoProd";

    public static final String PARAM_ID_PRODUCTEUR = "idProducteur";

    private String successCreation;
    private String successMaj;

    private String unsuccessCreation;
    private String unsuccessMaj;
    //private Long idTheSameProducteur;

    private Map<String, String> erreurs = new HashMap<String, String>();
    ProducteurDao producteurDao;
    private SessionUtils sessionUtils;

    public CreationProducteurForm(ProducteurDao producteurDao, SessionUtils sessionUtils) {
        this.producteurDao = producteurDao;
        this.sessionUtils = sessionUtils;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getSuccessCreation() {
        return successCreation;
    }

    public String getSuccessMaj() {
        return successMaj;
    }

    public String getUnsuccessCreation() {
        return unsuccessCreation;
    }

    public String getUnsuccessMaj() {
        return unsuccessMaj;
    }

 /*   public Long getIdTheSameProducteur() {
        return idTheSameProducteur;
    }*/

    public Producteur mapProducteur(HttpServletRequest request, Utilisateur sessionUtilisateur) {
        Producteur producteur = new Producteur();
        Long id = sessionUtils.getValeurChampLong(request, PARAM_ID_PRODUCTEUR);
        String nom = sessionUtils.getValeurChampString(request, CHAMP_NOM);
        String adresse = sessionUtils.getValeurChampString(request, CHAMP_ADRESSE);
        String contact = sessionUtils.getValeurChampString(request, CHAMP_CONTACT);
        String url = sessionUtils.getValeurChampString(request, CHAMP_URL);
        Boolean isAllowedCL = sessionUtils.getValeurChampBoolNoNullable(request, CHAMP_IS_ALLOWED_CL);
        producteur.setIsAllowedCL(isAllowedCL);
        producteur.setId(id);
        producteur.setIdUtilisateur(sessionUtilisateur.getId());
        producteur.setNom(nom);
        producteur.setAdresse(adresse);
        producteur.setContact(contact);
        producteur.setUrl(url);
        traiterExistenceProducteur(producteur);
        return producteur;
    }

    public Producteur copyProducteurPourUtilisateur(Long idUtilisateur, Long idProducteur) {
        Producteur producteur = producteurDao.trouver(idProducteur);
        if (producteur == null || !producteur.getIsAllowedCL()) {
            return null;
        }
        producteur.setIdUtilisateur(idUtilisateur);
        producteur.setId(null);
        producteur.setIsAllowedCL(true);
        producteur = traiterExistenceProducteur(producteur);
        if (erreurs.isEmpty()) {
            try {
                producteur = producteurDao.creerPourUtilisateur(producteur);
                successCreation = " " + producteur.getNom();
            } catch (DAOException e) {
                setErreur(CHAMP_ERREUR_DAO, e.getMessage());
                e.printStackTrace();
                unsuccessCreation = " " + producteur.getNom();
            }
        } else {
            unsuccessCreation = " " + producteur.getNom();
        }
        return producteur;
    }

    public Producteur creerProducteurPourUtilisateur(HttpServletRequest request, Utilisateur sessionUtilisateur) {
        Producteur producteur = mapProducteur(request, sessionUtilisateur);

        if (erreurs.isEmpty()) {
            try {
                producteur = producteurDao.creerPourUtilisateur(producteur);
                successCreation = " " + producteur.getNom();
            } catch (DAOException e) {
                setErreur(CHAMP_ERREUR_DAO, e.getMessage());
                e.printStackTrace();
                unsuccessCreation = " " + producteur.getNom();
            }
        } else {
            unsuccessCreation = " " + producteur.getNom();
        }

        return producteur;
    }

    public Producteur updateProducteur(HttpServletRequest request, Utilisateur sessionUtilisateur) {
        Producteur producteur = mapProducteur(request, sessionUtilisateur);
        if (erreurs.isEmpty()) {
            try {
                producteurDao.update(producteur);
                successMaj = " " + producteur.getNom();
            } catch (DAOException e) {
                setErreur(CHAMP_ERREUR_DAO, e.getMessage());
                e.printStackTrace();
                unsuccessMaj = " " + producteur.getNom();
            }
        } else {
            unsuccessMaj = " " + producteur.getNom();
        }

        return producteur;
    }

    private Producteur traiterExistenceProducteur(Producteur producteur) {
        //Producteur producteurExs = null;
        List<Producteur> listSessionProducteurs = sessionUtils.getSessionProducteurs();
        Producteur producteurDansList = null;
        for (Producteur producteurCurr : listSessionProducteurs) {
            if (producteurCurr.getNom().equals(producteur.getNom()) &&
                    producteurCurr.getAdresse().equals(producteur.getAdresse()) &&
                    producteurCurr.getContact().equals(producteur.getContact())
            ) {
                producteurDansList = producteurCurr;
            }
        }
        if (producteurDansList != null && !producteurDansList.getId().equals(producteur.getId())) {
            producteur = producteurDansList;
            setErreur(CHAMP_PRODUCTEUR_EXISTE, producteur.getNom());
        }
        return producteur;



/*        try {
            producteurExs = validationExistenceProducteur(producteur, id);
        } catch (FormValidationException e) {
            setErreur(CHAMP_PRODUCTEUR_EXISTE, e.getMessage());
        }
        return producteurExs;*/
    }

/*    private Producteur validationExistenceProducteur(Producteur producteur, Long id)
            throws FormValidationException {
        List<Producteur> listSessionProducteurs = sessionUtils.getSessionProducteurs();
        Producteur producteurDansList = null;
        for (Producteur producteurCurr : listSessionProducteurs) {
            if (producteurCurr.getNom().equals(producteur.getNom()) &&
                    producteurCurr.getAdresse().equals(producteur.getAdresse()) &&
                    producteurCurr.getContact().equals(producteur.getContact())
            ) {
                producteurDansList = producteurCurr;
            }
        }
        if (producteurDansList != null && !producteurDansList.getId().equals(id)) {
            producteur = producteurDansList;
            throw new FormValidationException(producteur.getNom());
        }
        return producteur;
    }*/

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }

}