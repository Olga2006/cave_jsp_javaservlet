package com.cave.beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class Erreur implements Serializable {
    private Long id;
    private Long utilisateurId;
    private String codErreur;
    private String txtErreur;
    private Timestamp dateSynchro;
    private Timestamp dateCreat;

    public Erreur(Long id, Long utilisateurId, String codErreur, String txtErreur, Timestamp dateSynchro, Timestamp dateCreat) {
        this.id = id;
        this.utilisateurId = utilisateurId;
        this.codErreur = codErreur;
        this.txtErreur = txtErreur;
        this.dateSynchro = dateSynchro;
        this.dateCreat = dateCreat;
    }

    public Erreur(Long utilisateurId, String codErreur, String txtErreur) {
        this.utilisateurId = utilisateurId;
        this.codErreur = codErreur;
        this.txtErreur = txtErreur;
    }

    public Erreur() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getCodErreur() {
        return codErreur;
    }

    public void setCodErreur(String codErreur) {
        this.codErreur = codErreur;
    }

    public String getTxtErreur() {
        return txtErreur;
    }

    public void setTxtErreur(String txtErreur) {
        this.txtErreur = txtErreur;
    }

    public Timestamp getDateSynchro() {
        return dateSynchro;
    }

    public void setDateSynchro(Timestamp dateSynchro) {
        this.dateSynchro = dateSynchro;
    }

    public Timestamp getDateCreat() {
        return dateCreat;
    }

    public void setDateCreat(Timestamp dateCreat) {
        this.dateCreat = dateCreat;
    }
}
