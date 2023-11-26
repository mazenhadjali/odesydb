package org.example.gui;

import org.example.dao.LogementDaoImpl;
import org.example.dao.PersonneDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class Tab16Frame extends JFrame {
    private static final LogementDaoImpl logementDao = new LogementDaoImpl();

    int eau_indiv;
    int eau_coll;
    int eau_autre;

    public Tab16Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {


        eau_indiv = logementDao.countByColumnValue("eau_indiv", 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        eau_coll = logementDao.countByColumnValue("eau_coll", 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        eau_autre = logementDao.countByColumnValue("eau_autre", 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        // Set the title of the JFrame
        setTitle("جدول 16 :التزود بالماء الصالح للشراب");

        // Set the initial size of the JFrame
        setSize(800, 250);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"مصدر الماء", "العدد", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", eau_indiv * 100.0 / (eau_indiv + eau_coll + eau_autre)), "" + eau_indiv, "حنفية فردية"},
                {String.format("%.2f%%", eau_coll * 100.0 / (eau_indiv + eau_coll + eau_autre)), "" + eau_coll, "حنفية جماعية"},
                {String.format("%.2f%%", eau_autre * 100.0 / (eau_indiv + eau_coll + eau_autre)), "" + eau_autre, "مصادر أخرى"},
                {String.format("%.2f%%", (eau_indiv + eau_coll + eau_autre) * 100.0 / (eau_indiv + eau_coll + eau_autre)), "" + (eau_indiv + eau_coll + eau_autre), "مجموع"},
        };

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


