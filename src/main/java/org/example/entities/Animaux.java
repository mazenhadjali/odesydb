package org.example.entities;

public class Animaux {
    public Animaux() {
    }

    public Long id;
    public int vaches_locaux;
    public int vaches_amel;
    public int vaches_enrac;
    public int vaches_total;

    public int ovins_chep;
    public int ovins_chev;
    public int ovins_bet;

    public int ruches_moderne;
    public int ruches_trad;

    public int nb_unit_elv_lap;
    public int nb_unit_elv_poul;

    public String nom_foret;
    public int mois;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVaches_locaux() {
        return vaches_locaux;
    }

    public void setVaches_locaux(int vaches_locaux) {
        this.vaches_locaux = vaches_locaux;
    }

    public int getVaches_amel() {
        return vaches_amel;
    }

    public void setVaches_amel(int vaches_amel) {
        this.vaches_amel = vaches_amel;
    }

    public int getVaches_enrac() {
        return vaches_enrac;
    }

    public void setVaches_enrac(int vaches_enrac) {
        this.vaches_enrac = vaches_enrac;
    }

    public int getVaches_total() {
        return vaches_total;
    }

    public void setVaches_total(int vaches_total) {
        this.vaches_total = vaches_total;
    }

    public int getOvins_chep() {
        return ovins_chep;
    }

    public void setOvins_chep(int ovins_chep) {
        this.ovins_chep = ovins_chep;
    }

    public int getOvins_chev() {
        return ovins_chev;
    }

    public void setOvins_chev(int ovins_chev) {
        this.ovins_chev = ovins_chev;
    }

    public int getOvins_bet() {
        return ovins_bet;
    }

    public void setOvins_bet(int ovins_bet) {
        this.ovins_bet = ovins_bet;
    }

    public int getRuches_moderne() {
        return ruches_moderne;
    }

    public void setRuches_moderne(int ruches_moderne) {
        this.ruches_moderne = ruches_moderne;
    }

    public int getRuches_trad() {
        return ruches_trad;
    }

    public void setRuches_trad(int ruches_trad) {
        this.ruches_trad = ruches_trad;
    }

    public int getNb_unit_elv_lap() {
        return nb_unit_elv_lap;
    }

    public void setNb_unit_elv_lap(int nb_unit_elv_lap) {
        this.nb_unit_elv_lap = nb_unit_elv_lap;
    }

    public int getNb_unit_elv_poul() {
        return nb_unit_elv_poul;
    }

    public void setNb_unit_elv_poul(int nb_unit_elv_poul) {
        this.nb_unit_elv_poul = nb_unit_elv_poul;
    }

    public String getNom_foret() {
        return nom_foret;
    }

    public void setNom_foret(String nom_foret) {
        this.nom_foret = nom_foret;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }
}
