package org.example.dao;

import org.example.entities.Logement;
import org.example.entities.RepEnfants;

import java.sql.SQLException;

public interface LogementDao {

    Long add(Logement logement) throws SQLException;

    int sumfromLogementByColumn(String column, String gouvernorat, String delegation, String secteur, String complexeRes);

    int countByColumnValue(String column, String value, String gouvernorat, String delegation, String secteur, String complexeRes);


}
