package org.example.entities;

public class SuperficieLab {

    public Long id;
    public Double total_pluviale;
    public Double total_irrigue;

    public Double grains_pluviale;
    public Double grains_irrigue;

    public Double alimentation_pluviale;
    public Double alimentation_irrigue;

    public Double legumineuses_pluviale;
    public Double legumineuses_irrigue;

    public Double legumes_pluviale;
    public Double legumes_irrigue;

    public Double agr_indus_pluviale;
    public Double agr_indus_irrigue;

    public Double arb_fruit_pluviale;
    public Double arb_fruit_irrigue;

    public Double oliviers_pluviale;
    public Double oliviers_irrigue;

    public Double paturage;
    public int meca_agr;
    public int labour;

    public SuperficieLab() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
