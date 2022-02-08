package com.cave.beans;

import java.io.Serializable;
import java.util.List;

public class Compartiment implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String referenceC;
    private String nom;
    private Cave cave;
    private List<Rangee> rangees;
    private Integer nbrRangees;

    public Compartiment() {
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
     * @return the referenceC
     */
    public String getReferenceC() {
        return referenceC;
    }

    /**
     * @param referenceC the referenceC to set
     */
    public void setReferenceC(String referenceC) {
        this.referenceC = referenceC;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the cave
     */
    public Cave getCave() {
        return cave;
    }

    /**
     * @param cave the cave to set
     */
    public void setCave(Cave cave) {
        this.cave = cave;
    }

    /**
     * @return the rangees
     */
    public List<Rangee> getRangees() {
        return rangees;
    }

    /**
     * @param rangees the rangees to set
     */
    public void setRangees(List<Rangee> rangees) {
        this.rangees = rangees;
    }

    public Integer getNbrRangees() {
        return nbrRangees;
    }

    public void setNbrRangees(Integer nbrRangees) {
        this.nbrRangees = nbrRangees;
    }

}
