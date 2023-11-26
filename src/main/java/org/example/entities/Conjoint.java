package org.example.entities;


public class Conjoint {

    public Long id;
    public int naissance;
    public int sexe;
    public int scolarite;
    public int competances;
    public int actPrincipale;

    public Conjoint() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNaissance() {
        return naissance;
    }

    public void setNaissance(int naissance) {
        this.naissance = naissance;
    }

    public int getSexe() {
        return sexe;
    }

    public void setSexe(int sexe) {
        this.sexe = sexe;
    }

    public int getScolarite() {
        return scolarite;
    }

    public void setScolarite(int scolarite) {
        this.scolarite = scolarite;
    }

    public int getCompetances() {
        return competances;
    }

    public void setCompetances(int competances) {
        this.competances = competances;
    }

    public int getActPrincipale() {
        return actPrincipale;
    }

    public void setActPrincipale(int actPrincipale) {
        this.actPrincipale = actPrincipale;
    }
}
