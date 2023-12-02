package org.example.gui;

import org.example.Utils.PrintableFrame;
import org.example.dao.AnimauxDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class Tab30Frame extends PrintableFrame {

    int totalvacheinf5;
    int totalvaches;
    int countpersonsvaches;
    int counttotalpersonsarea;

    int totalchempsinf30;

    int totalcheps;

    int countpersonschempsinf30;


    AnimauxDaoImpl animauxDao = new AnimauxDaoImpl();

    public Tab30Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        totalvacheinf5 = animauxDao.sumtotalvachinfeg5(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        totalvaches = animauxDao.sumtotalvaches(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        countpersonsvaches = animauxDao.countpersonscahcehif5(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        counttotalpersonsarea = animauxDao.countotalpersonsarea(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        totalchempsinf30 = animauxDao.sumchepsinf30(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        totalcheps = animauxDao.sumchepstotalcheps(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        countpersonschempsinf30 = animauxDao.countpersonschepsinf30(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        // Set the title of the JFrame
        setTitle("جدول 30 :تصنيف الماشية");

        // Set the initial size of the JFrame
        setSize(910, 250);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"", "عدد الرؤوس", "النسبة", "عدد الأشخاص", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", ((countpersonsvaches * 100.0) / counttotalpersonsarea)), "" + countpersonsvaches, String.format("%.2f%%", ((totalvacheinf5 * 100.0) / totalvaches)), "" + totalvacheinf5, "ابقار اقل أو يساوي من 5 رؤوس"},
                {String.format("%.2f%%", ((countpersonschempsinf30 * 100.0) / counttotalpersonsarea)), "" + countpersonschempsinf30, String.format("%.2f%%", ((totalchempsinf30 * 100.0) / totalcheps)), "" + totalchempsinf30, " أغنام و ماعز أقل أو يساوي من 30"},
        };
        //"%.2f"  mou3adel

        JTable table = new JTable(data, columnNames);

        // Center-align cell data for all columns
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


