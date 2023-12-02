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

public class Tab20Frame extends PrintableFrame {
    private static final SuperficieLabDaoImpl superficieLabDao = new SuperficieLabDaoImpl();

    Double grains_pluviale;
    Double grains_irrigue;
    Double alimentation_pluviale;
    Double alimentation_irrigue;
    Double legumineuses_pluviale;
    Double legumineuses_irrigue;
    Double paturage;

    Double total;


    public Tab20Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {


        grains_pluviale = superficieLabDao.sumfromSuperficieLabByColumn("grains_pluviale", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        grains_irrigue = superficieLabDao.sumfromSuperficieLabByColumn("grains_irrigue", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        alimentation_pluviale = superficieLabDao.sumfromSuperficieLabByColumn("alimentation_pluviale", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        alimentation_irrigue = superficieLabDao.sumfromSuperficieLabByColumn("alimentation_irrigue", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        legumineuses_pluviale = superficieLabDao.sumfromSuperficieLabByColumn("legumineuses_pluviale", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        legumineuses_irrigue = superficieLabDao.sumfromSuperficieLabByColumn("legumineuses_irrigue", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        paturage = superficieLabDao.sumfromSuperficieLabByColumn("paturage", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);


        total = grains_irrigue + grains_pluviale + alimentation_pluviale + alimentation_irrigue + legumineuses_pluviale + legumineuses_irrigue + paturage;

        // Set the title of the JFrame
        setTitle("جدول 20 :لزراعات الصغرى");

        // Set the initial size of the JFrame
        setSize(800, 700);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"المساحة المحروثة", "مساحة (هك)", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", grains_pluviale * 100.0 / total), String.format("%.2f", grains_pluviale), "حبوب بعلي"},
                {String.format("%.2f%%", grains_irrigue * 100.0 / total), String.format("%.2f", grains_irrigue), "حبوب سقوي"},
                {String.format("%.2f%%", (grains_irrigue + grains_pluviale) * 100 / total), String.format("%.2f", grains_irrigue + grains_pluviale), "مجموع حبوب"},
                {String.format("%.2f%%", alimentation_pluviale * 100.0 / total), String.format("%.2f", alimentation_pluviale), "أعلاف بعلي"},
                {String.format("%.2f%%", alimentation_irrigue * 100.0 / total), String.format("%.2f", alimentation_irrigue), "أعلاف سقوي"},
                {String.format("%.2f%%", (alimentation_pluviale + alimentation_irrigue) * 100.0 / total), String.format("%.2f", alimentation_pluviale + alimentation_irrigue), "مجموع الاعلاف"},
                {String.format("%.2f%%", legumineuses_pluviale * 100.0 / total), String.format("%.2f", legumineuses_pluviale), "بقول بعلي"},
                {String.format("%.2f%%", legumineuses_irrigue * 100.0 / total), String.format("%.2f", legumineuses_irrigue), "بقول سقوي"},
                {String.format("%.2f%%", (legumineuses_pluviale + legumineuses_irrigue) * 100.0 / total), String.format("%.2f", legumineuses_pluviale + legumineuses_irrigue), "مجموع البقول"},
                {String.format("%.2f%%", paturage * 100.0 / total), String.format("%.2f", paturage), "المراعي"},
                {"--", String.format("%.2f", total), "المجموع"},
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


