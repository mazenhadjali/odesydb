package org.example.gui;

import org.example.dao.LogementDaoImpl;
import org.example.dao.PersonneDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tab15Frame extends JFrame {
    private static final LogementDaoImpl logementDao = new LogementDaoImpl();

    int citerne;
    int nonCiterne;

    int total;


    public Tab15Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        citerne = logementDao.countByColumnValue("citerne", 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        nonCiterne = logementDao.countByColumnValue("citerne", 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        total = citerne + nonCiterne;
        // Set the title of the JFrame
        setTitle("جدول 15 :منتفع بماجل");

        // Set the initial size of the JFrame
        setSize(800, 250);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"ماجل", "العدد", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", citerne * 100.0 / total), "" + citerne, "منتفع بماجل"},
                {String.format("%.2f%%", nonCiterne * 100.0 / total), "" + nonCiterne, "غي منتفع بماجل"},
                {String.format("%.2f%%", total * 100.0 / total), "" + total, "مجموع"},
        };

        JTable table = new JTable(data, columnNames);


        // Center-align cell data for all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                switch (row) {
                    case 0:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(logementDao.getPersonsByColumnNotEqualTo("citerne", 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 1:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(logementDao.getPersonsByColumnNotEqualTo("citerne", 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    default:
                        // Handle any other cases here
                        break;
                }
            }
        });


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


