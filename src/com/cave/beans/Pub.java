package com.cave.beans;

import java.sql.Timestamp;

public class Pub {
    private Long id;
    private Long idUtilisateur;
    private String codeHtml;
    private String image;
    private String url;
    private Boolean isFirst;
    private Boolean isSecond;
    private Timestamp dateCreat;
    private Timestamp dateSynchro;


    public Pub() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Pub(Long id, Long idUtilisateur, String codeHtml, String image, String url, Boolean isFirst, Boolean isSecond) {
        this.id = id;
        this.idUtilisateur = idUtilisateur;
        this.codeHtml = codeHtml;
        this.image = image;
        this.url = url;
        this.isFirst = isFirst;
        this.isSecond = isSecond;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getCodeHtml() {
        return codeHtml;
    }

    public void setCodeHtml(String codeHtml) {
        this.codeHtml = codeHtml;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Boolean first) {
        isFirst = first;
    }

    public Boolean getIsSecond() {
        return isSecond;
    }

    public void setIsSecond(Boolean second) {
        isSecond = second;
    }

    public Timestamp getDateCreat() {
        return dateCreat;
    }

    public void setDateCreat(Timestamp dateCreat) {
        this.dateCreat = dateCreat;
    }

    public Timestamp getDateSynchro() {
        return dateSynchro;
    }

    public void setDateSynchro(Timestamp dateSynchro) {
        this.dateSynchro = dateSynchro;
    }
}
