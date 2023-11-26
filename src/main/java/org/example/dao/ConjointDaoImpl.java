package org.example.dao;

import org.example.MySQLConnection;
import org.example.entities.Conjoint;
import org.example.gui.SingletonJFrame1;

import java.sql.*;

public class ConjointDaoImpl implements ConjointDao {
    Connection connection = MySQLConnection.getInstance().getConnection();

    private static final String INSERT_CONJOINT_SQL = "INSERT INTO conjoint (competances, actPrincipale, sexe, naissance,scolarite) VALUES (?, ?, ?, ?, ?)";

    @Override
    public Long add(Conjoint conjoint) throws SQLException {


        long generatedId = -1;
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CONJOINT_SQL, Statement.RETURN_GENERATED_KEYS);
        try {


            preparedStatement.setInt(1, conjoint.getCompetances());
            preparedStatement.setInt(2, conjoint.getActPrincipale());
            preparedStatement.setInt(3, conjoint.getSexe());
            preparedStatement.setInt(4, conjoint.getNaissance());
            preparedStatement.setInt(5, conjoint.getScolarite());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getLong(1); // Assuming "id" is the first generated key column
                }
            }
        } catch (Exception e) {
            System.out.print("SQL :");
            System.out.println(preparedStatement);
            e.printStackTrace();

        }
        return generatedId;
    }

    @Override
    public long countConjointsMale(String gouvernorat, String delegation, String secteur, String complexeRes) {
        long count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM conjoint AS c");
        queryBuilder.append(" INNER JOIN personne AS p ON c.id = p.idConjoint");
        queryBuilder.append(" WHERE c.sexe = 2"); // Assuming 1 represents female in your data.

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

        try (PreparedStatement statement = connection.prepareStatement(queryBuilder.toString())) {
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
            e.printStackTrace();
        }

        return count;
    }


    @Override
    public long countConjointsFemale(String gouvernorat, String delegation, String secteur, String complexeRes) {
        long count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM conjoint AS c");
        queryBuilder.append(" INNER JOIN personne AS p ON c.id = p.idConjoint");
        queryBuilder.append(" WHERE c.sexe = 1"); // Assuming 1 represents female in your data.

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

        try (PreparedStatement statement = connection.prepareStatement(queryBuilder.toString())) {
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
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public int countConjointsByScolarite(int sexe, int scolariteValue, String gouvernorat, String delegation, String secteur, String complexeRes) {
        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM conjoint AS c");
        queryBuilder.append(" INNER JOIN personne AS p ON c.id = p.idConjoint");
        queryBuilder.append(" WHERE c.scolarite = ? AND c.sexe = ?");

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

        try (PreparedStatement statement = connection.prepareStatement(queryBuilder.toString())) {
            int parameterIndex = 1;
            statement.setInt(parameterIndex++, scolariteValue);
            statement.setInt(parameterIndex++, sexe);

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
            e.printStackTrace();
        }

        return count;
    }


    @Override
    public int countConjointsByActPrincipale(int sexe, int actPrincipale, String gouvernorat, String delegation, String secteur, String complexeRes) {
        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM conjoint AS c");
        queryBuilder.append(" INNER JOIN personne AS p ON c.id = p.idConjoint");
        queryBuilder.append(" WHERE c.actPrincipale = ? AND c.sexe = ?");

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

        try (PreparedStatement statement = connection.prepareStatement(queryBuilder.toString())) {
            int parameterIndex = 1;
            statement.setInt(parameterIndex++, actPrincipale);
            statement.setInt(parameterIndex++, sexe);

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
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public int countConjointsBycompetances(int sexe, int competances, String gouvernorat, String delegation, String secteur, String complexeRes) {
        int count = 0;
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM conjoint AS c");
        queryBuilder.append(" INNER JOIN personne AS p ON c.id = p.idConjoint");
        queryBuilder.append(" WHERE c.competances = ? AND c.sexe = ?");

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

        try (PreparedStatement statement = connection.prepareStatement(queryBuilder.toString())) {
            int parameterIndex = 1;
            statement.setInt(parameterIndex++, competances);
            statement.setInt(parameterIndex++, sexe);

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
            e.printStackTrace();
        }

        return count;
    }


    @Override
    public Conjoint getById(Long id) {
        return null;
    }
}
