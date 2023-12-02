package org.example.gui;

import org.example.Utils.PrintableFrame;
import org.example.dao.PersonneDaoImpl;
import org.example.dao.SuperficieLabDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tab21Frame extends PrintableFrame {
    private static final SuperficieLabDaoImpl superficieLabDao = new SuperficieLabDaoImpl();

    int l0, l1, l2, l3, l4, l5, total;


    public Tab21Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        l0 = superficieLabDao.countByColumnValue("meca_agr", 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        l1 = superficieLabDao.countByColumnValue("meca_agr", 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        l2 = superficieLabDao.countByColumnValue("meca_agr", 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        l3 = superficieLabDao.countByColumnValue("meca_agr", 3, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        l4 = superficieLabDao.countByColumnValue("meca_agr", 4, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        l5 = superficieLabDao.countByColumnValue("meca_agr", 5, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        total = l0 + l1 + l2 + l3 + l4 + l5;

        // Set the title of the JFrame
        setTitle("جدول 22 :الميكنة الفلاحية");

        // Set the initial size of the JFrame
        setSize(800, 320);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"", "العدد", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", l0 * 100.0 / total), "" + l0, "لا يملك"},
                {String.format("%.2f%%", l1 * 100.0 / total), "" + l1, "جرار مع توابع"},
                {String.format("%.2f%%", l2 * 100.0 / total), "" + l2, "الة حصاد"},
                {String.format("%.2f%%", l3 * 100.0 / total), "" + l3, "بريسة"},
                {String.format("%.2f%%", l4 * 100.0 / total), "" + l4, "شاحنة"},
                {String.format("%.2f%%", l5 * 100.0 / total), "" + l5, "معدات تقليدية"},
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
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieLabDao.getPersonsByColumnValue("meca_agr", 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 1:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieLabDao.getPersonsByColumnValue("meca_agr", 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 2:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieLabDao.getPersonsByColumnValue("meca_agr", 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 3:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieLabDao.getPersonsByColumnValue("meca_agr", 3, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 4:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieLabDao.getPersonsByColumnValue("meca_agr", 4, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 5:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieLabDao.getPersonsByColumnValue("meca_agr", 5, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
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
        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Create a "File" menu
        JMenu fileMenu = new JMenu("ملف");
        menuBar.add(fileMenu);

        // Create a "Print" menu item
        JMenuItem printMenuItem = new JMenuItem("طباعة");
        fileMenu.add(printMenuItem);

        // Add an ActionListener to the "Print" menu item
        printMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the print method to print the frame content
                saveTableAsImage(table);
            }
        });
    }

}


