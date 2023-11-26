package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {
    private static MySQLConnection instance;
    private static Connection connection;

    String unicode = "?useUnicode=yes&characterEncoding=UTF-8";

    private String url = "jdbc:mysql://localhost:3306/odesydb";
    private String username = "root";
    private String password = "root";

    // Private constructor to prevent direct instantiation
    private MySQLConnection() {
        try {
            connection = DriverManager.getConnection(url + unicode, username, password);
            System.out.println("Connected to database ...");

            System.out.println("checking Conjoints table...");
            createConjointTable();
            System.out.println("checking RepEnfs table...");
            createRepEnfantsTable();
            System.out.println("checking Logement table...");
            createLogementTable();
            System.out.println("checking Superficie table...");
            createSuperficieTable();
            System.out.println("checking SuperficieLab table...");
            createSuperficieLabTable();
            System.out.println("checking animaux table...");
            createAnimauxTable();
            System.out.println("checking persons table...");
            createPersonneTable();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Public method to get the Singleton instance
    public static synchronized MySQLConnection getInstance() {
        if (instance == null) {
            instance = new MySQLConnection();
        }
        return instance;
    }

    // Public method to get the database connection
    public Connection getConnection() {
        return connection;
    }

    public static void createPersonneTable() {
        try (Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS personne ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                    + "gouvernorat VARCHAR(255) CHARACTER SET utf8,"
                    + "delegation VARCHAR(255) CHARACTER SET utf8,"
                    + "secteur VARCHAR(255) CHARACTER SET utf8,"
                    + "complexeRes VARCHAR(255) CHARACTER SET utf8,"
                    + "nomComplet VARCHAR(255) CHARACTER SET utf8,"
                    + "sexe VARCHAR(255) CHARACTER SET utf8,"
                    + "naissance INT,"
                    + "cin VARCHAR(255) CHARACTER SET utf8,"
                    + "scolarite INT,"
                    + "actPrincipale INT,"
                    + "actSecondaire INT,"
                    + "etatSociale VARCHAR(255) CHARACTER SET utf8,"
                    + "exploitation INT,"
                    + "presence INT,"
                    + "miniProjet INT,"
                    + "idConjoint BIGINT,"
                    + "idRepEnfant BIGINT,"
                    + "nb_handicap INT,"
                    + "idLogement BIGINT,"
                    + "idSuperficie BIGINT,"
                    + "idSuperficielab BIGINT,"
                    + "struct_pro INT,"
                    + "idAnimaux BIGINT,"
                    + "FOREIGN KEY (idConjoint) REFERENCES conjoint(id),"
                    + "FOREIGN KEY (idRepEnfant) REFERENCES rep_enfants(id),"
                    + "FOREIGN KEY (idLogement) REFERENCES logement(id),"
                    + "FOREIGN KEY (idSuperficie) REFERENCES superficie(id),"
                    + "FOREIGN KEY (idSuperficielab) REFERENCES superficielab(id),"
                    + "FOREIGN KEY (idAnimaux) REFERENCES animaux(id)"
                    + ")";

            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void createConjointTable() {
        try (Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS conjoint ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                    + "competances INT,"
                    + "actPrincipale INT,"
                    + "sexe INT,"
                    + "naissance INT,"
                    + "scolarite INT"
                    + ")";
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createRepEnfantsTable() {
        try (Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS rep_enfants ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                    + "total_filles INT,"
                    + "total_garcons INT,"
                    + "nb_age_G_moins_6 INT,"
                    + "nb_age_F_moins_6 INT,"
                    + "nb_age_G_6_18 INT,"
                    + "nb_age_F_6_18 INT,"
                    + "nb_age_G_18_40 INT,"
                    + "nb_age_F_18_40 INT,"
                    + "nb_age_plus_40 INT,"
                    + "etude_G_6_18 INT,"
                    + "etude_F_6_18 INT,"
                    + "nb_enf_diplome_sup INT,"
                    + "nb_enf_diplome_sup_chom INT,"
                    + "nb_enf_cert_Pro INT,"
                    + "nb_enf_cert_Pro_Chom INT,"
                    + "nb_enf_sans_diplome_qualifie INT,"
                    + "nb_enf_sans_diplome_non_qualifie INT"
                    + ")";
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createLogementTable() {
        try (Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS logement ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                    + "ciment INT,"
                    + "traditionnel INT,"
                    + "nb_chambres INT,"
                    + "citerne INT,"
                    + "eau_indiv INT,"
                    + "eau_coll INT,"
                    + "eau_autre INT" // Adjust the VARCHAR size as needed
                    + ")";
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createSuperficieTable() {
        try (Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS superficie ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                    + "prop DOUBLE,"
                    + "louer DOUBLE,"
                    + "surChiites DOUBLE,"
                    + "total DOUBLE,"
                    + "nombre DOUBLE"
                    + ")";
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createSuperficieLabTable() {
        try (Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS superficielab ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                    + "total_pluviale DOUBLE,"
                    + "total_irrigue DOUBLE,"
                    + "grains_pluviale DOUBLE,"
                    + "grains_irrigue DOUBLE,"
                    + "alimentation_pluviale DOUBLE,"
                    + "alimentation_irrigue DOUBLE,"
                    + "legumineuses_pluviale DOUBLE,"
                    + "legumineuses_irrigue DOUBLE,"
                    + "legumes_pluviale DOUBLE,"
                    + "legumes_irrigue DOUBLE,"
                    + "agr_indus_pluviale DOUBLE,"
                    + "agr_indus_irrigue DOUBLE,"
                    + "arb_fruit_pluviale DOUBLE,"
                    + "arb_fruit_irrigue DOUBLE,"
                    + "oliviers_pluviale DOUBLE,"
                    + "oliviers_irrigue DOUBLE,"
                    + "paturage DOUBLE,"
                    + "meca_agr INT,"
                    + "labour INT"
                    + ")";
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createAnimauxTable() {
        try (Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS animaux ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                    + "vaches_locaux INT,"
                    + "vaches_amel INT,"
                    + "vaches_enrac INT,"
                    + "vaches_total INT,"
                    + "ovins_chep INT,"
                    + "ovins_chev INT,"
                    + "ovins_bet INT,"
                    + "ruches_moderne INT,"
                    + "ruches_trad INT,"
                    + "nb_unit_elv_lap INT,"
                    + "nb_unit_elv_poul INT,"
                    + "nom_foret VARCHAR(255) CHARACTER SET utf8,"
                    + "mois INT"
                    + ")";
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void dropTable(String tableName) {
        try (Statement statement = connection.createStatement()) {
            String dropTableSQL = "DROP TABLE " + tableName;
            statement.execute(dropTableSQL);
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


}
