package com.cave.forms;

import com.cave.beans.Bouteille;
import com.cave.beans.Producteur;
import com.cave.beans.Utilisateur;
import com.cave.dao.BouteilleDao;
import com.cave.dao.DAOException;
import com.cave.dao.ErreurDao;
import com.cave.dao.ProducteurDao;
import com.cave.tools.AWSUtils;
import com.cave.tools.SessionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CreationBouteilleForm {
    public static final String PARAM_ID_BOUTEILLE = "idBouteille";
    public static final String PARAM_NOM_BOUTEILLE = "nomBouteille";
    private static final String CHAMP_IMAGE = "image";
    private static final String CHAMP_URL_ACHAT = "urlAchat";
    private static final String CHAMP_IS_ALLOWED_CL = "isAllowedCLBut";
    private static final String CHAMP_CHOIX_PRODUCTER = "choixNouveauProducteur";
    private static final String CHAMP_CHOIX_AJOUTER_PRODUCTER = "choixAjouterProducteur";
    private static final String CHAMP_LISTE_PRODCTEURS = "listeProducteurs";
    private static final String CHAMP_ERREUR_DAO = "erreurDaoBouteille";
    private static final String CHAMP_NOM = "nom";


    private static final String CHAMP_BOTEILLE_EXISTE = "bouteilleExiste";

    private static final String CHAMP_PAYS = "pays";
    private static final String CHAMP_REGION = "region";
    private static final String CHAMP_APPELATION = "appelation";
    private static final String CHAMP_CRU = "cru";
    private static final String CHAMP_COLEUR = "couleur";
    private static final String CHAMP_TAILLE = "taille";

    private static final String CHAMP_QUANTITE_ACHETER = "quantiteAcheter";
    private static final String CHAMP_PRIX_ACHAT = "prixAchat";
    private static final String CHAMP_PRIX_ACTUELLE = "prixActuelle";
    private static final String CHAMP_DATE_DE_PRODUCTION = "dateDeProduction";
    private static final String CHAMP_DATE_GARDER = "dateGarder";
    private static final String CHAMP_EVALUATION = "evaluation";
    private static final String CHAMP_COMMENTAIRE = "commentaire";

    private static final String ANCIEN_PRODUCTER = "ancienProducteur";
    private static final String AJOUTER_PRODUCTER = "ajouterProducteur";

    public static Integer beginningimg = 1;

    private String successCreation;
    private String successMaj;
    private String successCreationB;
    private String successMajB;
    private String successMajLC;
    private String successMajEvaluation;
    private String successMajCommentaire;
    private String successCopy;

    private String unsuccessCreation;
    private String unsuccessMaj;
    private String unsuccessCreationB;
    private String unsuccessMajB;
    private String unsuccessMajLC;
    private String unsuccessMajEvaluation;
    private String unsuccessMajCommentaire;
    private String unsuccessCopy;

    /* ------------------Pour producteur---------------------------- */
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
    /* ------------------fin---------------------------- */

    public void setUnsuccessMajCommentaire(String unsuccessMajCommentaire) {
        this.unsuccessMajCommentaire = unsuccessMajCommentaire;
    }

    private Map<String, String> erreurs = new HashMap<String, String>();

    private BouteilleDao bouteilleDao;
    private ProducteurDao producteurDao;
    private SessionUtils sessionUtils;

    public String getSuccessCreationB() {
        return successCreationB;
    }

    public String getSuccessMajB() {
        return successMajB;
    }

    public String getSuccessMajLC() {
        return successMajLC;
    }

    public String getSuccessMajEvaluation() {
        return successMajEvaluation;
    }

    public String getSuccessMajCommentaire() {
        return successMajCommentaire;
    }

    public String getUnsuccessCreationB() {
        return unsuccessCreationB;
    }

    public String getUnsuccessMajB() {
        return unsuccessMajB;
    }

    public String getUnsuccessMajLC() {
        return unsuccessMajLC;
    }

    public String getUnsuccessMajEvaluation() {
        return unsuccessMajEvaluation;
    }

    public String getUnsuccessMajCommentaire() {
        return unsuccessMajCommentaire;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public CreationBouteilleForm(ProducteurDao producteurDao, BouteilleDao bouteilleDao, SessionUtils sessionUtils) {
        this.bouteilleDao = bouteilleDao;
        this.producteurDao = producteurDao;
        this.sessionUtils = sessionUtils;
    }

    public Bouteille mapBouteille(HttpServletRequest request, Utilisateur sessionUtilisateur, Producteur producteur) {
        Long id = sessionUtils.getValeurChampLong(request, PARAM_ID_BOUTEILLE);
        String nom = sessionUtils.getValeurChampString(request, CHAMP_NOM);
        String pays = sessionUtils.getValeurChampString(request, CHAMP_PAYS);
        String region = sessionUtils.getValeurChampString(request, CHAMP_REGION);
        String appelation = sessionUtils.getValeurChampString(request, CHAMP_APPELATION);
        String cru = sessionUtils.getValeurChampString(request, CHAMP_CRU);
        String couleur = sessionUtils.getValeurChampString(request, CHAMP_COLEUR);
        String taille = sessionUtils.getValeurChampString(request, CHAMP_TAILLE);
        String quantiteAcheter = sessionUtils.getValeurChampString(request, CHAMP_QUANTITE_ACHETER);
        String prixAchat = sessionUtils.getValeurChampString(request, CHAMP_PRIX_ACHAT);
        String prixActuelle = sessionUtils.getValeurChampString(request, CHAMP_PRIX_ACTUELLE);
        String dateProduction = sessionUtils.getValeurChampString(request, CHAMP_DATE_DE_PRODUCTION);
        String dateGarder = sessionUtils.getValeurChampString(request, CHAMP_DATE_GARDER);
        String evaluation = sessionUtils.getValeurChampString(request, CHAMP_EVALUATION);
        String commentaire = sessionUtils.getValeurChampString(request, CHAMP_COMMENTAIRE);
        String image = sessionUtils.getValeurChampString(request, CHAMP_IMAGE);
        String urlAchat = sessionUtils.getValeurChampString(request, CHAMP_URL_ACHAT);
        Boolean isAllowedCL = sessionUtils.getValeurChampBoolNoNullable(request, CHAMP_IS_ALLOWED_CL);
        Bouteille bouteille = new Bouteille();
        bouteille.setId(id);
        bouteille.setIdUtilisateur(sessionUtilisateur.getId());
        bouteille.setNom(nom);
        bouteille.setPays(pays);
        bouteille.setRegion(region);
        bouteille.setAppelation(appelation);
        bouteille.setCru(cru);
        bouteille.setCouleur(couleur);
        bouteille.setCommentaire(commentaire);
        bouteille.setUrlAchat(urlAchat);
        bouteille.setImage(image);
        bouteille.setProducteur(producteur);
        bouteille.setIdProducteur(producteur.getId());
        bouteille.setEvaluation(validationNombre(evaluation));
        bouteille.setNbrListCourses(validationNombre(quantiteAcheter));
        bouteille.setTaille(validationDouble(taille));
        bouteille.setPrixAchat(validationDouble(prixAchat));
        bouteille.setPrixActuelle(validationDouble(prixActuelle));
        bouteille.setDateDeProduction(validationAnee(dateProduction));
        bouteille.setDateGarder(validationAnee(dateGarder));
        bouteille.setIsAllowedCL(isAllowedCL);

        traiterExistenceBouteille(bouteille);

        return bouteille;
    }

    public Bouteille copyBouteillePourUtilisateur(Utilisateur utilisateur, Long idBouteille,  ErreurDao erreurDao, String page) {
        Bouteille bouteille = bouteilleDao.trouver(idBouteille);
        if (bouteille == null && !bouteille.getIsAllowedCL()) {
            return null;
        }
        bouteille.setId(null);
        bouteille.setIdUtilisateur(utilisateur.getId());
        bouteille.setIsAllowedCL(true);
        bouteille.setNbrListCourses(null);

        if (bouteille.getIdProducteur() != null) {
            CreationProducteurForm producteurForm = new CreationProducteurForm(producteurDao, sessionUtils);
            Producteur producteur = producteurForm.copyProducteurPourUtilisateur(utilisateur.getId(), bouteille.getIdProducteur());
            if (producteur != null) {
                bouteille.setIdProducteur(producteur.getId());
                bouteille.setProducteur(producteur);
            } else {
                bouteille.setIdProducteur(null);
                bouteille.setProducteur(null);
            }
        }

        traiterExistenceBouteille(bouteille);

        if (erreurs.isEmpty()) {
            String imageBouteilleFrom = bouteille.getImage();
            if (imageBouteilleFrom != null){
                String imageBouteilleTo = imageBouteilleFrom.split("/")[0]+"/"+utilisateur.getId().toString() + "_" + imageBouteilleFrom.split("/")[1];
                new AWSUtils().CopyImage(imageBouteilleFrom, imageBouteilleTo, erreurDao, utilisateur.getId(), page);
                bouteille.setImage(imageBouteilleTo);
            }
            try {
                bouteille = bouteilleDao.creerPourUtilisateur(bouteille);
                successCreationB = " " + bouteille.getNom();

            } catch (DAOException e) {
                setErreur(CHAMP_ERREUR_DAO, e.getMessage());
                e.printStackTrace();
                unsuccessCreationB = " " + bouteille.getNom();
            }
        } else {
            unsuccessCreationB = " " + bouteille.getNom();
        }
        return bouteille;
    }

    public Bouteille creerBouteillePourUtilisateur(HttpServletRequest request, Utilisateur sessionUtilisateur) {
        Producteur producteur = new Producteur();
        String choixNouveauProducteur = sessionUtils.getValeurChampString(request, CHAMP_CHOIX_PRODUCTER);
        String choixAjouterProducteur = sessionUtils.getValeurChampString(request, CHAMP_CHOIX_AJOUTER_PRODUCTER);
        if (AJOUTER_PRODUCTER.equals(choixAjouterProducteur)) {
            if (ANCIEN_PRODUCTER.equals(choixNouveauProducteur)) {
                /* Récupération du id du Producteur choisi */
                Long idAncienProducteur = sessionUtils.getValeurChampLong(request, CHAMP_LISTE_PRODCTEURS);
                if (idAncienProducteur == null) {
                    setErreur(CHAMP_CHOIX_PRODUCTER,
                            " ");
                    idAncienProducteur = 0L;
                }
                List<Producteur> producteurs = sessionUtilisateur.getProducteurs();
                if (idAncienProducteur != null && producteurs != null) {
                    for (Producteur producteurCurr : producteurs) {
                        if (producteurCurr.getId().equals(idAncienProducteur)) {
                            producteur = producteurCurr;
                            break;
                        }
                    }
                }

            } else {

                CreationProducteurForm producteurForm = new CreationProducteurForm(producteurDao, sessionUtils);
                producteur = producteurForm.creerProducteurPourUtilisateur(request, sessionUtilisateur);
                erreurs = producteurForm.getErreurs();
                successCreation = producteurForm.getSuccessCreation();
                unsuccessCreation = producteurForm.getUnsuccessCreation();
                successMaj = producteurForm.getSuccessMaj();
                unsuccessMaj = producteurForm.getUnsuccessMaj();
            }
        }

        Bouteille bouteille = mapBouteille(request, sessionUtilisateur, producteur);
        if (erreurs.isEmpty()) {
            try {
                bouteille = bouteilleDao.creerPourUtilisateur(bouteille);
                successCreationB = " " + bouteille.getNom();

            } catch (DAOException e) {
                setErreur(CHAMP_ERREUR_DAO, e.getMessage());
                e.printStackTrace();
                unsuccessCreationB = " " + bouteille.getNom();
            }
        } else {
            unsuccessCreationB = " " + bouteille.getNom();
        }
        return bouteille;
    }

    public Bouteille updateBouteille(HttpServletRequest request, Utilisateur sessionUtilisateur) {
        Producteur producteur = new Producteur();
        String choixNouveauProducteur = sessionUtils.getValeurChampString(request, CHAMP_CHOIX_PRODUCTER);
        String choixAjouterProducteur = sessionUtils.getValeurChampString(request, CHAMP_CHOIX_AJOUTER_PRODUCTER);
        if (AJOUTER_PRODUCTER.equals(choixAjouterProducteur)) {
            if (ANCIEN_PRODUCTER.equals(choixNouveauProducteur)) {
                /* Récupération du id du Producteur choisi */
                Long idAncienProducteur = sessionUtils.getValeurChampLong(request, CHAMP_LISTE_PRODCTEURS);
                //Long id = null;
                List<Producteur> producteurs = sessionUtilisateur.getProducteurs();

                if (idAncienProducteur != null && producteurs != null) {
                    for (Producteur producteurCurr : producteurs) {
                        if (producteurCurr.getId().equals(idAncienProducteur)) {
                            producteur = producteurCurr;
                            break;
                        }
                    }
                }
                if (idAncienProducteur == null) {
                    setErreur(CHAMP_CHOIX_PRODUCTER,
                            " ");
                    //idAncienProducteur = 0L;
                }
/*                try {
                    id = Long.parseLong(idAncienProducteur);
                } catch (NumberFormatException e) {
                    setErreur(CHAMP_CHOIX_PRODUCTER,
                            " ");
                    id = 0L;
                }*/

            } else {

                CreationProducteurForm producteurForm = new CreationProducteurForm(producteurDao, sessionUtils);
                producteur = producteurForm.creerProducteurPourUtilisateur(request, sessionUtilisateur);
                erreurs = producteurForm.getErreurs();
                successCreation = producteurForm.getSuccessCreation();
                unsuccessCreation = producteurForm.getUnsuccessCreation();
                successMaj = producteurForm.getSuccessMaj();
                unsuccessMaj = producteurForm.getUnsuccessMaj();
            }
        }

        Bouteille bouteille = mapBouteille(request, sessionUtilisateur, producteur);

        if (bouteille.getImage() == null) {
            Bouteille bouteilleCurr = bouteilleDao.trouver(bouteille.getId());
            bouteille.setImage(bouteilleCurr.getImage());
        }
        if (erreurs.isEmpty()) {
            try {
                bouteilleDao.update(bouteille);
                successMajB = " " + bouteille.getNom();
            } catch (DAOException e) {
                setErreur(CHAMP_ERREUR_DAO, e.getMessage());
                e.printStackTrace();
                unsuccessMajB = " " + bouteille.getNom();
            }

        } else {
            unsuccessMajB = " " + bouteille.getNom();
        }
        return bouteille;

    }

    public Bouteille updateEvaluation(HttpServletRequest request, Utilisateur sessionUtilisateur) {
        String evaluation = sessionUtils.getValeurChampString(request, CHAMP_EVALUATION);
        Long idBouteille = sessionUtils.getValeurChampLong(request, PARAM_ID_BOUTEILLE);
        String nomBouteille = sessionUtils.getValeurChampString(request, PARAM_NOM_BOUTEILLE);
        Bouteille bouteille = new Bouteille(idBouteille, nomBouteille, null, null);
        if (idBouteille != null) {
            Integer eval = validationNombre(evaluation);
            bouteille.setEvaluation(eval);
            try {
                bouteilleDao.changerEvaluation(bouteille.getEvaluation(), idBouteille);
                successMajEvaluation = " " + bouteille.getNom();
            } catch (DAOException e) {
                setErreur(CHAMP_ERREUR_DAO, e.getMessage());
                e.printStackTrace();
                unsuccessMajEvaluation = " " + bouteille.getNom();
            }

        }

        return bouteille;
    }

    public Bouteille updateQuantiteLC(HttpServletRequest request) {
        Long idBouteille = sessionUtils.getValeurChampLong(request, PARAM_ID_BOUTEILLE);
        String nomBouteille = sessionUtils.getValeurChampString(request, PARAM_NOM_BOUTEILLE);
        String quantiteAcheter = sessionUtils.getValeurChampString(request, CHAMP_QUANTITE_ACHETER);
        Bouteille bouteille = new Bouteille(idBouteille, nomBouteille, null, null);
        if (idBouteille != null) {
            Integer quantite = validationNombre(quantiteAcheter);
            bouteille.setNbrListCourses(quantite);
            try {
                bouteilleDao.changerLC(bouteille.getNbrListCourses(), bouteille.getId());
                successMajLC = " " + bouteille.getNom();
            } catch (DAOException e) {
                setErreur(CHAMP_ERREUR_DAO, e.getMessage());
                e.printStackTrace();
                unsuccessMajLC = " " + bouteille.getNom();
            }
        }
        return bouteille;
    }

    public Bouteille updateCommentair(HttpServletRequest request) {
        Long idBouteille = sessionUtils.getValeurChampLong(request, PARAM_ID_BOUTEILLE);
        String nomBouteille = sessionUtils.getValeurChampString(request, PARAM_NOM_BOUTEILLE);
        String commentaire = sessionUtils.getValeurChampString(request, CHAMP_COMMENTAIRE);
        Bouteille bouteille = new Bouteille(idBouteille, nomBouteille, commentaire, null);
        if (idBouteille != null) {
            try {
                bouteilleDao.ajouterCommentaire(bouteille.getCommentaire(), bouteille.getId());
                successMajCommentaire = " " + bouteille.getNom();
            } catch (DAOException e) {
                setErreur(CHAMP_ERREUR_DAO, e.getMessage());
                e.printStackTrace();
                unsuccessMajCommentaire = " " + bouteille.getNom();
            }
        }

        return bouteille;
    }

/*    private void traiterProdcteur(Producteur producteur, Bouteille bouteille) {
        if (producteur == null) {
            bouteille.setIdProducteur(null);
        } else {
            bouteille.setIdProducteur(producteur.getId());
        }
        bouteille.setProducteur(producteur);
    }*/

/*    private void traiterTaille(String taille, Bouteille bouteille) {
        double valeurMontant = 0;
        valeurMontant = validationDouble(taille);
        bouteille.setTaille(valeurMontant);
    }*/

/*    private void traiterPrixActuelle(String prixActuelle, Bouteille bouteille) {
        double valeurMontant = 0;
        valeurMontant = validationDouble(prixActuelle);
        bouteille.setPrixActuelle(valeurMontant);
    }

    private void traiterPrixAchat(String prixAchat, Bouteille bouteille) {
        double valeurMontant = 0;
        valeurMontant = validationDouble(prixAchat);
        bouteille.setPrixAchat(valeurMontant);
    }*/

    private double validationDouble(String nombre) {
        double temp;
        if (nombre != null) {
            try {
                temp = Double.parseDouble(nombre);
                if (temp < 0) {
                    temp = 0;
                }
            } catch (NumberFormatException e) {
                temp = 0;
            }
        } else {
            temp = 0;

        }
        return temp;
    }

/*    private void traiterQuantite(String quantiteAcheter, Bouteille bouteille) {
        Integer valeurMontant = 0;
        valeurMontant = validationNombre(quantiteAcheter);
        bouteille.setNbrListCourses(valeurMontant);
    }

    private void traiterEvaluation(String evaluation, Bouteille bouteille) {
        Integer valeurMontant = 0;
        valeurMontant = validationNombre(evaluation);
        bouteille.setEvaluation(valeurMontant);
    }*/

    private Integer validationNombre(String nombre) {
        Integer temp;
        if (nombre != null) {
            try {
                temp = Integer.parseInt(nombre);
                if (temp < 0) {
                    temp = 0;
                }
            } catch (NumberFormatException e) {
                temp = 0;
            }
        } else {
            temp = 0;

        }
        return temp;
    }

/*    private void traiterDateProduction(String dateProduction, Bouteille bouteille) {
        int anee = 0000;

        anee = validationAnee(dateProduction);
        bouteille.setDateDeProduction(anee);
    }

    private void traiterDateGarder(String dateGarder, Bouteille bouteille) {
        int anee = 0000;
        anee = validationAnee(dateGarder);
        bouteille.setDateGarder(anee);
    }*/

    private Integer validationAnee(String anee) {
        Integer temp;
        Calendar now = Calendar.getInstance();
        Integer yearNow = now.get(Calendar.YEAR);

        if (anee != null) {
            try {
                temp = Integer.parseInt(anee);
                if (temp < 0) {
                    temp = 0;
                }
            } catch (NumberFormatException e) {
                temp = 0;
            }
        } else {
            temp = 0;
        }
        return temp;
    }

    private void traiterExistenceBouteille(Bouteille bouteille) {
        try {
            validationExistenceBouteille(bouteille);
        } catch (FormValidationException e) {
            setErreur(CHAMP_BOTEILLE_EXISTE, e.getMessage());
        }

    }

    public void validationExistenceBouteille(Bouteille bouteille)
            throws FormValidationException {
        List<Bouteille> listSessionBouteilles = sessionUtils.getBouteillesSessionUtilisateur();
        Bouteille bouteilleDansList = null;
        for (Bouteille bouteilleCurr : listSessionBouteilles) {
            if (bouteilleCurr.getNom().equals(bouteille.getNom()) &&
                    bouteilleCurr.getPays().equals(bouteille.getPays()) &&
                    bouteilleCurr.getRegion().equals(bouteille.getRegion()) &&
                    bouteilleCurr.getAppelation().equals(bouteille.getAppelation()) &&
                    bouteilleCurr.getCouleur().equals(bouteille.getCouleur()) &&
                    bouteilleCurr.getCru().equals(bouteille.getCru()) &&
                    bouteilleCurr.getDateDeProduction().equals(bouteille.getDateDeProduction()) &&
                    bouteilleCurr.getTaille().equals(bouteille.getTaille()) &&
                    bouteilleCurr.getPrixAchat().equals(bouteille.getPrixAchat()) &&
                    bouteilleCurr.getIdProducteur().equals(bouteille.getIdProducteur())
            ) {
                bouteilleDansList = bouteilleCurr;
            }
        }
        if (bouteilleDansList != null) {
            if (bouteilleDansList.getId().equals(bouteille.getId())) {
                return;
            }
            throw new FormValidationException(bouteille.getNom());
        }
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }

}