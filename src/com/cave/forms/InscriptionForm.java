package com.cave.forms;

import com.cave.beans.Utilisateur;
import com.cave.dao.DAOException;
import com.cave.dao.UtilisateurDao;
import com.cave.tools.ServiceUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public final class InscriptionForm {


    private static final String CHAMP_ID = "idUser";
    private static final String CHAMP_EMAIL = "email";
    private static final String CHAMP_PASS = "motdepasse";
    private static final String CHAMP_CONF = "confirmation";
    private static final String CHAMP_NOM = "nom";
    private static final String CHAMP_ISPAYED = "isPayed";
    private static final String CHAMP_WINEPRODUCER = "isWineproducer";
    private static final String CHAMP_IS_ALLOWED_AD = "isAllowedAD";

    private static final String WINEPRODUCER = "wineproducer";
    private static final String CHAMP_ERREUR_DAO = "erreurDao";

    private String successMaj;
    private String unsuccessMaj;
    private String successCreation;
    private String unsuccessCreation;
    private Map<String, String> erreurs = new HashMap<String, String>();

    private UtilisateurDao utilisateurDao;
    private ServiceUtils serviceUtils;

    public InscriptionForm(UtilisateurDao utilisateurDao, ServiceUtils serviceUtils) {
        this.utilisateurDao = utilisateurDao;
        this.serviceUtils = serviceUtils;
    }

    public InscriptionForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getSuccessCreation() {
        return successCreation;
    }

    public String getUnsuccessCreation() {
        return unsuccessCreation;
    }

    public String getSuccessMaj() {
        return successMaj;
    }

    public String getUnsuccessMaj() {
        return unsuccessMaj;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }


    public Utilisateur mapUtilisateur(HttpServletRequest request, Boolean inAllowedAD, Boolean isAdmin) {
        Long id = serviceUtils.getValeurChampLong(request, CHAMP_ID);
        Utilisateur utilisateur = new Utilisateur();
        String email = serviceUtils.getValeurChampString(request, CHAMP_EMAIL);
        String motDePasse = serviceUtils.getValeurChampString(request, CHAMP_PASS);
        String confirmation = serviceUtils.getValeurChampString(request, CHAMP_CONF);
        String nom = serviceUtils.getValeurChampString(request, CHAMP_NOM);
        String isWineproducer = serviceUtils.getValeurChampString(request, CHAMP_WINEPRODUCER);
        Boolean isAllowedAD = serviceUtils.getValeurChampBoolNoNullable(request, CHAMP_IS_ALLOWED_AD);
        if (isAdmin) {
            utilisateur.setIsPayed(serviceUtils.getValeurChampIntNoNullable(request, CHAMP_ISPAYED));
        }
        utilisateur.setIsWineproducer(WINEPRODUCER.equals(isWineproducer));
/*        if (WINEPRODUCER.equals(isWineproducer)) {
            utilisateur.setIsWineproducer(true);
        } else {
            utilisateur.setIsWineproducer(false);
        }*/
        utilisateur.setIsAllowedAD(inAllowedAD && isAllowedAD);
        utilisateur.setId(id);
        try {
            traiterMotsDePasse(motDePasse, confirmation, utilisateur);
            traiterNom(nom, utilisateur);
            traiterEmail(email, utilisateur);
        } catch (DAOException e) {
            setErreur(CHAMP_ERREUR_DAO, e.getMessage());
            e.printStackTrace();
        }
        return utilisateur;
    }


    public Utilisateur inscrireUtilisateur(HttpServletRequest request, Boolean inAllowedAD, Boolean isAdmin) {
        Utilisateur utilisateur = mapUtilisateur(request, inAllowedAD, isAdmin);
        if (erreurs.isEmpty()) {
            utilisateurDao.creer(utilisateur, isAdmin);
            successCreation = "Succès de l'inscription." + utilisateur.getNom();
        } else {
            unsuccessCreation = " " + utilisateur.getNom();
        }
        return utilisateur;
    }

    public Utilisateur updateUtilisateur(HttpServletRequest request, Boolean inAllowedAD, Boolean isAdmin) {
        Utilisateur utilisateur = mapUtilisateur(request, inAllowedAD, isAdmin);
        if (erreurs.isEmpty()) {
            utilisateurDao.update(utilisateur, isAdmin);
            successMaj = " " + utilisateur.getNom();
        } else {
            unsuccessMaj = " " + utilisateur.getNom();
        }
        return utilisateur;
    }

    private void traiterEmail(String email, Utilisateur utilisateur) {
        try {
            validationEmail(email, utilisateur);
        } catch (FormValidationException e) {
            setErreur(CHAMP_EMAIL, e.getMessage());
        }
        utilisateur.setEmail(email);
    }

    /* Validation de l'adresse email */
    private void validationEmail(String email, Utilisateur utilisateur) throws FormValidationException {
        Long idParMail = null;
        try {
            idParMail = utilisateurDao.trouverIdParEmail(email);
        } catch (Exception e) {
            setErreur(CHAMP_ERREUR_DAO, e.getMessage());
            e.printStackTrace();
        }
        if (idParMail != null && !idParMail.equals(utilisateur.getId())) {
            throw new FormValidationException(
                    "Cette adresse email est déjà utilisée, merci d'en choisir une autre.");
        }
    }

    /*
     * Appel à la validation des mots de passe reçus, chiffrement du mot de
     * passe et initialisation de la propriété motDePasse du bean
     */
    private void traiterMotsDePasse(String motDePasse, String confirmation, Utilisateur utilisateur) {
        try {
            validationMotDePasse(motDePasse);
            try {
                validationConfirmationMotsDePasse(motDePasse, confirmation);
            } catch (Exception e) {
                setErreur(CHAMP_CONF, e.getMessage());
            }
        } catch (FormValidationException e) {
            setErreur(CHAMP_PASS, e.getMessage());

        }
        utilisateur.setMotDePasse(motDePasse);
    }

    /* Validation des mots de passe */
    private void validationMotDePasse(String motDePasse) throws FormValidationException {
        if (motDePasse.length() < 6) {
            throw new FormValidationException("Les mots de passe doivent contenir au moins 6 caractères.");
        }

    }

    private void validationConfirmationMotsDePasse(String motDePasse, String confirmation)
            throws FormValidationException {
        if (!motDePasse.equals(confirmation)) {
            throw new FormValidationException(
                    "Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
        }
    }

    private void traiterNom(String nom, Utilisateur utilisateur) {
        try {
            validationNom(nom);
        } catch (FormValidationException e) {
            setErreur(CHAMP_NOM, e.getMessage());
        }
        utilisateur.setNom(nom);
    }

    private void validationNom(String nom) throws FormValidationException {
        if (nom == null) {
            throw new FormValidationException("Merci de saisir votre nom.");
        }
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }

}