package org.example.entities;

public class Logement {

    public Long id;
    public int ciment;
    public int traditionnel;
    public int nb_chambres;
    public int citerne;
    public int eau_indiv;
    public int eau_coll;
    public int eau_autre;

    public Logement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCiment() {
        return ciment;
    }

    public void setCiment(int ciment) {
        this.ciment = ciment;
    }

    public int getTraditionnel() {
        return traditionnel;
    }

    public void setTraditionnel(int traditionnel) {
        this.traditionnel = traditionnel;
    }

    public int getNb_chambres() {
        return nb_chambres;
    }

    public void setNb_chambres(int nb_chambres) {
        this.nb_chambres = nb_chambres;
    }

    public int getCiterne() {
        return citerne;
    }

    public void setCiterne(int citerne) {
        this.citerne = citerne;
    }

    public int getEau_indiv() {
        return eau_indiv;
    }

    public void setEau_indiv(int eau_indiv) {
        this.eau_indiv = eau_indiv;
    }

    public int getEau_coll() {
        return eau_coll;
    }

    public void setEau_coll(int eau_coll) {
        this.eau_coll = eau_coll;
    }

    public int getEau_autre() {
        return eau_autre;
    }

    public void setEau_autre(int eau_autre) {
        this.eau_autre = eau_autre;
    }
}
