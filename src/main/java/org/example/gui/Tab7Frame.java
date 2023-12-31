package org.example.gui;

import org.example.Utils.PrintableFrame;
import org.example.dao.ConjointDaoImpl;
import org.example.dao.PersonneDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tab7Frame extends PrintableFrame {
    private static final ConjointDaoImpl conjointDao = new ConjointDaoImpl();

    long f1, f2, f3, f4, f5;
    long m1, m2, m3, m4, m5;

    long totalf, totalm;


    public Tab7Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        f1 = conjointDao.countConjointsByScolarite(1, 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        f2 = conjointDao.countConjointsByScolarite(1, 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        f3 = conjointDao.countConjointsByScolarite(1, 3, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        f4 = conjointDao.countConjointsByScolarite(1, 4, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        f5 = conjointDao.countConjointsByScolarite(1, 5, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        m1 = conjointDao.countConjointsByScolarite(2, 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        m2 = conjointDao.countConjointsByScolarite(2, 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        m3 = conjointDao.countConjointsByScolarite(2, 3, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        m4 = conjointDao.countConjointsByScolarite(2, 4, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        m5 = conjointDao.countConjointsByScolarite(2, 5, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        totalm = conjointDao.countConjointsMale(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        totalf = conjointDao.countConjointsFemale(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        // Set the title of the JFrame
        setTitle("جدول 7 :المستوى التعليمي للقرين  (الزوج / الزوجة)");

        // Set the initial size of the JFrame
        setSize(800, 350);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        System.out.println(totalf);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"المستوى التعليمي", "عدد الرجال", "نسبة الرجال", "عدد النساء", "نسبة النساء", "مجموع", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", ((f1 + m1) * 100.0 / (totalm + totalf))), "" + (f1 + m1), String.format("%.2f%%", (f1 * 100.0 / (totalm + totalf))), "" + f1, String.format("%.2f%%", (m1 * 100.0 / (totalm + totalf))), "" + m1, "أمي"},
                {String.format("%.2f%%", ((f2 + m2) * 100.0 / (totalm + totalf))), "" + (f2 + m2), String.format("%.2f%%", (f2 * 100.0 / (totalm + totalf))), "" + f2, String.format("%.2f%%", (m2 * 100.0 / (totalm + totalf))), "" + m2, "كتاب"},
                {String.format("%.2f%%", ((f3 + m3) * 100.0 / (totalm + totalf))), "" + (f3 + m3), String.format("%.2f%%", (f3 * 100.0 / (totalm + totalf))), "" + f3, String.format("%.2f%%", (m3 * 100.0 / (totalm + totalf))), "" + m3, "ابتدائي"},
                {String.format("%.2f%%", ((f4 + m4) * 100.0 / (totalm + totalf))), "" + (f4 + m4), String.format("%.2f%%", (f4 * 100.0 / (totalm + totalf))), "" + f4, String.format("%.2f%%", (m4 * 100.0 / (totalm + totalf))), "" + m4, "ثانوي"},
                {String.format("%.2f%%", ((f5 + m5) * 100.0 / (totalm + totalf))), "" + (f5 + m5), String.format("%.2f%%", (f5 * 100.0 / (totalm + totalf))), "" + f5, String.format("%.2f%%", (m5 * 100.0 / (totalm + totalf))), "" + m5, "عالي"},
                {String.format("%.2f%%", ((f1 + f2 + f3 + f4 + f5 + m1 + m2 + m3 + m4 + m5) * 100.0 / (totalm + totalf))), "" + (f1 + f2 + f3 + f4 + f5 + m1 + m2 + m3 + m4 + m5), String.format("%.2f%%", ((f1 + f2 + f3 + f4 + f5) * 100.0 / (totalm + totalf))), "" + (f1 + f2 + f3 + f4 + f5), String.format("%.2f%%", ((m1 + m2 + m3 + m4 + m5) * 100.0 / (totalm + totalf))), "" + (m1 + m2 + m3 + m4 + m5), "المجموع"},
        };

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


