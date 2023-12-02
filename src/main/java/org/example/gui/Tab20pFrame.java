package org.example.gui;

import org.example.Utils.PrintableFrame;
import org.example.dao.SuperficieLabDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class Tab20pFrame extends PrintableFrame {
    private static final SuperficieLabDaoImpl superficieLabDao = new SuperficieLabDaoImpl();

    Double grains_pluviale;
    Double grains_irrigue;
    Double alimentation_pluviale;
    Double alimentation_irrigue;
    Double legumineuses_pluviale;
    Double legumineuses_irrigue;
    Double legumes_pluviale;
    Double legumes_irrigue;
    Double agr_indus_pluviale;
    Double agr_indus_irrigue;
    Double arb_fruit_pluviale;
    Double arb_fruit_irrigue;
    Double oliviers_pluviale;
    Double oliviers_irrigue;
    Double paturage;

    Double total;


    public Tab20pFrame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {


        grains_pluviale = superficieLabDao.sumfromSuperficieLabByColumn("grains_pluviale", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        grains_irrigue = superficieLabDao.sumfromSuperficieLabByColumn("grains_irrigue", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        alimentation_pluviale = superficieLabDao.sumfromSuperficieLabByColumn("alimentation_pluviale", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        alimentation_irrigue = superficieLabDao.sumfromSuperficieLabByColumn("alimentation_irrigue", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        legumineuses_pluviale = superficieLabDao.sumfromSuperficieLabByColumn("legumineuses_pluviale", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        legumineuses_irrigue = superficieLabDao.sumfromSuperficieLabByColumn("legumineuses_irrigue", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        paturage = superficieLabDao.sumfromSuperficieLabByColumn("paturage", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        legumes_pluviale = superficieLabDao.sumfromSuperficieLabByColumn("legumes_pluviale", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        legumes_irrigue = superficieLabDao.sumfromSuperficieLabByColumn("legumes_irrigue", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        agr_indus_pluviale = superficieLabDao.sumfromSuperficieLabByColumn("agr_indus_pluviale", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        agr_indus_irrigue = superficieLabDao.sumfromSuperficieLabByColumn("agr_indus_irrigue", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        arb_fruit_pluviale = superficieLabDao.sumfromSuperficieLabByColumn("arb_fruit_pluviale", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        arb_fruit_irrigue = superficieLabDao.sumfromSuperficieLabByColumn("arb_fruit_irrigue", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        oliviers_pluviale = superficieLabDao.sumfromSuperficieLabByColumn("oliviers_pluviale", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        oliviers_irrigue = superficieLabDao.sumfromSuperficieLabByColumn("oliviers_irrigue", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);


        total = grains_irrigue + grains_pluviale + alimentation_pluviale + alimentation_irrigue + legumineuses_pluviale + legumineuses_irrigue + paturage + legumes_pluviale + legumes_irrigue + agr_indus_pluviale + agr_indus_irrigue + arb_fruit_pluviale + arb_fruit_irrigue + oliviers_pluviale + oliviers_irrigue;

        // Set the title of the JFrame
        setTitle("جدول 21 :الزراعات والغراسات");

        // Set the initial size of the JFrame
        setSize(800, 710);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"المساحة المحروثة", "مساحة (هك)", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", legumes_pluviale * 100.0 / total), String.format("%.2f", legumes_pluviale), "خضروات بعلي"},
                {String.format("%.2f%%", legumes_irrigue * 100.0 / total), String.format("%.2f", legumes_irrigue), "خضروات سقوي"},
                {String.format("%.2f%%", (legumes_pluviale + legumes_irrigue) * 100.0 / total), String.format("%.2f", legumes_pluviale + legumes_irrigue), "مجموع الخضروات"},
                {String.format("%.2f%%", agr_indus_pluviale * 100.0 / total), String.format("%.2f", agr_indus_pluviale), "زراعات صناعية بعلي"},
                {String.format("%.2f%%", agr_indus_irrigue * 100.0 / total), String.format("%.2f", agr_indus_irrigue), "زراعات صناعية  سقوي"},
                {String.format("%.2f%%", (agr_indus_pluviale + agr_indus_irrigue) * 100.0 / total), String.format("%.2f", agr_indus_pluviale + agr_indus_irrigue), "مجموع الزراعات"},
                {String.format("%.2f%%", arb_fruit_pluviale * 100.0 / total), String.format("%.2f", arb_fruit_pluviale), "أشجار مثمرة بعلي"},
                {String.format("%.2f%%", arb_fruit_irrigue * 100.0 / total), String.format("%.2f", arb_fruit_irrigue), "أشجار مثمرة سقوي "},
                {String.format("%.2f%%", (arb_fruit_pluviale + arb_fruit_irrigue) * 100.0 / total), String.format("%.2f", arb_fruit_pluviale + arb_fruit_irrigue), "مجموع الاشجارالمثمرة"},
                {String.format("%.2f%%", oliviers_pluviale * 100.0 / total), String.format("%.2f", oliviers_pluviale), "زياتين بعلي"},
                {String.format("%.2f%%", oliviers_irrigue * 100.0 / total), String.format("%.2f", oliviers_irrigue), "زياتين سقوي"},
                {String.format("%.2f%%", (oliviers_pluviale + oliviers_irrigue) * 100.0 / total), String.format("%.2f", oliviers_pluviale + oliviers_irrigue), "مجموع الزياتين"},
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
    }

}


