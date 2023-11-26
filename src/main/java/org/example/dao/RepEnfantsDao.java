package org.example.dao;

import org.example.entities.RepEnfants;

import java.sql.SQLException;

public interface RepEnfantsDao {

    Long add(RepEnfants repEnfants) throws SQLException;

    int sumfromRepEnfByColumn(String column, String gouvernorat, String delegation, String secteur, String complexeRes);


}
