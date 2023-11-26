package org.example.dao;

import org.example.entities.Animaux;
import org.example.entities.SuperficieLab;

import java.sql.SQLException;

public interface AnimauxDao {

    Long add(Animaux animaux) throws SQLException;

}
