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

public class Tab23Frame extends PrintableFrame {
    private static final AnimauxDaoImpl animauxDao = new AnimauxDaoImpl();

    int vaches_locaux;
    int count_locaux;
    int vaches_amel;
    int count_amel;
    int vaches_enrac;
    int count_enrac;

    int vaches_total;

    //    sumfromAnimauxByColumn
    public Tab23Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {


        vaches_locaux = animauxDao.sumfromAnimauxByColumn("vaches_locaux", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        vaches_amel = animauxDao.sumfromAnimauxByColumn("vaches_amel", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        vaches_enrac = animauxDao.sumfromAnimauxByColumn("vaches_enrac", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
//        vaches_total = animauxDao.sumfromAnimauxByColumn("vaches_total", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        count_locaux = animauxDao.countByColumnNotEqualsToZero("vaches_locaux", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        count_amel = animauxDao.countByColumnNotEqualsToZero("vaches_amel", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        count_enrac = animauxDao.countByColumnNotEqualsToZero("vaches_enrac", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        vaches_total = vaches_locaux + vaches_amel + vaches_enrac;

        // Set the title of the JFrame
        setTitle("جدول 24 :الأبقار");

        // Set the initial size of the JFrame
        setSize(800, 250);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"", "عدد الرؤوس", "النسبة", "عدد الأشخاص"};
        String[][] data = {
                {"" + count_locaux, String.format("%.2f%%", vaches_locaux * 100.0 / vaches_total), "" + vaches_locaux, "محلية"},
                {"" + count_amel, String.format("%.2f%%", vaches_amel * 100.0 / vaches_total), "" + vaches_amel, "محسنة"},
                {"" + count_enrac, String.format("%.2f%%", vaches_enrac * 100.0 / vaches_total), "" + vaches_enrac, "مؤصلة"},
                {"" + (count_locaux + count_amel + count_enrac ), "--", "" + vaches_total, "المجموع"},
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
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(animauxDao.getPersonsByColumnNotEqualTo("vaches_locaux", 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 1:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(animauxDao.getPersonsByColumnNotEqualTo("vaches_amel", 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 2:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(animauxDao.getPersonsByColumnNotEqualTo("vaches_enrac", 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
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


