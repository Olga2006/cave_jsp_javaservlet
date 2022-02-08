package com.cave.beans;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public class BouteillePost implements Serializable {

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
    private Integer nbrListCourses;
    private String image;
    private String commentaire;
    private Long idProducteur;
    private String urlAchat;
    private Long idUtilisateur;
    private Integer evaluation;
    private Boolean isAllowedCL;

    public BouteillePost() {
        super();
    }

    public BouteillePost(Long id, String nom, String couleur, String region, String pays, String cru, String appelation, Double taille, Double prixAchat, Double prixActuelle, Integer dateDeProduction, Integer dateGarder, Integer nbrListCourses, String image, String commentaire, Long idProducteur, String urlAchat, Long idUtilisateur, Integer evaluation, Boolean isAllowedCL) {
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
        this.nbrListCourses = nbrListCourses;
        this.image = image;
        this.commentaire = commentaire;
        this.idProducteur = idProducteur;
        this.urlAchat = urlAchat;
        this.idUtilisateur = idUtilisateur;
        this.evaluation = evaluation;
        this.isAllowedCL = isAllowedCL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getCru() {
        return cru;
    }

    public void setCru(String cru) {
        this.cru = cru;
    }

    public String getAppelation() {
        return appelation;
    }

    public void setAppelation(String appelation) {
        this.appelation = appelation;
    }

    public Double getTaille() {
        return taille;
    }

    public void setTaille(Double taille) {
        this.taille = taille;
    }

    public Double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(Double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public Double getPrixActuelle() {
        return prixActuelle;
    }

    public void setPrixActuelle(Double prixActuelle) {
        this.prixActuelle = prixActuelle;
    }

    public Integer getDateDeProduction() {
        return dateDeProduction;
    }

    public void setDateDeProduction(Integer dateDeProduction) {
        this.dateDeProduction = dateDeProduction;
    }

    public Integer getDateGarder() {
        return dateGarder;
    }

    public void setDateGarder(Integer dateGarder) {
        this.dateGarder = dateGarder;
    }

    public Integer getNbrListCourses() {
        return nbrListCourses;
    }

    public void setNbrListCourses(Integer nbrListCourses) {
        this.nbrListCourses = nbrListCourses;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Long getIdProducteur() {
        return idProducteur;
    }

    public void setIdProducteur(Long idProducteur) {
        this.idProducteur = idProducteur;
    }

    public String getUrlAchat() {
        return urlAchat;
    }

    public void setUrlAchat(String urlAchat) {
        this.urlAchat = urlAchat;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    public Boolean getIsAllowedCL() {
        return isAllowedCL;
    }

    public void setIsAllowedCL(Boolean allowedCL) {
        isAllowedCL = allowedCL;
    }
}
