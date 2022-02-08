package com.cave.beans;

import java.io.Serializable;
import java.util.List;

public class BouteilleOfProducer implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String nom;
    private String couleur;
    private String region;
    private String pays;
    private String cru;
    private String appelation;
    private Double taille;
    private Integer dateDeProduction;
    private Integer dateGarder;
    private Integer nbrAneeABoir;
    private Double prixActuelle;
    private Integer nbrTotal;
    private List<PositionShort> positions;

    public BouteilleOfProducer(Long id, String nom, String couleur, String region, String pays, String cru, String appelation, Double taille, Integer dateDeProduction, Integer dateGarder, Integer nbrAneeABoir, Double prixActuelle, Integer nbrTotal, List<PositionShort> positions) {
        this.id = id;
        this.nom = nom;
        this.couleur = couleur;
        this.region = region;
        this.pays = pays;
        this.cru = cru;
        this.appelation = appelation;
        this.taille = taille;
        this.dateDeProduction = dateDeProduction;
        this.dateGarder = dateGarder;
        this.nbrAneeABoir = nbrAneeABoir;
        this.prixActuelle = prixActuelle;
        this.nbrTotal = nbrTotal;
        this.positions = positions;
    }

    public BouteilleOfProducer() {
    }

    public Integer getDateGarder() {
        return dateGarder;
    }

    public void setDateGarder(Integer dateGarder) {
        this.dateGarder = dateGarder;
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

    public Integer getDateDeProduction() {
        return dateDeProduction;
    }

    public void setDateDeProduction(Integer dateDeProduction) {
        this.dateDeProduction = dateDeProduction;
    }

    public Integer getNbrAneeABoir() {
        return nbrAneeABoir;
    }

    public void setNbrAneeABoir(Integer nbrAneeABoir) {
        this.nbrAneeABoir = nbrAneeABoir;
    }

    public Double getPrixActuelle() {
        return prixActuelle;
    }

    public void setPrixActuelle(Double prixActuelle) {
        this.prixActuelle = prixActuelle;
    }

    public Integer getNbrTotal() {
        return nbrTotal;
    }

    public void setNbrTotal(Integer nbrTotal) {
        this.nbrTotal = nbrTotal;
    }

    public List<PositionShort> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionShort> positions) {
        this.positions = positions;
    }
}
