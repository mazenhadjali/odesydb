package org.example.gui;

import org.example.Utils.PrintableFrame;
import org.example.dao.PersonneDaoImpl;
import org.example.dao.SuperficieLabDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tab22Frame extends PrintableFrame {
    private static final SuperficieLabDaoImpl superficieLabDao = new SuperficieLabDaoImpl();

    int l1, l2, l3, total;

    public Tab22Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        l1 = superficieLabDao.countByColumnValue("labour", 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        l2 = superficieLabDao.countByColumnValue("labour", 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        l3 = superficieLabDao.countByColumnValue("labour", 3, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        total = l1 + l2 + l3;

        // Set the title of the JFrame
        setTitle("جدول 23 :الحراثة");

        // Set the initial size of the JFrame
        setSize(800, 250);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"", "العدد", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", l1 * 100.0 / total), "" + l1, "تقليدية"},
                {String.format("%.2f%%", l2 * 100.0 / total), "" + l2, "عصرية"},
                {String.format("%.2f%%", l3 * 100.0 / total), "" + l3, "مختلطة"},
                {String.format("%.2f%%", total * 100.0 / total), "" + total, "المجموع"},
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
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieLabDao.getPersonsByColumnValue("labour", 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 1:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieLabDao.getPersonsByColumnValue("labour", 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 2:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieLabDao.getPersonsByColumnValue("labour", 3, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
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


