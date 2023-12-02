package org.example.gui;

import org.example.Utils.PrintableFrame;
import org.example.dao.ConjointDaoImpl;
import org.example.dao.PersonneDaoImpl;
import org.example.dao.RepEnfantsDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class Tab11Frame extends PrintableFrame {
    private static final RepEnfantsDaoImpl repEnfantsDao = new RepEnfantsDaoImpl();
    private static final PersonneDaoImpl personneDao = new PersonneDaoImpl();


    int nb_age_G_moins_6;
    int nb_age_F_moins_6;
    int nb_age_G_6_18;
    int nb_age_F_6_18;
    int nb_age_G_18_40;
    int nb_age_F_18_40;
    int nbpers1840F;
    int nbpers1840G;

    int sommetotalfilles;
    int sommetotalgarcons;


    public Tab11Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        nb_age_G_moins_6 = repEnfantsDao.sumfromRepEnfByColumn("nb_age_G_moins_6", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        nb_age_F_moins_6 = repEnfantsDao.sumfromRepEnfByColumn("nb_age_F_moins_6", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        nb_age_G_6_18 = repEnfantsDao.sumfromRepEnfByColumn("nb_age_G_6_18", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        nb_age_F_6_18 = repEnfantsDao.sumfromRepEnfByColumn("nb_age_F_6_18", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        nb_age_G_18_40 = repEnfantsDao.sumfromRepEnfByColumn("nb_age_G_18_40", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        nb_age_F_18_40 = repEnfantsDao.sumfromRepEnfByColumn("nb_age_F_18_40", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        sommetotalfilles = repEnfantsDao.sumfromRepEnfByColumn("total_filles", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        sommetotalgarcons = repEnfantsDao.sumfromRepEnfByColumn("total_garcons", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        nbpers1840F = personneDao.countPersons1840bysexe(1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        nbpers1840G = personneDao.countPersons1840bysexe(2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        System.out.println(nbpers1840F);
        System.out.println(nbpers1840G);
        // Set the title of the JFrame
        setTitle("جدول 11 :الشباب حسب العمر");

        // Set the initial size of the JFrame
        setSize(1000, 350);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        String[] columnNames = {"العمر", "عدد ذكور", "نسبة ذكور", "عدد إناث", "نسبة إناث", "نسبة إناث بالنسبة للنساء", "مجموع", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", ((nb_age_F_moins_6 + nb_age_G_moins_6) * 100.0 / (sommetotalgarcons + sommetotalfilles))), "" + (nb_age_F_moins_6 + nb_age_G_moins_6), String.format("%.2f%%", (nb_age_F_moins_6 * 100.0 / sommetotalfilles)), String.format("%.2f%%", (nb_age_F_moins_6 * 100.0 / (sommetotalgarcons + sommetotalfilles))), "" + nb_age_F_moins_6, String.format("%.2f%%", (nb_age_G_moins_6 * 100.0 / (sommetotalgarcons + sommetotalfilles))), "" + nb_age_G_moins_6, "أقل من 6 سنوات"},
                {String.format("%.2f%%", ((nb_age_F_6_18 + nb_age_G_6_18) * 100.0 / (sommetotalgarcons + sommetotalfilles))), "" + (nb_age_F_6_18 + nb_age_G_6_18), String.format("%.2f%%", (nb_age_F_6_18 * 100.0 / sommetotalfilles)), String.format("%.2f%%", (nb_age_F_6_18 * 100.0 / (sommetotalgarcons + sommetotalfilles))), "" + nb_age_F_6_18, String.format("%.2f%%", (nb_age_G_6_18 * 100.0 / (sommetotalgarcons + sommetotalfilles))), "" + nb_age_G_6_18, "من 6 إلى 18 سنة"},
                {String.format("%.2f%%", ((nb_age_F_18_40 + nb_age_G_18_40) * 100.0 / (sommetotalgarcons + sommetotalfilles))), "" + (nb_age_F_18_40 + nb_age_G_18_40), String.format("%.2f%%", (nb_age_F_18_40 * 100.0 / sommetotalfilles)), String.format("%.2f%%", (nb_age_F_18_40 * 100.0 / (sommetotalgarcons + sommetotalfilles))), "" + nb_age_F_18_40, String.format("%.2f%%", (nb_age_G_18_40 * 100.0 / (sommetotalgarcons + sommetotalfilles))), "" + nb_age_G_18_40, "من 18 إلى 40"},
                {String.format("%.2f%%", ((nbpers1840G + nbpers1840F) * 100.0 / (sommetotalgarcons + sommetotalfilles))), "" + (nbpers1840G + nbpers1840F), String.format("%.2f%%", (nbpers1840F * 100.0 / sommetotalfilles)), String.format("%.2f%%", (nbpers1840F * 100.0 / (sommetotalgarcons + sommetotalfilles))), "" + nbpers1840F, String.format("%.2f%%", (nbpers1840G * 100.0 / (sommetotalgarcons + sommetotalfilles))), "" + nbpers1840G, "رؤساء العائلات من 18 إلى 40 سنة"},
                {String.format("%.2f%%", ((sommetotalfilles + sommetotalgarcons) * 100.0 / (sommetotalgarcons + sommetotalfilles))), "" + (sommetotalgarcons + sommetotalfilles), String.format("%.2f%%", ((nb_age_F_moins_6 + nb_age_F_6_18 + nb_age_F_18_40) * 100.0 / sommetotalfilles)), String.format("%.2f%%", (sommetotalfilles * 100.0 / (sommetotalgarcons + sommetotalfilles))), "" + sommetotalfilles, String.format("%.2f%%", (sommetotalgarcons * 100.0 / (sommetotalgarcons + sommetotalfilles))), "" + sommetotalgarcons, "المجموع"},
        };
        //String.format("%.2f%%", ((nb_age_F_moins_6 + nb_age_F_6_18 + nb_age_F_18_40) * 100.0 / sommetotalfilles))
        JTable table = new JTable(data, columnNames);
//        / Center-align cell data for all column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set up the table design
        table.setRowHeight(40); // Adjust the row height for Arabic text
        table.setFont(new Font("Arial", Font.PLAIN, 16)); // Set a suitable Arabic font

        // Make the table headers bold and styled for Arabic text
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18));
        header.setPreferredSize(new Dimension(1, 40));
        header.setBackground(Color.LIGHT_GRAY);
        header.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); // Set header orientation

        // Add the table to a JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the JScrollPane to the JFrame
        add(scrollPane, BorderLayout.CENTER);
    }

}


