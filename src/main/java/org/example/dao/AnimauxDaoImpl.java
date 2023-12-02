package org.example.dao;

import org.example.MySQLConnection;
import org.example.entities.Animaux;
import org.example.gui.SingletonJFrame1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnimauxDaoImpl implements AnimauxDao {
    Connection connection = MySQLConnection.getInstance().getConnection();

    @Override
    public Long add(Animaux animaux) throws SQLException {
        long generatedId = -1;
        String INSERT_ANIMAUX_SQL = "INSERT INTO animaux ("
                + "vaches_locaux, vaches_amel, vaches_enrac, vaches_total, "
                + "ovins_chep, ovins_chev, ovins_bet, "
                + "ruches_moderne, ruches_trad, "
                + "nb_unit_elv_lap, nb_unit_elv_poul, "
                + "nom_foret, mois) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ANIMAUX_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, animaux.vaches_locaux);
            preparedStatement.setInt(2, animaux.vaches_amel);
            preparedStatement.setInt(3, animaux.vaches_enrac);
            preparedStatement.setInt(4, animaux.vaches_total);
            preparedStatement.setInt(5, animaux.ovins_chep);
            preparedStatement.setInt(6, animaux.ovins_chev);
            preparedStatement.setInt(7, animaux.ovins_bet);
            preparedStatement.setInt(8, animaux.ruches_moderne);
            preparedStatement.setInt(9, animaux.ruches_trad);
            preparedStatement.setInt(10, animaux.nb_unit_elv_lap);
            preparedStatement.setInt(11, animaux.nb_unit_elv_poul);
            preparedStatement.setString(12, animaux.nom_foret);
            preparedStatement.setInt(13, animaux.mois);

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


    public int sumfromAnimauxByColumn(String column, String gouvernorat, String delegation, String secteur, String complexeRes) {
        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT SUM(" + column + ") FROM animaux AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idAnimaux");
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

    public int countDistinctValuesByColumn(String column, String gouvernorat, String delegation, String secteur, String complexeRes) {

        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM animaux AS l");
        queryBuilder.append(" INNER JOIN personne AS p ON l.id = p.idAnimaux");
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

    public int countByColumn(String column, String gouvernorat, String delegation, String secteur, String complexeRes) {
        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM animaux AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idAnimaux");
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

    public List<Map<String, String>> getPersonsByColumnNotEqualTo(String column, int value, String gouvernorat, String delegation, String secteur, String complexeRes) {
        List<Map<String, String>> persons = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM personne AS p");
        queryBuilder.append(" INNER JOIN animaux AS s ON p.idAnimaux = s.id");
        queryBuilder.append(" WHERE s." + column + " <>");
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

    public int countByColumnNotEqualsToZero(String column, String gouvernorat, String delegation, String secteur, String complexeRes) {
        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM animaux AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idAnimaux");
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

    public int countByColumngratherThanZero(String column, String gouvernorat, String delegation, String secteur, String complexeRes) {
        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM animaux AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idAnimaux");
        // Add a condition to check if the specified column is not equal to 0
        queryBuilder.append(" WHERE " + column + " > 0");

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

    public int sumtotalvachinfeg5(String gouvernorat, String delegation, String secteur, String complexeRes) {
        long count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT SUM(s.vaches_total) FROM animaux AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idAnimaux");
        // Add a condition to check if the specified column is not equal to 0
        queryBuilder.append(" WHERE vaches_total <= 5 ");

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
                count = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            System.out.println(statement);
            e.printStackTrace();
        }

        return Math.toIntExact(count);
    }

    public int countpersonscahcehif5(String gouvernorat, String delegation, String secteur, String complexeRes) {
        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM animaux AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idAnimaux");
        // Add a condition to check if the specified column is not equal to 0
        queryBuilder.append(" WHERE vaches_total <= 5 ");

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

        return Math.toIntExact(count);
    }

    public int countpersonschepsinf30(String gouvernorat, String delegation, String secteur, String complexeRes) {
        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM animaux AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idAnimaux");
        // Add a condition to check if the specified column is not equal to 0
        queryBuilder.append(" WHERE 30 >= ( s.ovins_chep + s.ovins_chev + s.ovins_bet ) ");

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

        return Math.toIntExact(count);
    }

    public int sumchepsinf30(String gouvernorat, String delegation, String secteur, String complexeRes) {
        long count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT SUM(s.ovins_chep + s.ovins_chev + s.ovins_bet ) FROM animaux AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idAnimaux");
        // Add a condition to check if the specified column is not equal to 0
        queryBuilder.append(" WHERE 30 >= ( s.ovins_chep + s.ovins_chev + s.ovins_bet ) ");

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

        return Math.toIntExact(count);
    }


    public int sumchepstotalcheps(String gouvernorat, String delegation, String secteur, String complexeRes) {
        long count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT SUM(s.ovins_chep + s.ovins_chev + s.ovins_bet ) FROM animaux AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idAnimaux");
        // Add a condition to check if the specified column is not equal to 0
        queryBuilder.append(" WHERE 1=1 ");

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

        return Math.toIntExact(count);
    }


    public int countotalpersonsarea(String gouvernorat, String delegation, String secteur, String complexeRes) {
        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM animaux AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idAnimaux");
        queryBuilder.append(" WHERE 1=1");

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

        return Math.toIntExact(count);
    }


    public int sumtotalvaches(String gouvernorat, String delegation, String secteur, String complexeRes) {
        long count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT SUM(s.vaches_total) FROM animaux AS s");
        queryBuilder.append(" INNER JOIN personne AS p ON s.id = p.idAnimaux");
        queryBuilder.append(" WHERE 1=1 ");

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
                count = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            System.out.println(statement);
            e.printStackTrace();
        }

        return Math.toIntExact(count);
    }
}
