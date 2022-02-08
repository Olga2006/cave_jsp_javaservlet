package com.cave.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Utilisateur implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nom;
    private String email;
    private String motDePasse;
    private Timestamp dateInscription;
    private List<Cave> caves;
    private List<Bouteille> bouteilles;
    private List<Producteur> producteurs;
    private List<Pub> pubs;
    private Integer isPayed;
    private Boolean isWineproducer;
    private Boolean isInCommonList;
    private Boolean isInSecondAD;
    private Boolean isInFirstAD;
    private Boolean isAllowedAD;
    private Boolean isInAllowedAD;
    private Boolean isAdmin;


    public Utilisateur() {
        super();
    }


    public List<Pub> getPubs() {
        return pubs;
    }

    public void setPubs(List<Pub> pubs) {
        this.pubs = pubs;
    }

    public Boolean getInCommonList() {
        return isInCommonList;
    }

    public void setInCommonList(Boolean isInCommonList) {
        this.isInCommonList = isInCommonList;
    }

    public Boolean getInSecondAD() {
        return isInSecondAD;
    }

    public void setInSecondAD(Boolean isInSecondAD) {
        this.isInSecondAD = isInSecondAD;
    }

    public Boolean getInFirstAD() {
        return isInFirstAD;
    }

    public void setInFirstAD(Boolean isInFirstAD) {
        this.isInFirstAD = isInFirstAD;
    }


    public Boolean getInAllowedAD() {
        return isInAllowedAD;
    }

    public void setInAllowedAD(Boolean isInAllowedAD) {
        this.isInAllowedAD = isInAllowedAD;
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the motDePasse
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * @param motDePasse the motDePasse to set
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    /**
     * @return the dateInscription
     */
    public Timestamp getDateInscription() {
        return dateInscription;
    }

    /**
     * @param dateInscription the dateInscription to set
     */
    public void setDateInscription(Timestamp dateInscription) {
        this.dateInscription = dateInscription;
    }

    /**
     * @return the isPayed
     */
    public Integer getIsPayed() {
        return isPayed;
    }

    /**
     * @param isPayed the isPayed to set
     */
    public void setIsPayed(Integer isPayed) {
        this.isPayed = isPayed;
    }


    /**
     * @return the caves
     */
    public List<Cave> getCaves() {
        return caves;
    }

    /**
     * @param caves the caves to set
     */
    public void setCaves(List<Cave> caves) {
        this.caves = caves;
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

    /**
     * @return the producteurs
     */
    public List<Producteur> getProducteurs() {
        return producteurs;
    }

    /**
     * @param producteurs the producteurs to set
     */
    public void setProducteurs(List<Producteur> producteurs) {
        this.producteurs = producteurs;
    }


    public Boolean getIsWineproducer() {
        return isWineproducer;
    }

    public void setIsWineproducer(Boolean isWineproducer) {
        this.isWineproducer = isWineproducer;
    }

    public Boolean getIsAllowedAD() {
        return isAllowedAD;
    }

    public void setIsAllowedAD(Boolean allowedAD) {
        isAllowedAD = allowedAD;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}