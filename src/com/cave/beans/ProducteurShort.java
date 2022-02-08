package com.cave.beans;

public class ProducteurShort {

    private Long id;
    private String nom;
    private String adresse;
    private String contact;
    private Long idUtilisateur;
    private Boolean byWineproducer;
    private Boolean isAllowedCL;
    private String url;

    public ProducteurShort() {
    }

    public ProducteurShort(Long id, String nom, String adresse, String contact, Long idUtilisateur, String url, Boolean isAllowedCL) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.contact = contact;
        this.idUtilisateur = idUtilisateur;
        this.url = url;
        this.isAllowedCL = isAllowedCL;
    }

    public ProducteurShort(Long id, String nom, String adresse, String contact, Long idUtilisateur, Boolean byWineproducer, Boolean isAllowedCL, String url) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.contact = contact;
        this.idUtilisateur = idUtilisateur;
        this.byWineproducer = byWineproducer;
        this.isAllowedCL = isAllowedCL;
        this.url = url;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Boolean getByWineproducer() {
        return byWineproducer;
    }

    public void setByWineproducer(Boolean byWineproducer) {
        this.byWineproducer = byWineproducer;
    }

    public Boolean getAllowedCL() {
        return isAllowedCL;
    }

    public void setAllowedCL(Boolean allowedCL) {
        isAllowedCL = allowedCL;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
