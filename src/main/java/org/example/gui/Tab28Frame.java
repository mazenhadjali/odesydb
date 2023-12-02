package org.example.gui;

import org.example.Utils.PrintableFrame;
import org.example.dao.AnimauxDaoImpl;
import org.example.dao.PersonneDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class Tab28Frame extends PrintableFrame {
    private static final PersonneDaoImpl personneDao = new PersonneDaoImpl();
    private static final AnimauxDaoImpl animauxDao = new AnimauxDaoImpl();


    long countMale;
    long countFemale;
    long total;

    int count;

    int mois;


    public Tab28Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        this.countMale = personneDao.countPersonneMale(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        this.countFemale = personneDao.countPersonneFemale(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        this.total = this.countMale + this.countFemale;

        count = animauxDao.countByColumn("mois", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        mois = animauxDao.sumfromAnimauxByColumn("mois", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        // Set the title of the JFrame
        setTitle("جدول 29 :استغلال الغابة");

        // Set the initial size of the JFrame
        setSize(800, 250);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"العدد الجملي لرؤساء العائلات", "عدد رؤساء العائلات المستغلين للمرعى/غابة", "مدة استغلال المرعى/غابة  (عدد الأشهر )",  "نسبة المستغلين لمرعى/لغابة", "معدل استغلال الغابة لمستغليها"};
        String[][] data = {{
                String.format("%.2f", mois * 1.0 / count),
                String.format("%.2f%%", count * 100.0 / total),
                "" + mois, "" + count, "" + total
        }};
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


