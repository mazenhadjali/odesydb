package org.example.gui;

import org.example.dao.PersonneDaoImpl;
import org.example.dao.SuperficieLabDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tab18Frame extends JFrame {
    private static final SuperficieLabDaoImpl superficieLabDao = new SuperficieLabDaoImpl();

    Double total_pluviale;
    int count_pluviale;
    int count_paturage;
    Double total_irrigue;
    int count_irrigue;
    Double paturage;
    Double total;

    public Tab18Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        total_pluviale = superficieLabDao.sumfromSuperficieLabByColumn("total_pluviale", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        total_irrigue = superficieLabDao.sumfromSuperficieLabByColumn("total_irrigue", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        paturage = superficieLabDao.sumfromSuperficieLabByColumn("paturage", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        count_pluviale = superficieLabDao.countFromSuperficieByColumn("total_pluviale", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        count_irrigue = superficieLabDao.countFromSuperficieByColumn("total_irrigue", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        count_paturage = superficieLabDao.countFromSuperficieByColumn("paturage", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        total = total_pluviale + total_irrigue + paturage;
        // Set the title of the JFrame
        setTitle("جدول 18 :توزيع مساحة (هك)");

        // Set the initial size of the JFrame
        setSize(800, 250);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"المساحة المحروثة", "مساحة (هك)", "النسبة", "عدد الأشخاص"};
        String[][] data = {
                {"" + count_pluviale, String.format("%.2f%%", total_pluviale * 100.0 / total), "" + String.format("%.2f", total_pluviale), "بعلي"},
                {"" + count_irrigue, String.format("%.2f%%", total_irrigue * 100.0 / total), "" + String.format("%.2f", total_irrigue), "سقوي"},
                {"" + count_paturage, String.format("%.2f%%", paturage * 100.0 / total), "" + String.format("%.2f", paturage), "بور/مرعى"},
                {"" + ( count_pluviale + count_irrigue + count_paturage) , String.format("%.2f%%", (total_pluviale + total_irrigue + paturage  )* 100.0 / total), "" + String.format("%.2f", total), "المساحة الجملية"},
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
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieLabDao.getPersonsByColumnNotEqualToZero("total_pluviale", 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 1:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieLabDao.getPersonsByColumnNotEqualToZero("total_irrigue", 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 2:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieLabDao.getPersonsByColumnNotEqualToZero("paturage", 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
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


