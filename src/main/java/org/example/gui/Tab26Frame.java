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

public class Tab26Frame extends PrintableFrame {
    private static final AnimauxDaoImpl animauxDao = new AnimauxDaoImpl();

    int nb_unit_elv_lap;
    int count_lap;
    int nb_unit_elv_poul;
    int count_poul;

    public Tab26Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        nb_unit_elv_lap = animauxDao.sumfromAnimauxByColumn("nb_unit_elv_lap", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        nb_unit_elv_poul = animauxDao.sumfromAnimauxByColumn("nb_unit_elv_poul", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        count_lap = animauxDao.countByColumngratherThanZero("nb_unit_elv_lap", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        count_poul = animauxDao.countByColumngratherThanZero("nb_unit_elv_poul", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        // Set the title of the JFrame
        setTitle("جدول 27 :الأرانب و الدجاج");

        // Set the initial size of the JFrame
        setSize(800, 250);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"", "عدد", "النسبة", "عدد الأشخاص"};
        String[][] data = {
                {"" + count_lap, String.format("%.2f%%", nb_unit_elv_lap * 100.0 / (nb_unit_elv_lap + nb_unit_elv_poul)), "" + nb_unit_elv_lap, "عدد وحدات تربية الأرانب"},
                {"" + count_poul, String.format("%.2f%%", nb_unit_elv_poul * 100.0 / (nb_unit_elv_lap + nb_unit_elv_poul)), "" + nb_unit_elv_poul, "عدد وحدات تربية الدجاج"},
                {"" + ( count_poul + count_lap ) , "--", "" + (nb_unit_elv_lap + nb_unit_elv_poul), "المجموع"},
        };
        // + ( count_poul + count_poul ) // String.format("%.2f%%", (nb_unit_elv_lap + nb_unit_elv_poul) * 100.0 / (nb_unit_elv_lap + nb_unit_elv_poul))
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
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(animauxDao.getPersonsByColumnNotEqualTo("nb_unit_elv_lap", 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 1:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(animauxDao.getPersonsByColumnNotEqualTo("nb_unit_elv_poul", 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
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


