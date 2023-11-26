package org.example.dao;

import org.example.MySQLConnection;
import org.example.entities.Logement;
import org.example.gui.SingletonJFrame1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogementDaoImpl implements LogementDao {

    Connection connection = MySQLConnection.getInstance().getConnection();

    private static final String INSERT_LOGEMENT_SQL =
            "INSERT INTO logement (ciment, traditionnel, nb_chambres, citerne, eau_indiv, eau_coll, eau_autre) VALUES (?, ?, ?, ?, ?, ?, ?)";

    @Override
    public Long add(Logement logement) throws SQLException {
        long generatedId = -1;
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LOGEMENT_SQL, Statement.RETURN_GENERATED_KEYS);

        try {
            preparedStatement.setInt(1, logement.ciment);
            preparedStatement.setInt(2, logement.traditionnel);
            preparedStatement.setInt(3, logement.nb_chambres);
            preparedStatement.setInt(4, logement.citerne);
            preparedStatement.setInt(5, logement.eau_indiv);
            preparedStatement.setInt(6, logement.eau_coll);
            preparedStatement.setInt(7, logement.eau_autre);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getLong(1); // Assuming "id" is the first generated key column
                }
            }
        } catch (SQLException e) {
            System.out.print("SQL: ");
            System.out.println(preparedStatement);
            System.out.println(logement.eau_autre);
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return generatedId;
    }

    @Override
    public int sumfromLogementByColumn(String column, String gouvernorat, String delegation, String secteur, String complexeRes) {
        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT SUM(" + column + ") FROM logement AS l");
        queryBuilder.append(" INNER JOIN personne AS p ON l.id = p.idLogement");
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

    @Override
    public int countByColumnValue(String column, String value, String gouvernorat, String delegation, String secteur, String complexeRes) {

        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM logement AS l");
        queryBuilder.append(" INNER JOIN personne AS p ON l.id = p.idLogement");
        queryBuilder.append(" WHERE 1 = 1");

        if (column != null && !column.isEmpty() && value != null && !value.isEmpty()) {
            queryBuilder.append(" AND ").append(column).append(" = ?");
        }

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
            if (column != null && !column.isEmpty() && value != null && !value.isEmpty()) {
                statement.setString(1, value);
            }
            int parameterIndex = 2;
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

            System.out.println(statement);
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

    public int countByColumnValue(String column, int value, String gouvernorat, String delegation, String secteur, String complexeRes) {

        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM logement AS l");
        queryBuilder.append(" INNER JOIN personne AS p ON l.id = p.idLogement");
        queryBuilder.append(" WHERE 1 = 1");

        if (column != null && !column.isEmpty()) {
            queryBuilder.append(" AND ").append(column).append(" = ?");
        }

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
            if (column != null && !column.isEmpty()) {
                statement.setInt(1, value);
            }
            int parameterIndex = 2;
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

    public int countDistinctValuesByColumn(String column, String gouvernorat, String delegation, String secteur, String complexeRes) {

        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM logement AS l");
        queryBuilder.append(" INNER JOIN personne AS p ON l.id = p.idLogement");
        queryBuilder.append(" WHERE " + column + " <> '0.0' ");


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

    public List<Map<String, String>> getPersonsByColumnNotEqualTo(String column, int value, String gouvernorat, String delegation, String secteur, String complexeRes) {
        List<Map<String, String>> persons = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM personne AS p");
        queryBuilder.append(" INNER JOIN logement AS s ON p.idLogement = s.id");
        queryBuilder.append(" WHERE 1 = 1");
        queryBuilder.append(" AND s." + column + " <>");
        queryBuilder.append(value + " ");

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
