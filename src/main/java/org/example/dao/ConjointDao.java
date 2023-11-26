package org.example.dao;

import org.example.entities.Conjoint;

import java.sql.SQLException;

public interface ConjointDao {

    Long add(Conjoint conjoint) throws SQLException;

    long countConjointsMale(String gouvernorat, String delegation, String secteur, String complexeRes);

    long countConjointsFemale(String gouvernorat, String delegation, String secteur, String complexeRes);

    int countConjointsByScolarite(int sexe, int scolariteValue, String gouvernorat, String delegation, String secteur, String complexeRes);

    int countConjointsByActPrincipale(int sexe, int actPrincipale, String gouvernorat, String delegation, String secteur, String complexeRes);

    int countConjointsBycompetances(int sexe, int competances, String gouvernorat, String delegation, String secteur, String complexeRes);

    Conjoint getById(Long id);


}
