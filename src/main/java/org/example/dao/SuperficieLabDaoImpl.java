package org.example.dao;

import org.example.MySQLConnection;
import org.example.entities.SuperficieLab;
import org.example.gui.SingletonJFrame1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SuperficieLabDaoImpl implements SuperficieLabDao {
    Connection connection = MySQLConnection.getInstance().getConnection();

    @Override
    public Long add(SuperficieLab superficieLab) throws SQLException {
        long generatedId = -1;
        String INSERT_SUPERFICIE_SQL = "INSERT INTO superficielab ("
                + "total_pluviale, total_irrigue, grains_pluviale, grains_irrigue, "
                + "alimentation_pluviale, alimentation_irrigue, legumineuses_pluviale, legumineuses_irrigue, "
                + "legumes_pluviale, legumes_irrigue, agr_indus_pluviale, agr_indus_irrigue, "
                + "arb_fruit_pluviale, arb_fruit_irrigue, oliviers_pluviale, oliviers_irrigue, paturage,meca_agr,labour) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SUPERFICIE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, superficieLab.total_pluviale);
            preparedStatement.setDouble(2, superficieLab.total_irrigue);
            preparedStatement.setDouble(3, superficieLab.grains_pluviale);
            preparedStatement.setDouble(4, superficieLab.grains_irrigue);
            preparedStatement.setDouble(5, superficieLab.alimentation_pluviale);
            preparedStatement.setDouble(6, superficieLab.alimentation_irrigue);
            preparedStatement.setDouble(7, superficieLab.legumineuses_pluviale);
            preparedStatement.setDouble(8, superficieLab.legumineuses_irrigue);
            preparedStatement.setDouble(9, superficieLab.legumes_pluviale);
            preparedStatement.setDouble(10, superficieLab.legumes_irrigue);
            preparedStatement.setDouble(11, superficieLab.agr_indus_pluviale);
            preparedStatement.setDouble(12, superficieLab.agr_indus_irrigue);
            preparedStatement.setDouble(13, superficieLab.arb_fruit_pluviale);
            preparedStatement.setDouble(14, superficieLab.arb_fruit_irrigue);
            preparedStatement.setDouble(15, superficieLab.oliviers_pluviale);
            preparedStatement.setDouble(16, superficieLab.oliviers_irrigue);
            preparedStatement.setDouble(17, superficieLab.paturage);
            preparedStatement.setDouble(18, superficieLab.meca_agr);
            preparedStatement.setDouble(19, superficieLab.labour);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getLong(1); // Assuming "id" is the first generated key column
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId;
    }

    public Double sumfromSuperficieLabByColumn(String column, String gouvernorat, String delegation, String secteur, String complexeRes) {
        double count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT SUM(" + column + ") FROM superficielab AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idSuperficieLab");
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
                count = resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println(statement);
            e.printStackTrace();
        }

        return count;
    }

    public int countByColumnValue(String column, int value, String gouvernorat, String delegation, String secteur, String complexeRes) {

        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM superficielab AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idSuperficieLab");
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


    public int countFromSuperficieByColumn(String column, String gouvernorat, String delegation, String secteur, String complexeRes) {
        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM superficielab AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idSuperficieLab");
        // Add a condition to check if the specified column is not equal to 0
        queryBuilder.append(" WHERE " + column + " <> 0");

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

    public List<Map<String, String>> getPersonsByColumnNotEqualToZero(String column, int value, String gouvernorat, String delegation, String secteur, String complexeRes) {
        List<Map<String, String>> persons = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM personne AS p");
        queryBuilder.append(" INNER JOIN superficielab AS s ON p.idSuperficieLab = s.id");
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


    public List<Map<String, String>> getPersonsByColumnValue(String column, int value, String gouvernorat, String delegation, String secteur, String complexeRes) {
        List<Map<String, String>> persons = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM personne AS p");
        queryBuilder.append(" INNER JOIN superficielab AS s ON p.idSuperficieLab = s.id");
        queryBuilder.append(" WHERE 1 = 1");
        queryBuilder.append(" AND s." + column + " =");
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
