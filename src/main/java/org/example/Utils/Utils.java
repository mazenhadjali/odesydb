package org.example.Utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.example.entities.*;

public class Utils {

    final static int firstcol = 2;

    public static int getCellValueAsInt(Cell cell) {
        if (cell == null) {
            return 0;
        }

        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return (int) cell.getNumericCellValue();
            } else {
                String cellValue = cell.toString();
                return (int) Double.parseDouble(cellValue);
            }
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double getCellValueAsDouble(Cell cell) {
        if (cell == null) {
            return 0.0; // Default value for a null cell
        }

        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return cell.getNumericCellValue();
            } else {
                String cellValue = cell.toString();
                return Double.parseDouble(cellValue);
            }
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }


    public static Personne getPersonneFromRow(Row row) {
        Personne personne = new Personne();
        personne.setGouvernorat((row.getCell(1) != null && !row.getCell(1).toString().isEmpty()) ? row.getCell(1).toString().trim() : "N/A");
        personne.setDelegation((row.getCell(2) != null && !row.getCell(2).toString().isEmpty()) ? row.getCell(2).toString().trim() : "N/A");
        personne.setSecteur(row.getCell(firstcol + 1).toString().trim());
        personne.setComplexeRes(row.getCell(firstcol + 2).toString().trim());
        personne.setNomComplet(row.getCell(firstcol + 3).toString().trim());
        personne.setSexe((getCellValueAsInt(row.getCell(firstcol + 4)) == 1) ? 1 : 2);
        personne.setNaissance(getCellValueAsInt(row.getCell(firstcol + 5)));
        personne.setCin((row.getCell(firstcol + 6) != null ? row.getCell(firstcol + 6).toString() : "").replace(".0", ""));
        personne.setScolarite(getCellValueAsInt(row.getCell(firstcol + 7)));
        personne.setActPrincipale(getCellValueAsInt(row.getCell(firstcol + 8)));
        personne.setActSecondaire(getCellValueAsInt(row.getCell(firstcol + 9)));
        personne.setEtatSociale(getCellValueAsInt(row.getCell(firstcol + 10)));
        personne.setExploitation(getCellValueAsInt(row.getCell(firstcol + 11)));
        personne.setPresence(getCellValueAsInt(row.getCell(firstcol + 12)));
        personne.setMiniProjet(getCellValueAsInt(row.getCell(firstcol + 13)));
        personne.setNb_handicap(getCellValueAsInt(row.getCell(firstcol + 19)));
        personne.setStruct_pro(getCellValueAsInt(row.getCell(firstcol + 79)));

        return personne;
    }

    public static Conjoint getConjointFromRow(Row row) {
        Conjoint conjoint = new Conjoint();

        conjoint.setNaissance(getCellValueAsInt(row.getCell(firstcol + 14)));
        conjoint.setSexe((getCellValueAsInt(row.getCell(firstcol + 15))));
        int scolariteValue = getCellValueAsInt(row.getCell(firstcol + 16));
        conjoint.setScolarite(scolariteValue);

        int activiteValue = getCellValueAsInt(row.getCell(firstcol + 17));
        conjoint.setActPrincipale(activiteValue);

        int competanceValue = getCellValueAsInt(row.getCell(firstcol + 18));
        conjoint.setCompetances(competanceValue);

        return conjoint;
    }


    public static RepEnfants getRepEnfantsFromRow(Row row) {
        RepEnfants repEnfants = new RepEnfants();


        int Nb_age_G_moins_6 = getCellValueAsInt(row.getCell(firstcol + 22));
        repEnfants.setNb_age_G_moins_6(Nb_age_G_moins_6);
        int Nb_age_F_moins_6 = getCellValueAsInt(row.getCell(firstcol + 23));
        repEnfants.setNb_age_F_moins_6(Nb_age_F_moins_6);

        int Nb_age_G_6_18 = getCellValueAsInt(row.getCell(firstcol + 24));
        repEnfants.setNb_age_G_6_18(Nb_age_G_6_18);
        int Nb_age_F_6_18 = getCellValueAsInt(row.getCell(firstcol + 25));
        repEnfants.setNb_age_F_6_18(Nb_age_F_6_18);

        int Nb_age_G_18_40 = getCellValueAsInt(row.getCell(firstcol + 26));
        repEnfants.setNb_age_G_18_40(Nb_age_G_18_40);
        int Nb_age_F_18_40 = getCellValueAsInt(row.getCell(firstcol + 27));
        repEnfants.setNb_age_F_18_40(Nb_age_F_18_40);

        int Nb_age_plus_40 = getCellValueAsInt(row.getCell(firstcol + 28));
        repEnfants.setNb_age_plus_40(Nb_age_plus_40);

        repEnfants.setTotal_garcons(Nb_age_G_moins_6 + Nb_age_G_6_18 + Nb_age_G_18_40 + Nb_age_plus_40);
        repEnfants.setTotal_filles(Nb_age_F_moins_6 + Nb_age_F_6_18 + Nb_age_F_18_40);

//        repEnfants.setTotal_garcons(getCellValueAsInt(row.getCell(firstcol + 20)));
//        repEnfants.setTotal_filles(getCellValueAsInt(row.getCell(firstcol + 21)));


        repEnfants.setEtude_G_6_18(getCellValueAsInt(row.getCell(firstcol + 29)));
        repEnfants.setEtude_F_6_18(getCellValueAsInt(row.getCell(firstcol + 30)));

        repEnfants.setNb_enf_diplome_sup(getCellValueAsInt(row.getCell(firstcol + 31)));
        repEnfants.setNb_enf_diplome_sup_chom(getCellValueAsInt(row.getCell(firstcol + 32)));

        repEnfants.setNb_enf_cert_Pro(getCellValueAsInt(row.getCell(firstcol + 33)));
        repEnfants.setNb_enf_cert_Pro_Chom(getCellValueAsInt(row.getCell(firstcol + 34)));

        repEnfants.setNb_enf_sans_diplome_qualifie(getCellValueAsInt(row.getCell(firstcol + 35)));
        repEnfants.setNb_enf_sans_diplome_non_qualifie(getCellValueAsInt(row.getCell(firstcol + 36)));

        return repEnfants;
    }

    public static Logement getLogementFromRow(Row row) {

        Logement logement = new Logement();

        int ciment = getCellValueAsInt(row.getCell(firstcol + 37));
        logement.setCiment(ciment == 1 ? ciment : 2);

        int trad = getCellValueAsInt(row.getCell(firstcol + 38));
        logement.setTraditionnel(trad == 1 ? trad : 2);

        int nbCh = getCellValueAsInt(row.getCell(firstcol + 39));
        logement.setNb_chambres(nbCh);

        int citerne = getCellValueAsInt(row.getCell(firstcol + 40));
        logement.setCiterne(citerne == 1 ? citerne : 2);

        int eau_indiv = getCellValueAsInt(row.getCell(firstcol + 41));
        logement.setEau_indiv(eau_indiv);

        int eau_coll = getCellValueAsInt(row.getCell(firstcol + 42));
        logement.setEau_coll(eau_coll);

        int eau_autre = getCellValueAsInt(row.getCell(firstcol + 43));
        logement.setEau_autre(eau_autre);

        return logement;

    }

    public static Superficie getSuperficieFromRow(Row row) {

        Superficie superficie = new Superficie();

        Double prop = getCellValueAsDouble(row.getCell(firstcol + 44));
        Double louer = getCellValueAsDouble(row.getCell(firstcol + 45));
        Double surChiites = getCellValueAsDouble(row.getCell(firstcol + 46));
//        Double total = getCellValueAsDouble(row.getCell(firstcol + 47));
        Double total = prop + louer + surChiites;
        Double nombre = getCellValueAsDouble(row.getCell(firstcol + 48));

        superficie.prop = prop;
        superficie.louer = louer;
        superficie.surChiites = surChiites;
        superficie.total = total;
        superficie.nombre = nombre;

        return superficie;

    }

    public static SuperficieLab getSuperficielabFromRow(Row row) {

        SuperficieLab superficieLab = new SuperficieLab();

//        Double total_pluviale = getCellValueAsDouble(row.getCell(firstcol + 49));
//        Double total_irrigue = getCellValueAsDouble(row.getCell(firstcol + 50));

        Double grains_pluviale = getCellValueAsDouble(row.getCell(firstcol + 51));
        Double grains_irrigue = getCellValueAsDouble(row.getCell(firstcol + 52));
        Double alimentation_pluviale = getCellValueAsDouble(row.getCell(firstcol + 53));
        Double alimentation_irrigue = getCellValueAsDouble(row.getCell(firstcol + 54));
        Double legumineuses_pluviale = getCellValueAsDouble(row.getCell(firstcol + 55));
        Double legumineuses_irrigue = getCellValueAsDouble(row.getCell(firstcol + 56));
        Double legumes_pluviale = getCellValueAsDouble(row.getCell(firstcol + 57));
        Double legumes_irrigue = getCellValueAsDouble(row.getCell(firstcol + 58));
        Double agr_indus_pluviale = getCellValueAsDouble(row.getCell(firstcol + 59));
        Double agr_indus_irrigue = getCellValueAsDouble(row.getCell(firstcol + 60));
        Double arb_fruit_pluviale = getCellValueAsDouble(row.getCell(firstcol + 61));
        Double arb_fruit_irrigue = getCellValueAsDouble(row.getCell(firstcol + 62));
        Double oliviers_pluviale = getCellValueAsDouble(row.getCell(firstcol + 63));
        Double oliviers_irrigue = getCellValueAsDouble(row.getCell(firstcol + 64));
        Double paturage = getCellValueAsDouble(row.getCell(firstcol + 65));

        Double total_pluviale = grains_pluviale + alimentation_pluviale + legumineuses_pluviale + legumes_pluviale + agr_indus_pluviale + arb_fruit_pluviale + oliviers_pluviale;
        Double total_irrigue = grains_irrigue + alimentation_irrigue + legumineuses_irrigue + legumes_irrigue + agr_indus_irrigue + arb_fruit_irrigue + oliviers_irrigue;

        int meca_agr = getCellValueAsInt(row.getCell(firstcol + 66));
        int labour = getCellValueAsInt(row.getCell(firstcol + 67));

        superficieLab.total_pluviale = total_pluviale;
        superficieLab.total_irrigue = total_irrigue;
        superficieLab.grains_pluviale = grains_pluviale;
        superficieLab.grains_irrigue = grains_irrigue;
        superficieLab.alimentation_pluviale = alimentation_pluviale;
        superficieLab.alimentation_irrigue = alimentation_irrigue;
        superficieLab.legumineuses_pluviale = legumineuses_pluviale;
        superficieLab.legumineuses_irrigue = legumineuses_irrigue;
        superficieLab.legumes_pluviale = legumes_pluviale;
        superficieLab.legumes_irrigue = legumes_irrigue;
        superficieLab.agr_indus_pluviale = agr_indus_pluviale;
        superficieLab.agr_indus_irrigue = agr_indus_irrigue;
        superficieLab.arb_fruit_pluviale = arb_fruit_pluviale;
        superficieLab.arb_fruit_irrigue = arb_fruit_irrigue;
        superficieLab.oliviers_pluviale = oliviers_pluviale;
        superficieLab.oliviers_irrigue = oliviers_irrigue;
        superficieLab.paturage = paturage;
        superficieLab.meca_agr = ((meca_agr > 5) | (meca_agr < 0)) ? 0 : meca_agr;
        superficieLab.labour = ((labour > 5) | (labour < 1)) ? 1 : labour;

        return superficieLab;
    }


    public static Animaux getAnimeauFromRow(Row row) {

        Animaux animaux = new Animaux();

        int vaches_locaux = getCellValueAsInt(row.getCell(firstcol + 68));
        int vaches_amel = getCellValueAsInt(row.getCell(firstcol + 69));
        int vaches_enrac = getCellValueAsInt(row.getCell(firstcol + 70));
        int vaches_total = vaches_locaux + vaches_amel + vaches_enrac;

        int ovins_chep = getCellValueAsInt(row.getCell(firstcol + 72));
        int ovins_chev = getCellValueAsInt(row.getCell(firstcol + 73));
        int ovins_bet = getCellValueAsInt(row.getCell(firstcol + 74));
        int ruches_moderne = getCellValueAsInt(row.getCell(firstcol + 75));
        int ruches_trad = getCellValueAsInt(row.getCell(firstcol + 76));
        int nb_unit_elv_lap = getCellValueAsInt(row.getCell(firstcol + 77));
        int nb_unit_elv_poul = getCellValueAsInt(row.getCell(firstcol + 78));
        String nom_foret = (row.getCell(firstcol + 80) != null) ? row.getCell(firstcol + 80).toString().trim().replaceAll("\\s{2,}", " ") : "";
        int mois = getCellValueAsInt(row.getCell(firstcol + 81));

        animaux.vaches_locaux = vaches_locaux;
        animaux.vaches_amel = vaches_amel;
        animaux.vaches_enrac = vaches_enrac;
        animaux.vaches_total = vaches_total;
        animaux.ovins_chep = ovins_chep;
        animaux.ovins_chev = ovins_chev;
        animaux.ovins_bet = ovins_bet;
        animaux.ruches_moderne = ruches_moderne;
        animaux.ruches_trad = ruches_trad;
        animaux.nb_unit_elv_lap = nb_unit_elv_lap;
        animaux.nb_unit_elv_poul = nb_unit_elv_poul;
        animaux.nom_foret = nom_foret;
        animaux.mois = mois;
        return animaux;
    }

    public static boolean isRowEmptyPercentage(Row row, double threshold) {
        // Check if the row is null
        if (row == null) {
            return false;
        }
        int totalCells = Math.min(row.getPhysicalNumberOfCells(), 84); // Check only the first 84 cells

        int emptyCellCount = 0;
        for (int i = 0; i < totalCells; i++) {
            Cell cell = row.getCell(i);
            if (cell == null || cell.getCellType() == CellType.BLANK) {
                emptyCellCount++;
            }
        }

        double emptyPercentage = (double) emptyCellCount / totalCells;
        return emptyPercentage < threshold;
    }


}
