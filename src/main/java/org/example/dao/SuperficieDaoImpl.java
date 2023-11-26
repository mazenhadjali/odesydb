package org.example.dao;

import org.example.MySQLConnection;
import org.example.entities.Superficie;
import org.example.gui.SingletonJFrame1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SuperficieDaoImpl implements SuperficieDao {
    Connection connection = MySQLConnection.getInstance().getConnection();

    private static final String INSERT_SUPERFICIE_SQL = "INSERT INTO superficie (prop, louer, surChiites, total, nombre) VALUES (?, ?, ?, ?, ?)";


    @Override
    public Long add(Superficie superficie) throws SQLException {
        long generatedId = -1;
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SUPERFICIE_SQL, Statement.RETURN_GENERATED_KEYS);

        try {
            // Set the values for the prepared statement
            preparedStatement.setDouble(1, superficie.prop);
            preparedStatement.setDouble(2, superficie.louer);
            preparedStatement.setDouble(3, superficie.surChiites);
            preparedStatement.setDouble(4, superficie.total);
            preparedStatement.setDouble(5, superficie.nombre);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getLong(1); // Assuming "id" is the first generated key column
                }
            }
        } catch (Exception e) {
            System.out.print("SQL: ");
            System.out.println(preparedStatement);
            e.printStackTrace();
        } finally {
            preparedStatement.close();
        }
        return generatedId;
    }

    public Double sumfromSuperficieByColumn(String column, String gouvernorat, String delegation, String secteur, String complexeRes) {
        double count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT SUM(" + column + ") FROM superficie AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idSuperficie");
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


    public int countRowsByTotalRange(double lowerLimit, double upperLimit, String gouvernorat, String delegation, String secteur, String complexeRes) {
        int rowCount = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM superficie AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idSuperficie");
        queryBuilder.append(" WHERE 1 = 1");

        // Add the range condition for the "total" column.
        queryBuilder.append(" AND s.total > ? AND s.total <= ?");

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

            // Set the lower and upper limits for the "total" column.
            statement.setDouble(parameterIndex++, lowerLimit);
            statement.setDouble(parameterIndex++, upperLimit);

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
                rowCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(statement);
            e.printStackTrace();
        }

        return rowCount;
    }

    public long sumRowsByTotalRange(double lowerLimit, double upperLimit, String gouvernorat, String delegation, String secteur, String complexeRes) {
        long sum = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT SUM(s.total) FROM superficie AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idSuperficie");
        queryBuilder.append(" WHERE 1 = 1");

        // Add the range condition for the "total" column.
        queryBuilder.append(" AND s.total > ? AND s.total <= ?");

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

            // Set the lower and upper limits for the "total" column.
            statement.setDouble(parameterIndex++, lowerLimit);
            statement.setDouble(parameterIndex++, upperLimit);

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
                sum = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            System.out.println(statement);
            e.printStackTrace();
        }

        return sum;
    }

    public List<Map<String, String>> getPersonsByTotalRange(double lowerLimit, double upperLimit, String gouvernorat, String delegation, String secteur, String complexeRes) {
        List<Map<String, String>> persons = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder("SELECT p.* FROM personne AS p");
        queryBuilder.append(" INNER JOIN superficie AS s ON p.idSuperficie = s.id");
        queryBuilder.append(" WHERE 1 = 1");

        // Add the range condition for the "total" column.
        queryBuilder.append(" AND s.total > ? AND s.total <= ?");

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

            // Set the lower and upper limits for the "total" column.
            statement.setDouble(parameterIndex++, lowerLimit);
            statement.setDouble(parameterIndex++, upperLimit);

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

    public List<Map<String, String>> getPersonsByTotalLowerThan(double upperLimit, String gouvernorat, String delegation, String secteur, String complexeRes) {
        List<Map<String, String>> persons = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder("SELECT p.* FROM personne AS p");
        queryBuilder.append(" INNER JOIN superficie AS s ON p.idSuperficie = s.id");
        queryBuilder.append(" WHERE 1 = 1");

        // Add the condition for the "total" column to be less than the upper limit.
        queryBuilder.append(" AND s.total < ?");

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

            // Set the upper limit for the "total" column.
            statement.setDouble(parameterIndex++, upperLimit);

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

    public List<Map<String, String>> getPersonsByTotalGreaterThan(double lowerLimit, String gouvernorat, String delegation, String secteur, String complexeRes) {
        List<Map<String, String>> persons = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder("SELECT p.* FROM personne AS p");
        queryBuilder.append(" INNER JOIN superficie AS s ON p.idSuperficie = s.id");
        queryBuilder.append(" WHERE 1 = 1");

        // Add the condition for the "total" column to be greater than the lower limit.
        queryBuilder.append(" AND s.total > ?");

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

            // Set the lower limit for the "total" column.
            statement.setDouble(parameterIndex++, lowerLimit);

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

    public int countRowsWithTotalZero(String gouvernorat, String delegation, String secteur, String complexeRes) {
        int rowCount = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM superficie AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idSuperficie");
        queryBuilder.append(" WHERE 1 = 1");

        // Add the condition for the "total" column to be equal to 0.
        queryBuilder.append(" AND s.total = 0");

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
                rowCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(statement);
            e.printStackTrace();
        }

        return rowCount;
    }

    public int countRowsWithTotalGreaterThanEqualToTen(String gouvernorat, String delegation, String secteur, String complexeRes) {
        int rowCount = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM superficie AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idSuperficie");
        queryBuilder.append(" WHERE 1 = 1");

        // Add the condition for the "total" column to be greater than or equal to 10.
        queryBuilder.append(" AND s.total > 10");

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
                rowCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(statement);
            e.printStackTrace();
        }

        return rowCount;
    }

    public long sumRowsWithTotalGreaterThanEqualToTen(String gouvernorat, String delegation, String secteur, String complexeRes) {
        long sum = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT SUM(s.total) FROM superficie AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idSuperficie");
        queryBuilder.append(" WHERE 1 = 1");

        // Add the condition for the "total" column to be greater than or equal to 10.
        queryBuilder.append(" AND s.total > 10");

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
                sum = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            System.out.println(statement);
            e.printStackTrace();
        }

        return sum;
    }

    public int countFromSuperficieByColumn(String column, String gouvernorat, String delegation, String secteur, String complexeRes) {
        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM superficie AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idSuperficie");
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

    public List<Map<String, String>> getPersonsByColumnNotEqualToZero(String column, String gouvernorat, String delegation, String secteur, String complexeRes) {
        List<Map<String, String>> persons = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM personne AS p");
        queryBuilder.append(" INNER JOIN superficie AS s ON p.idSuperficie = s.id");
        queryBuilder.append(" WHERE 1 = 1");
        queryBuilder.append(" AND s." + column + " <> 0");

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
