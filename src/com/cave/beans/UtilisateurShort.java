package com.cave.beans;

import com.cave.tools.Const;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class UtilisateurShort implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nom;
    private String email;
    private String motDePasse;
    private Timestamp dateInscription;
    private Integer isPayed;
    private Boolean isWineproducer;
    private Boolean isAllowedAD ;
    private Boolean isInCommonList = getIsPayed() != null && getIsPayed() >= Const.PAID_TO_COMMON_LIST;
    private Boolean isInSecondAD = getIsPayed() != null && getIsPayed() >= Const.PAID_TO_SECOND_AD;
    private Boolean isInFirstAD = getIsPayed() != null && getIsPayed() >= Const.PAID_TO_FIRST_AD;
    private Boolean isInAllowedAD = getIsPayed() != null && getIsPayed() >= Const.PAID_TO_IN_ALLOWED_AD;
    private Boolean isAdmin = getEmail() != null && Const.EMAIL_ADMIN.equals(getEmail());

    public UtilisateurShort(Long id, String nom, String email, String motDePasse, Timestamp dateInscription, Integer isPayed, Boolean isWineproducer, Boolean isAllowedAD, Boolean isInCommonList, Boolean isInSecondAD, Boolean isInFirstAD, Boolean isInAllowedAD, Boolean isAdmin) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.dateInscription = dateInscription;
        this.isPayed = isPayed;
        this.isWineproducer = isWineproducer;
        this.isAllowedAD = isAllowedAD;
        this.isInCommonList = isInCommonList;
        this.isInSecondAD = isInSecondAD;
        this.isInFirstAD = isInFirstAD;
        this.isInAllowedAD = isInAllowedAD;
        this.isAdmin = isAdmin;
    }

    public UtilisateurShort() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Timestamp getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Timestamp dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Integer getIsPayed() {
        return isPayed;
    }

    public void setIsPayed(Integer isPayed) {
        this.isPayed = isPayed;
    }

    public Boolean getIsWineproducer() {
        return isWineproducer;
    }

    public void setIsWineproducer(Boolean wineproducer) {
        isWineproducer = wineproducer;
    }

    public Boolean getInCommonList() {
        return isInCommonList;
    }

    public void setInCommonList(Boolean inCommonList) {
        isInCommonList = inCommonList;
    }

    public Boolean getInSecondAD() {
        return isInSecondAD;
    }

    public void setInSecondAD(Boolean inSecondAD) {
        isInSecondAD = inSecondAD;
    }

    public Boolean getInFirstAD() {
        return isInFirstAD;
    }

    public void setInFirstAD(Boolean inFirstAD) {
        isInFirstAD = inFirstAD;
    }

    public Boolean getIsAllowedAD() {
        return isAllowedAD;
    }

    public void setIsAllowedAD(Boolean allowedAD) {
        isAllowedAD = allowedAD;
    }

    public Boolean getInAllowedAD() {
        return isInAllowedAD;
    }

    public void setInAllowedAD(Boolean inAllowedAD) {
        isInAllowedAD = inAllowedAD;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }
}