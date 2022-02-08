package com.cave.beans;

public class PositionShort {

    private Long id;
    private Long idBouteille;
    private Long idRangee;
    private Integer referenceP;
    private Integer referenceR;
    private String referenceC;
    private String nomCave;

    public PositionShort() {
    }

    public PositionShort(Long id, Long idBouteille, Long idRangee, Integer referenceP, Integer referenceR, String referenceC, String nomCave) {
        this.id = id;
        this.idBouteille = idBouteille;
        this.idRangee = idRangee;
        this.referenceP = referenceP;
        this.referenceR = referenceR;
        this.referenceC = referenceC;
        this.nomCave = nomCave;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdBouteille() {
        return idBouteille;
    }

    public void setIdBouteille(Long idBouteille) {
        this.idBouteille = idBouteille;
    }

    public Long getIdRangee() {
        return idRangee;
    }

    public void setIdRangee(Long idRangee) {
        this.idRangee = idRangee;
    }

    public Integer getReferenceP() {
        return referenceP;
    }

    public void setReferenceP(Integer referenceP) {
        this.referenceP = referenceP;
    }

    public Integer getReferenceR() {
        return referenceR;
    }

    public void setReferenceR(Integer referenceR) {
        this.referenceR = referenceR;
    }

    public String getReferenceC() {
        return referenceC;
    }

    public void setReferenceC(String referenceC) {
        this.referenceC = referenceC;
    }

    public String getNomCave() {
        return nomCave;
    }

    public void setNomCave(String nomCave) {
        this.nomCave = nomCave;
    }
}
