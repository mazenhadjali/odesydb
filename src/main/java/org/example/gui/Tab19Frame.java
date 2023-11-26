package org.example.gui;

import org.example.dao.SuperficieDaoImpl;
import org.example.dao.SuperficieLabDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tab19Frame extends JFrame {
    private static final SuperficieDaoImpl superficieDao = new SuperficieDaoImpl();

    int E_0;
    int B_0_2;
    int B_2_5;
    int B_5_10;
    int SE_10;

    long sum_B_0_2;
    long sum_B_2_5;
    long sum_B_5_10;
    long sum_SE_10;

    int total;


    public Tab19Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        E_0 = superficieDao.countRowsWithTotalZero(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        B_0_2 = superficieDao.countRowsByTotalRange(0, 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        B_2_5 = superficieDao.countRowsByTotalRange(2, 5, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        B_5_10 = superficieDao.countRowsByTotalRange(5, 10, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        SE_10 = superficieDao.countRowsWithTotalGreaterThanEqualToTen(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);


        sum_B_0_2 = superficieDao.sumRowsByTotalRange(0, 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        sum_B_2_5 = superficieDao.sumRowsByTotalRange(2, 5, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        sum_B_5_10 = superficieDao.sumRowsByTotalRange(5, 10, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        sum_SE_10 = superficieDao.sumRowsWithTotalGreaterThanEqualToTen(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);


        total = B_0_2 + B_2_5 + B_5_10 + SE_10;

        // Set the title of the JFrame
        setTitle("جدول 19 :توزيع  المستغلات الفلاحية حسب المساحة");

        // Set the initial size of the JFrame
        setSize(800, 400);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"مساحة المستغلة الفلاحية", "العدد", "النسبة", "مساحة", "النسبة"};
        String[][] data = {
                {"--", "--", String.format("%.2f%%", E_0 * 100.0 / total), "" + E_0, " 0"},
                {String.format("%.2f%%", sum_B_0_2 * 100.0 / (sum_B_0_2 + sum_B_2_5 + sum_B_5_10 + sum_SE_10)), "" + sum_B_0_2, String.format("%.2f%%", B_0_2 * 100.0 / total), "" + B_0_2, "من 0-  2  هك"},
                {String.format("%.2f%%", sum_B_2_5 * 100.0 / (sum_B_0_2 + sum_B_2_5 + sum_B_5_10 + sum_SE_10)), "" + sum_B_2_5, String.format("%.2f%%", B_2_5 * 100.0 / total), "" + B_2_5, "من 2-5  هك"},
                {String.format("%.2f%%", (sum_B_0_2 + sum_B_2_5) * 100.0 / (sum_B_0_2 + sum_B_2_5 + sum_B_5_10 + sum_SE_10)), "" + (sum_B_0_2 + sum_B_2_5), String.format("%.2f%%", (B_0_2 + B_2_5) * 100.0 / total), "" + (B_0_2 + B_2_5), "دون 5  هك"},
                {String.format("%.2f%%", sum_B_5_10 * 100.0 / (sum_B_0_2 + sum_B_2_5 + sum_B_5_10 + sum_SE_10)), "" + sum_B_5_10, String.format("%.2f%%", B_5_10 * 100.0 / total), "" + B_5_10, "من 5-10  هك"},
                {String.format("%.2f%%", sum_SE_10 * 100.0 / (sum_B_0_2 + sum_B_2_5 + sum_B_5_10 + sum_SE_10)), "" + sum_SE_10, String.format("%.2f%%", SE_10 * 100.0 / total), "" + SE_10, "اكثر من 10  هك"},
                {String.format("%.2f%%", (sum_B_0_2 + sum_B_2_5 + sum_B_5_10 + sum_SE_10) * 100.0 / (sum_B_0_2 + sum_B_2_5 + sum_B_5_10 + sum_SE_10)), "" + (sum_B_0_2 + sum_B_2_5 + sum_B_5_10 + sum_SE_10), "--", "" + total, "المجموع"},
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
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieDao.getPersonsByTotalRange(-1, 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));

                        break;
                    case 1:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieDao.getPersonsByTotalRange(0, 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 2:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieDao.getPersonsByTotalRange(2, 5, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 3:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieDao.getPersonsByTotalLowerThan(5, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 4:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieDao.getPersonsByTotalRange(5, 10, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 5:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieDao.getPersonsByTotalGreaterThan(10, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
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


