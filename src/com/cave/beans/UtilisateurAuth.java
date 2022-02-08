package com.cave.beans;

import java.io.Serializable;

public class UtilisateurAuth implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nom;
    private String email;
    private String motDePasse;
    private Boolean isWineproducer;
    private Boolean isAllowedAD;

    public UtilisateurAuth() {
    }

    public UtilisateurAuth(Long id, String nom, String email, String motDePasse, Boolean isWineproducer, Boolean isAllowedAD) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.isWineproducer = isWineproducer;
        this.isAllowedAD = isAllowedAD;
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

    public Boolean getIsWineproducer() {
        return isWineproducer;
    }

    public void setIsWineproducer(Boolean wineproducer) {
        isWineproducer = wineproducer;
    }

    public Boolean getIsAllowedAD() {
        return isAllowedAD;
    }

    public void setIsAllowedAD(Boolean allowedAD) {
        isAllowedAD = allowedAD;
    }
}