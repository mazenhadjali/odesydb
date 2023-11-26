package org.example.gui;

import org.example.dao.PersonneDaoImpl;
import org.example.dao.RepEnfantsDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class Tab12Frame extends JFrame {
    private static final RepEnfantsDaoImpl repEnfantsDao = new RepEnfantsDaoImpl();

    int etude_G_6_18;
    int etude_F_6_18;

    int sommetotalfilles;
    int sommetotalgarcons;

    public Tab12Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        etude_G_6_18 = repEnfantsDao.sumfromRepEnfByColumn("etude_G_6_18", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        etude_F_6_18 = repEnfantsDao.sumfromRepEnfByColumn("etude_F_6_18", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        sommetotalfilles = repEnfantsDao.sumfromRepEnfByColumn("total_filles", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        sommetotalgarcons = repEnfantsDao.sumfromRepEnfByColumn("total_garcons", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        // Set the title of the JFrame
        setTitle("" + "جدول 12 : بصدد الدراسة");


        // Set the initial size of the JFrame
        setSize(800, 250);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"بصدد الدراسة", "عدد الذكور", "نسبة الذكور", "عدد الاناث", "نسبة الاناث", "مجموع (رجال و نساء)", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", (etude_G_6_18 + etude_F_6_18) * 100.0 / (sommetotalfilles + sommetotalgarcons)), "" + (etude_G_6_18 + etude_F_6_18), String.format("%.2f%%", etude_F_6_18 * 100.0 / (sommetotalfilles + sommetotalgarcons)), "" + etude_F_6_18, String.format("%.2f%%", etude_G_6_18 * 100.0 / (sommetotalfilles + sommetotalgarcons)), "" + etude_G_6_18, "من 6 إلى 18 سنة"},
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


