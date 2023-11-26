package org.example.dao;

import org.example.entities.Superficie;

import java.sql.SQLException;

public interface SuperficieDao {

    Long add(Superficie superficie) throws SQLException;

}
