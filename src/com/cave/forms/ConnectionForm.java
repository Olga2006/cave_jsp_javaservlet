package com.cave.forms;

import com.cave.beans.Utilisateur;
import com.cave.dao.DAOException;
import com.cave.dao.UtilisateurDao;
import com.cave.tools.ServiceUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public final class ConnectionForm {

    private static final String CHAMP_EMAIL = "email";
    private static final String CHAMP_PASS = "motdepasseconnection";
    private static final String CHAMP_ERREUR_DAO = "erreurDao";
    public static final String CHAMP_ERREUR_DAO_MDP_OUBLIE = "erreurDaoMdpOublie";

    private String success;
    private String unsuccess;
    private Map<String, String> erreurs = new HashMap<String, String>();

    private UtilisateurDao utilisateurDao;
    private ServiceUtils serviceUtils;

    public ConnectionForm(UtilisateurDao utilisateurDao, ServiceUtils serviceUtils) {
        this.utilisateurDao = utilisateurDao;
        this.serviceUtils = serviceUtils;
    }

    public ConnectionForm() {
        super();
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String setSuccess) {
        success = setSuccess;
    }

    public String getUnsuccess() {
        return unsuccess;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public Utilisateur connecterUtilisateur(HttpServletRequest request) {
        /* Récupération des champs du formulaire */
        String email = serviceUtils.getValeurChampString(request, CHAMP_EMAIL);
        String motdepasseconnection = serviceUtils.getValeurChampString(request, CHAMP_PASS);

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);

        try {
            utilisateur = validationUtilisateur(email, motdepasseconnection);
        } catch (FormValidationException e) {
            setErreur(CHAMP_PASS, e.getMessage());
        }

        /* Initialisation du résultat global de la validation. */
        if (erreurs.isEmpty()) {
            success = "Succès de la connexion.";
        } else {
            unsuccess = "Échec de la connexion.";
        }
        return utilisateur;
    }

    /* Validation de l'adresse email */
    private Utilisateur validationUtilisateur(String email, String motdepasseconnection)
            throws FormValidationException {
        Utilisateur utilisateur = null;
        try {
            utilisateur = utilisateurDao.verifier(email, motdepasseconnection);
            if (utilisateur == null) {

                throw new FormValidationException(
                        "Email ou mot de passe incorrecte");
            }

        } catch (DAOException e) {
            setErreur(CHAMP_ERREUR_DAO, e.getMessage());
            e.printStackTrace();
        }
        return utilisateur;
    }

    public Map<String, String> trouverNomMDPParMail(String email) {
        Map<String, String> nomMDP = new HashMap<>();
        try {
            nomMDP = utilisateurDao.trouverNomMDP(email);
            if (nomMDP.isEmpty()) {
                setErreur(CHAMP_EMAIL, "notExist");
            }

        } catch (DAOException e) {
            setErreur(CHAMP_ERREUR_DAO_MDP_OUBLIE, e.getMessage());
            e.printStackTrace();
        }
        if (erreurs.isEmpty()) {
            success = "emailSended";
        }
        return nomMDP;
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    public void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }

}