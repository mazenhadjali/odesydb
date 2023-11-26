package org.example;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.dao.*;
import org.example.entities.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.Utils.Utils.*;

public class ExcelFileReader {

    private static final PersonneDaoImpl personneDao = new PersonneDaoImpl();
    private static final ConjointDaoImpl conjointDao = new ConjointDaoImpl();
    private static final RepEnfantsDaoImpl repEnfantsDao = new RepEnfantsDaoImpl();
    private static final LogementDaoImpl logementDao = new LogementDaoImpl();
    private static final SuperficieDaoImpl superficieDao = new SuperficieDaoImpl();
    private static final SuperficieLabDaoImpl superficieLabDao = new SuperficieLabDaoImpl();
    private static final AnimauxDaoImpl animauxDao = new AnimauxDaoImpl();

    public static List<String> readLines(File file) {
        List<String> redPersons = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file.getAbsolutePath());
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you are reading the first sheet.
            int rowNum = 1;
            Row row = sheet.getRow(rowNum);
            while (isRowEmptyPercentage(row, 0.10)) {

                System.out.println(rowNum);

                Personne personne = getPersonneFromRow(row);

                if (!personneDao.doesPersonExist(personne.cin)) {

                    Conjoint conjoint = getConjointFromRow(row);
                    Long idC = conjointDao.add(conjoint);

                    RepEnfants repEnfants = getRepEnfantsFromRow(row);
                    Long idEnf = repEnfantsDao.add(repEnfants);

                    Logement logement = getLogementFromRow(row);
                    Long idLog = logementDao.add(logement);

                    Superficie superficie = getSuperficieFromRow(row);
                    Long idSup = superficieDao.add(superficie);

                    SuperficieLab superficieLab = getSuperficielabFromRow(row);
                    Long idSuplab = superficieLabDao.add(superficieLab);

                    Animaux animaux = getAnimeauFromRow(row);
                    Long idAnimaux = animauxDao.add(animaux);

                    personne.setIdConjoint(idC);
                    personne.setIdRepEnfant(idEnf);
                    personne.setIdLogement(idLog);
                    personne.setIdSuperficie(idSup);
                    personne.setIdSuperficieLab(idSuplab);
                    personne.setIdAnimaux(idAnimaux);

                    personneDao.add(personne);
                } else {
                    redPersons.add(personne.cin);
                }
                rowNum++;
                row = sheet.getRow(rowNum);
            }

            System.out.println("100%");
            System.out.println("File Changed Successfully");


        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        return redPersons;
    }
}
