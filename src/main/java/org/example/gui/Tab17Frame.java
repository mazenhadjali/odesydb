package org.example.gui;

import org.example.dao.PersonneDaoImpl;
import org.example.dao.SuperficieDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Tab17Frame extends JFrame {
    private static final SuperficieDaoImpl superficieDao = new SuperficieDaoImpl();

    Double prop;

    int count_prop;
    Double louer;
    int count_louer;

    Double surChiites;

    int count_surChiites;

    Double total;
    Double nombre;


    public Tab17Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        prop = superficieDao.sumfromSuperficieByColumn("prop", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        louer = superficieDao.sumfromSuperficieByColumn("louer", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        surChiites = superficieDao.sumfromSuperficieByColumn("surChiites", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        total = superficieDao.sumfromSuperficieByColumn("total", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        nombre = superficieDao.sumfromSuperficieByColumn("nombre", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);


        count_prop = superficieDao.countFromSuperficieByColumn("prop", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        count_louer = superficieDao.countFromSuperficieByColumn("louer", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        count_surChiites = superficieDao.countFromSuperficieByColumn("surChiites", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        // Set the title of the JFrame
        setTitle("جدول 17 :مساحة الجملية (هك)");

        // Set the initial size of the JFrame
        setSize(800, 350);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"", "مساحة (هك)", "عدد الأشخاص", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", prop * 100.0 / total), "" + count_prop, String.format("%.2f", prop), "ملك"},
                {String.format("%.2f%%", louer * 100.0 / total), "" + count_louer, String.format("%.2f", louer), "كراء"},
                {String.format("%.2f%%", surChiites * 100.0 / total), "" + count_surChiites, String.format("%.2f", surChiites), "على الشياع"},
                {String.format("%.2f%%", (prop + louer + surChiites) * 100.0 / total), "" + (count_prop + count_louer + count_surChiites), String.format("%.2f", total), "مجموع المساحة (هك)"},
                {"--", "--", String.format("%.2f", nombre), "عدد القطع"},
        };

        JTable table = new JTable(data, columnNames);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                switch (row) {
                    case 0:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieDao.getPersonsByColumnNotEqualToZero("prop", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 1:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieDao.getPersonsByColumnNotEqualToZero("louer", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 2:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(superficieDao.getPersonsByColumnNotEqualToZero("surChiites", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    default:
                        // Handle any other cases here
                        break;
                }
            }
        });


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


