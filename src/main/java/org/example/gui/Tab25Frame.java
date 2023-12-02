package org.example.gui;

import org.example.Utils.PrintableFrame;
import org.example.dao.AnimauxDaoImpl;
import org.example.dao.PersonneDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tab25Frame extends PrintableFrame {
    private static final AnimauxDaoImpl animauxDao = new AnimauxDaoImpl();

    int ruches_moderne;
    int count_moderne;
    int ruches_trad;
    int count_trad;

    public Tab25Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        ruches_moderne = animauxDao.sumfromAnimauxByColumn("ruches_moderne", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        ruches_trad = animauxDao.sumfromAnimauxByColumn("ruches_trad", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        count_moderne = animauxDao.countByColumngratherThanZero("ruches_moderne", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        count_trad = animauxDao.countByColumngratherThanZero("ruches_trad", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        // Set the title of the JFrame
        setTitle("جدول 26 :النحل");

        // Set the initial size of the JFrame
        setSize(800, 250);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"خلايا النحل", "عدد", "النسبة", "عدد الأشخاص"};
        String[][] data = {
                {"" + count_moderne, String.format("%.2f%%", ruches_moderne * 100.0 / (ruches_moderne + ruches_trad)), "" + ruches_moderne, "عصري"},
                {"" + count_trad, String.format("%.2f%%", ruches_trad * 100.0 / (ruches_moderne + ruches_trad)), "" + ruches_trad, "تقليدي"},
                {"" + (count_moderne + count_trad), "--", "" + (ruches_moderne + ruches_trad), "المجموع"},
        };
        // + ( count_moderne + count_trad )
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
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(animauxDao.getPersonsByColumnNotEqualTo("ruches_moderne", 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 1:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(animauxDao.getPersonsByColumnNotEqualTo("ruches_trad", 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
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


