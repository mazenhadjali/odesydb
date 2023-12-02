package org.example.gui;

import org.example.Utils.PrintableFrame;
import org.example.dao.LogementDaoImpl;
import org.example.dao.PersonneDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tab14Frame extends PrintableFrame {
    private static final LogementDaoImpl logementDao = new LogementDaoImpl();

    int ciment;
    int traditionnel;
    int nb_chambres;


    public Tab14Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        ciment = logementDao.countByColumnValue("ciment", 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        traditionnel = logementDao.countByColumnValue("traditionnel", 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        nb_chambres = logementDao.sumfromLogementByColumn("nb_chambres", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);


        // Set the title of the JFrame
        setTitle("جدول 14 :حالة المسكن");

        // Set the initial size of the JFrame
        setSize(800, 250);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"حالة المسكن", "العدد", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", ciment * 100.0 / (ciment + traditionnel)), "" + ciment, "مسكن مغطى بالاسمنت"},
                {String.format("%.2f%%", traditionnel * 100.0 / (ciment + traditionnel)), "" + traditionnel, "مسكن بدائي"},
                {String.format("لكل منزل %.2f", nb_chambres * 1.0 / (ciment + traditionnel)), "" + nb_chambres, "عدد الغرف"},
                {String.format("%.2f%%", (ciment + traditionnel) * 100.0 / (ciment + traditionnel)), "" + (ciment + traditionnel), "مجموع"},
        };
//  %.2f%% لكل منزل
        JTable table = new JTable(data, columnNames);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                switch (row) {
                    case 0:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(logementDao.getPersonsByColumnNotEqualTo("ciment", 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 1:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(logementDao.getPersonsByColumnNotEqualTo("traditionnel", 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    default:
                        // Handle any other cases here
                        break;
                }
            }
        });


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


