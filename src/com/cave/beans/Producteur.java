package com.cave.beans;

import java.io.Serializable;
import java.util.List;

public class Producteur implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nom;
    private String adresse;
    private String contact;
    private Long idUtilisateur;
    private List<Bouteille> bouteilles;
    private Boolean byWineproducer;
    private Boolean isAllowedCL;
    private String url;

    public Producteur(String nom, String adresse, String contact) {
        this.nom = nom;
        this.adresse = adresse;
        this.contact = contact;
    }

    public Producteur() {
        super();
        // TODO Auto-generated constructor stub
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
     * @return the adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @param adresse the adresse to set
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return the bouteilles
     */
    public List<Bouteille> getBouteilles() {
        return bouteilles;
    }

    /**
     * @param bouteilles the bouteilles to set
     */
    public void setBouteilles(List<Bouteille> bouteilles) {
        this.bouteilles = bouteilles;
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


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
