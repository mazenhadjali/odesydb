package org.example.gui;

import org.example.Utils.PrintableFrame;
import org.example.dao.PersonneDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class Tab2Frame extends PrintableFrame {
    private static final PersonneDaoImpl personneDao = new PersonneDaoImpl();

    int f1, f2, f3, f4, f5;
    int m1, m2, m3, m4, m5;

    long totalf, totalm;
    long total;


    public Tab2Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        f1 = personneDao.countPersonsByScolarite(1, 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        f2 = personneDao.countPersonsByScolarite(1, 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        f3 = personneDao.countPersonsByScolarite(1, 3, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        f4 = personneDao.countPersonsByScolarite(1, 4, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        f5 = personneDao.countPersonsByScolarite(1, 5, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        m1 = personneDao.countPersonsByScolarite(2, 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        m2 = personneDao.countPersonsByScolarite(2, 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        m3 = personneDao.countPersonsByScolarite(2, 3, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        m4 = personneDao.countPersonsByScolarite(2, 4, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        m5 = personneDao.countPersonsByScolarite(2, 5, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        totalm = personneDao.countPersonneMale(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        totalf = personneDao.countPersonneFemale(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        total = totalm + totalf;

        // Set the title of the JFrame
        setTitle("جدول 2 :المستوى التعليمي (رجال و نساء رؤساء العائلات)");

        // Set the initial size of the JFrame
        setSize(800, 350);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        System.out.println(totalm);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"المستوى التعليمي", "عدد الرجال", "نسبة الرجال", "عدد النساء", "نسبة النساء", "مجموع", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", ((f1 + m1) * 100.0 / total)), "" + (f1 + m1), String.format("%.2f%%", (f1 * 100.0 / total)), "" + f1, String.format("%.2f%%", (m1 * 100.0 / total)), "" + m1, "أمي"},
                {String.format("%.2f%%", ((f2 + m2) * 100.0 / total)), "" + (f2 + m2), String.format("%.2f%%", (f2 * 100.0 / total)), "" + f2, String.format("%.2f%%", (m2 * 100.0 / total)), "" + m2, "كتاب"},
                {String.format("%.2f%%", ((f3 + m3) * 100.0 / total)), "" + (f3 + m3), String.format("%.2f%%", (f3 * 100.0 / total)), "" + f3, String.format("%.2f%%", (m3 * 100.0 / total)), "" + m3, "ابتدائي"},
                {String.format("%.2f%%", ((f4 + m4) * 100.0 / total)), "" + (f4 + m4), String.format("%.2f%%", (f4 * 100.0 / total)), "" + f4, String.format("%.2f%%", (m4 * 100.0 / total)), "" + m4, "ثانوي"},
                {String.format("%.2f%%", ((f5 + m5) * 100.0 / total)), "" + (f5 + m5), String.format("%.2f%%", (f5 * 100.0 / total)), "" + f5, String.format("%.2f%%", (m5 * 100.0 / total)), "" + m5, "عالي"},
                {String.format("%.2f%%", ((f1 + f2 + f3 + f4 + f5 + m1 + m2 + m3 + m4 + m5) * 100.0 / total)), (f1 + f2 + f3 + f4 + f5 + m1 + m2 + m3 + m4 + m5) + "", String.format("%.2f%%", ((f1 + f2 + f3 + f4 + f5) * 100.0 / total)), (f1 + f2 + f3 + f4 + f5) + "", String.format("%.2f%%", ((m1 + m2 + m3 + m4 + m5) * 100.0 / total)), "" + (m1 + m2 + m3 + m4 + m5), "المجموع"},
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
    }

}


