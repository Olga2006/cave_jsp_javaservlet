package com.cave.beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class Bouteille implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nom;
    private String couleur;
    private String region;
    private String pays;
    private String cru;
    private String appelation;
    private Double taille;
    private Double prixAchat;
    private Double prixActuelle;
    private Integer dateDeProduction;
    private Integer dateGarder;
    private Integer nbrAneeABoir;
    private Integer nbrListCourses;
    private Integer nbrTotal;
    private String image;
    private String commentaire;
    private Producteur producteur;
    private List<Position> positions;
    private Long idProducteur;
    private String nomProducteur;
    private Boolean byWineproducer;
    private String urlAchat;
    private Long idUtilisateur;
    private Integer evaluation;
    private Boolean isAllowedCL;

    public Bouteille() {
        super();
        // TODO Auto-generated constructor stub

/*        Calendar now = Calendar.getInstance();
        Integer yearNow = now.get(
                Calendar.YEAR);
        this.nbrAneeABoir = this.dateGarder - yearNow;*/
    }


    public Bouteille(Long id, String nom, String commentaire, Integer evaluation) {
        this.id = id;
        this.nom = nom;
        this.commentaire = commentaire;
        this.evaluation = evaluation;
    }

    public Bouteille(Long id, String nom, String couleur, String region, String pays, String cru, String appelation, Double taille, Double prixAchat, Double prixActuelle, Integer dateDeProduction, Integer dateGarder, Integer nbrAneeABoir, Integer nbrListCourses, Integer nbrTotal, String image, String commentaire, Producteur producteur, List<Position> positions, Long idProducteur, String nomProducteur, Boolean byWineproducer, String urlAchat, Long idUtilisateur, Integer evaluation, Boolean isAllowedCL) {
        this.id = id;
        this.nom = nom;
        this.couleur = couleur;
        this.region = region;
        this.pays = pays;
        this.cru = cru;
        this.appelation = appelation;
        this.taille = taille;
        this.prixAchat = prixAchat;
        this.prixActuelle = prixActuelle;
        this.dateDeProduction = dateDeProduction;
        this.dateGarder = dateGarder;
        this.nbrAneeABoir = nbrAneeABoir;
        this.nbrListCourses = nbrListCourses;
        this.nbrTotal = nbrTotal;
        this.image = image;
        this.commentaire = commentaire;
        this.producteur = producteur;
        this.positions = positions;
        this.idProducteur = idProducteur;
        this.nomProducteur = nomProducteur;
        this.byWineproducer = byWineproducer;
        this.urlAchat = urlAchat;
        this.idUtilisateur = idUtilisateur;
        this.evaluation = evaluation;
        this.isAllowedCL = isAllowedCL;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the couleur
     */
    public String getCouleur() {
        return couleur;
    }

    /**
     * @param couleur the couleur to set
     */
    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return the pays
     */
    public String getPays() {
        return pays;
    }

    /**
     * @param pays the pays to set
     */
    public void setPays(String pays) {
        this.pays = pays;
    }

    /**
     * @return the cru
     */
    public String getCru() {
        return cru;
    }

    /**
     * @param cru the cru to set
     */
    public void setCru(String cru) {
        this.cru = cru;
    }

    /**
     * @return the appelation
     */
    public String getAppelation() {
        return appelation;
    }

    /**
     * @param appelation the appelation to set
     */
    public void setAppelation(String appelation) {
        this.appelation = appelation;
    }

    /**
     * @return the taille
     */
    public Double getTaille() {
        return taille;
    }

    /**
     * @param taille the taille to set
     */
    public void setTaille(Double taille) {
        this.taille = taille;
    }

    /**
     * @return the prixAchat
     */
    public Double getPrixAchat() {
        return prixAchat;
    }

    /**
     * @param prixAchat the prixAchat to set
     */
    public void setPrixAchat(Double prixAchat) {
        this.prixAchat = prixAchat;
    }

    /**
     * @return the prixActuelle
     */
    public Double getPrixActuelle() {
        return prixActuelle;
    }

    /**
     * @param prixActuelle the prixActuelle to set
     */
    public void setPrixActuelle(Double prixActuelle) {
        this.prixActuelle = prixActuelle;
    }

    /**
     * @return the dateDeProduction
     */
    public Integer getDateDeProduction() {
        return dateDeProduction;
    }

    /**
     * @param dateDeProduction the dateDeProduction to set
     */
    public void setDateDeProduction(Integer dateDeProduction) {
        this.dateDeProduction = dateDeProduction;
    }

    /**
     * @return the dateGarder
     */
    public Integer getDateGarder() {
        return dateGarder;
    }

    /**
     * @param dateGarder the dateGarder to set
     */
    public void setDateGarder(Integer dateGarder) {
        this.dateGarder = dateGarder;
    }

    /**
     * @return the nbrAneeABoir
     */
    public Integer getNbrAneeABoir() {
        return nbrAneeABoir;
    }

    /**
     * @param nbrAneeABoir the nbrAneeABoir to set
     */
    public void setNbrAneeABoir(Integer nbrAneeABoir) {

        this.nbrAneeABoir = nbrAneeABoir;
    }

    /**
     * @return the nbrListCourses
     */
    public Integer getNbrListCourses() {
        return nbrListCourses;
    }

    /**
     * @param nbrListCourses the nbrListCourses to set
     */
    public void setNbrListCourses(Integer nbrListCourses) {
        this.nbrListCourses = nbrListCourses;
    }

    /**
     * @return the nbrTotal
     */
    public Integer getNbrTotal() {
        return nbrTotal;
    }

    /**
     * @param nbrTotal the nbrTotal to set
     */
    public void setNbrTotal(Integer nbrTotal) {
        this.nbrTotal = nbrTotal;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the commentaire
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     * @param commentaire the commentaire to set
     */
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    /**
     * @return the producteur
     */
    public Producteur getProducteur() {
        return producteur;
    }

    /**
     * @param producteur the producteur to set
     */
    public void setProducteur(Producteur producteur) {
        this.producteur = producteur;
    }

    /**
     * @return the positions
     */
    public List<Position> getPositions() {
        return positions;
    }

    /**
     * @param positions the positions to set
     */
    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    /**
     * @return the idProducteur
     */
    public Long getIdProducteur() {
        return idProducteur;
    }

    /**
     * @param idProducteur the idProducteur to set
     */
    public void setIdProducteur(Long idProducteur) {
        this.idProducteur = idProducteur;
    }

    /**
     * @return the nomProducteur
     */
    public String getNomProducteur() {
        return nomProducteur;
    }

    /**
     * @param nomProducteur the nomProducteur to set
     */
    public void setNomProducteur(String nomProducteur) {
        this.nomProducteur = nomProducteur;
    }

    /**
     * @return the idUtilisateur
     */
    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    /**
     * @param idUtilisateur the idUtilisateur to set
     */
    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    /**
     * @return the evaluation
     */
    public Integer getEvaluation() {
        return evaluation;
    }

    /**
     * @param evaluation the evaluation to set
     */
    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    public String getUrlAchat() {
        return urlAchat;
    }

    public void setUrlAchat(String urlAchat) {
        this.urlAchat = urlAchat;
    }

    public Boolean getByWineproducer() {
        return byWineproducer;
    }

    public void setByWineproducer(Boolean byWineproducer) {
        this.byWineproducer = byWineproducer;
    }

    public Boolean getIsAllowedCL() {
        return isAllowedCL;
    }

    public void setIsAllowedCL(Boolean isAllowedCL) {
        this.isAllowedCL = isAllowedCL;
    }
    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public static Comparator<Bouteille> ComparNbrAneeABoir = (bouteille0, bouteille1) -> {
        int nbrAneeABoir0 = bouteille0.getNbrAneeABoir();
        int nbrAneeABoir1 = bouteille1.getNbrAneeABoir();
        return nbrAneeABoir0 - nbrAneeABoir1;
    };

    public static Comparator<Bouteille> ComparNom = (bouteille0, bouteille1) -> {
        String nom0 = bouteille0.getNom().toUpperCase();
        String nom1 = bouteille1.getNom().toUpperCase();

        return nom0.compareTo(nom1);
    };

    public static Comparator<Bouteille> ComparDateDeProduction = (bouteille0, bouteille1) -> {
        int dateDeProduction0 = bouteille0.getDateDeProduction();
        int dateDeProduction1 = bouteille1.getDateDeProduction();
        return dateDeProduction0 - dateDeProduction1;
    };

    public static Comparator<Bouteille> ComparDateGarder = (bouteille0, bouteille1) -> {
        int dateGarder0 = bouteille0.getDateGarder();
        int dateGarder1 = bouteille1.getDateGarder();
        return dateGarder0 - dateGarder1;
    };

    public static Comparator<Bouteille> ComparTaille = (bouteille0, bouteille1) -> {
        Double taille0 = bouteille0.getTaille();
        Double taille1 = bouteille1.getTaille();
        return (int) (taille0 - taille1);
    };

    public static Comparator<Bouteille> ComparPrixAchat = (bouteille0, bouteille1) -> {
        Double prixAchat0 = bouteille0.getPrixAchat();
        Double prixAchat1 = bouteille1.getPrixAchat();
        return (int) (prixAchat0 - prixAchat1);
    };

    public static Comparator<Bouteille> ComparPrixActuelle = (bouteille0, bouteille1) -> {
        Double prixActuelle0 = bouteille0.getPrixActuelle();
        Double prixActuelle1 = bouteille1.getPrixActuelle();
        return (int) (prixActuelle0 - prixActuelle1);
    };

    public static Comparator<Bouteille> ComparNbrTotal = (bouteille0, bouteille1) -> {
        int nbrTotal0 = bouteille0.getNbrTotal();
        int nbrTotal1 = bouteille1.getNbrTotal();
        return nbrTotal0 - nbrTotal1;
    };

    public static Comparator<Bouteille> ComparCouleur = (bouteille0, bouteille1) -> {
        String couleur0 = bouteille0.getCouleur().toUpperCase();
        String couleur1 = bouteille1.getCouleur().toUpperCase();
        // ascending
        // order
        return couleur0.compareTo(couleur1);
    };

    public static Comparator<Bouteille> ComparCru = (bouteille0, bouteille1) -> {
        String cru0 = bouteille0.getCru().toUpperCase();
        String cru1 = bouteille1.getCru().toUpperCase();

        return cru0.compareTo(cru1);
    };

    public static Comparator<Bouteille> ComparPays = (bouteille0, bouteille1) -> {
        String pays0 = bouteille0.getPays().toUpperCase();
        String pays1 = bouteille1.getPays().toUpperCase();

        return pays0.compareTo(pays1);
    };

    public static Comparator<Bouteille> ComparRegion = (bouteille0, bouteille1) -> {
        String region0 = bouteille0.getRegion().toUpperCase();
        String region1 = bouteille1.getRegion().toUpperCase();

        return region0.compareTo(region1);
    };

    public static Comparator<Bouteille> ComparAppelation = (bouteille0, bouteille1) -> {
        String appelation0 = bouteille0.getAppelation().toUpperCase();
        String appelation1 = bouteille1.getAppelation().toUpperCase();

        return appelation0.compareTo(appelation1);
    };

}
