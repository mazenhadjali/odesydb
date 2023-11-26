package org.example.dao;

import org.example.entities.Superficie;
import org.example.entities.SuperficieLab;

import java.sql.SQLException;

public interface SuperficieLabDao {

    Long add(SuperficieLab superficieLab) throws SQLException;

}
