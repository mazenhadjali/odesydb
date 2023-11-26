package org.example.gui;

import org.example.dao.AnimauxDaoImpl;
import org.example.dao.PersonneDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tab24Frame extends JFrame {
    private static final AnimauxDaoImpl animauxDao = new AnimauxDaoImpl();

    int ovins_chep;
    int count_chep;
    int ovins_chev;
    int count_chev;
    int ovins_bet;
    int count_bet;
    int vaches_total;
    int count_vaches_total;
    int total;

    public Tab24Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {


        ovins_chep = animauxDao.sumfromAnimauxByColumn("ovins_chep", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        ovins_chev = animauxDao.sumfromAnimauxByColumn("ovins_chev", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        ovins_bet = animauxDao.sumfromAnimauxByColumn("ovins_bet", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        vaches_total = animauxDao.sumfromAnimauxByColumn("vaches_total", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);


        count_chep = animauxDao.countByColumnNotEqualsToZero("ovins_chep", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        count_chev = animauxDao.countByColumnNotEqualsToZero("ovins_chev", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        count_bet = animauxDao.countByColumnNotEqualsToZero("ovins_bet", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        count_vaches_total = animauxDao.countByColumnNotEqualsToZero("vaches_total", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        total = ovins_chep + ovins_chev + ovins_bet + vaches_total;
        // Set the title of the JFrame
        setTitle("جدول 25 :الماشية");

        // Set the initial size of the JFrame
        setSize(800, 250);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"", "عدد الرؤوس", "النسبة", "عدد الأشخاص"};
        String[][] data = {
                {"" + count_chep, String.format("%.2f%%", ovins_chep * 100.0 / total), "" + ovins_chep, "أغنام"},
                {"" + count_chev, String.format("%.2f%%", ovins_chev * 100.0 / total), "" + ovins_chev, "ماعز"},
                {"" + count_bet, String.format("%.2f%%", ovins_bet * 100.0 / total), "" + ovins_bet, "الدواب"},
                {"" + count_vaches_total, String.format("%.2f%%", vaches_total * 100.0 / total), "" + vaches_total, "مجموع الابقار"},
                { "" + (count_chep + count_chev + count_bet + count_vaches_total), "--", "" + total, "المجموع"},
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
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(animauxDao.getPersonsByColumnNotEqualTo("ovins_chep", 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 1:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(animauxDao.getPersonsByColumnNotEqualTo("ovins_chev", 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 2:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(animauxDao.getPersonsByColumnNotEqualTo("ovins_bet", 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 3:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(animauxDao.getPersonsByColumnNotEqualTo("vaches_total", 0, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
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


