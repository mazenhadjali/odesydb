package org.example.dao;

import org.example.MySQLConnection;
import org.example.entities.RepEnfants;
import org.example.gui.SingletonJFrame1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RepEnfantsDaoImpl implements RepEnfantsDao {
    Connection connection = MySQLConnection.getInstance().getConnection();

    private static final String INSERT_REPENFANTS_SQL = "INSERT INTO rep_enfants (total_filles, total_garcons, nb_age_G_moins_6, nb_age_F_moins_6, nb_age_G_6_18, nb_age_F_6_18, nb_age_G_18_40, nb_age_F_18_40, nb_age_plus_40, etude_G_6_18, etude_F_6_18, nb_enf_diplome_sup, nb_enf_diplome_sup_chom, nb_enf_cert_Pro, nb_enf_cert_Pro_Chom, nb_enf_sans_diplome_qualifie, nb_enf_sans_diplome_non_qualifie) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    public Long add(RepEnfants repEnfants) {
        long generatedId = -1;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REPENFANTS_SQL, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, repEnfants.getTotal_filles());
            preparedStatement.setInt(2, repEnfants.getTotal_garcons());
            preparedStatement.setInt(3, repEnfants.getNb_age_G_moins_6());
            preparedStatement.setInt(4, repEnfants.getNb_age_F_moins_6());
            preparedStatement.setInt(5, repEnfants.getNb_age_G_6_18());
            preparedStatement.setInt(6, repEnfants.getNb_age_F_6_18());
            preparedStatement.setInt(7, repEnfants.getNb_age_G_18_40());
            preparedStatement.setInt(8, repEnfants.getNb_age_F_18_40());
            preparedStatement.setInt(9, repEnfants.getNb_age_plus_40());
            preparedStatement.setInt(10, repEnfants.getEtude_G_6_18());
            preparedStatement.setInt(11, repEnfants.getEtude_F_6_18());
            preparedStatement.setInt(12, repEnfants.getNb_enf_diplome_sup());
            preparedStatement.setInt(13, repEnfants.getNb_enf_diplome_sup_chom());
            preparedStatement.setInt(14, repEnfants.getNb_enf_cert_Pro());
            preparedStatement.setInt(15, repEnfants.getNb_enf_cert_Pro_Chom());
            preparedStatement.setInt(16, repEnfants.getNb_enf_sans_diplome_qualifie());
            preparedStatement.setInt(17, repEnfants.getNb_enf_sans_diplome_non_qualifie());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getLong(1); // Assuming "id" is the first generated key column
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        return generatedId;
    }

    @Override
    public int sumfromRepEnfByColumn(String column, String gouvernorat, String delegation, String secteur, String complexeRes) {
        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT SUM(" + column + ") FROM rep_enfants AS r");
        queryBuilder.append(" INNER JOIN personne AS p ON r.id = p.idRepEnfant");
        queryBuilder.append(" WHERE 1 = 1");

        if (gouvernorat != null && !gouvernorat.equals(SingletonJFrame1.defaultOption) && !gouvernorat.isEmpty()) {
            queryBuilder.append(" AND p.gouvernorat = ?");
            if (delegation != null && !delegation.equals(SingletonJFrame1.defaultOption) && !delegation.isEmpty()) {
                queryBuilder.append(" AND p.delegation = ?");
                if (secteur != null && !secteur.equals(SingletonJFrame1.defaultOption) && !secteur.isEmpty()) {
                    queryBuilder.append(" AND p.secteur = ?");
                    if (complexeRes != null && !complexeRes.equals(SingletonJFrame1.defaultOption) && !complexeRes.isEmpty()) {
                        queryBuilder.append(" AND p.complexeRes = ?");
                    }
                }
            }
        }

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(queryBuilder.toString());
            int parameterIndex = 1;
            if (gouvernorat != null && !gouvernorat.equals(SingletonJFrame1.defaultOption) && !gouvernorat.isEmpty()) {
                statement.setString(parameterIndex++, gouvernorat);
                if (delegation != null && !delegation.equals(SingletonJFrame1.defaultOption) && !delegation.isEmpty()) {
                    statement.setString(parameterIndex++, delegation);
                    if (secteur != null && !secteur.equals(SingletonJFrame1.defaultOption) && !secteur.isEmpty()) {
                        statement.setString(parameterIndex++, secteur);
                        if (complexeRes != null && !complexeRes.equals(SingletonJFrame1.defaultOption) && !complexeRes.isEmpty()) {
                            statement.setString(parameterIndex, complexeRes);
                        }
                    }
                }
            }

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(statement);
            e.printStackTrace();
        }

        return count;
    }

    public List<Map<String, String>> getListfromRepEnfByColumnDiffZeor(String column, String gouvernorat, String delegation, String secteur, String complexeRes) {
        List<Map<String, String>> persons = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT p.nomComplet , p.cin FROM rep_enfants AS r");
        queryBuilder.append(" INNER JOIN personne AS p ON r.id = p.idRepEnfant");
        queryBuilder.append(" WHERE ");
        queryBuilder.append(column);
        queryBuilder.append(" > 0");

        if (gouvernorat != null && !gouvernorat.equals(SingletonJFrame1.defaultOption) && !gouvernorat.isEmpty()) {
            queryBuilder.append(" AND p.gouvernorat = ?");
            if (delegation != null && !delegation.equals(SingletonJFrame1.defaultOption) && !delegation.isEmpty()) {
                queryBuilder.append(" AND p.delegation = ?");
                if (secteur != null && !secteur.equals(SingletonJFrame1.defaultOption) && !secteur.isEmpty()) {
                    queryBuilder.append(" AND p.secteur = ?");
                    if (complexeRes != null && !complexeRes.equals(SingletonJFrame1.defaultOption) && !complexeRes.isEmpty()) {
                        queryBuilder.append(" AND p.complexeRes = ?");
                    }
                }
            }
        }

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(queryBuilder.toString());
            int parameterIndex = 1;
            if (gouvernorat != null && !gouvernorat.equals(SingletonJFrame1.defaultOption) && !gouvernorat.isEmpty()) {
                statement.setString(parameterIndex++, gouvernorat);
                if (delegation != null && !delegation.equals(SingletonJFrame1.defaultOption) && !delegation.isEmpty()) {
                    statement.setString(parameterIndex++, delegation);
                    if (secteur != null && !secteur.equals(SingletonJFrame1.defaultOption) && !secteur.isEmpty()) {
                        statement.setString(parameterIndex++, secteur);
                        if (complexeRes != null && !complexeRes.equals(SingletonJFrame1.defaultOption) && !complexeRes.isEmpty()) {
                            statement.setString(parameterIndex, complexeRes);
                        }
                    }
                }
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("nomComplet");
                String cin = resultSet.getString("cin");
                persons.add(Map.of("name", name, "cin", cin));
            }
        } catch (SQLException e) {
            System.out.println(statement);
            e.printStackTrace();
        }
        return persons;
    }
}
