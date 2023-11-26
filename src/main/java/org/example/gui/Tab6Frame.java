package org.example.gui;

import org.example.dao.PersonneDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tab6Frame extends JFrame {
    private static final PersonneDaoImpl personneDao = new PersonneDaoImpl();


    int m_miniProjet, f_miniProjet;
    int m_non_miniProjet, f_non_miniProjet;
    long totalf, totalm;

    public Tab6Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {


        m_miniProjet = personneDao.countPersonsByMiniProjet(2, 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        m_non_miniProjet = personneDao.countPersonsByMiniProjet(2, 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        f_miniProjet = personneDao.countPersonsByMiniProjet(1, 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        f_non_miniProjet = personneDao.countPersonsByMiniProjet(1, 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        totalm = personneDao.countPersonneMale(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        totalf = personneDao.countPersonneFemale(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);


        // Set the title of the JFrame
        setTitle("جدول 6 :الانتفاع بمشروع صغير (رجال و نساء رؤساء العائلات");

        // Set the initial size of the JFrame
        setSize(800, 250);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"", "عدد الرجال", "نسبة الرجال", "عدد النساء", "نسبة النساء", "مجموع", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", ((m_miniProjet + f_miniProjet) * 100.0 / (totalf + totalm))), "" + (m_miniProjet + f_miniProjet), String.format("%.2f%%", (f_miniProjet * 100.0 / (totalf + totalm))), "" + f_miniProjet, String.format("%.2f%%", (m_miniProjet * 100.0 / (totalf + totalm))), "" + m_miniProjet, "منتفع بمشروع صغير"},
                {String.format("%.2f%%", ((m_non_miniProjet + f_non_miniProjet) * 100.0 / (totalf + totalm))), "" + (m_non_miniProjet + f_non_miniProjet), String.format("%.2f%%", (f_non_miniProjet * 100.0 / (totalf + totalm))), "" + f_non_miniProjet, String.format("%.2f%%", (m_non_miniProjet * 100.0 / (totalf + totalm))), "" + m_non_miniProjet, "غير منتفع بمشروع صغير"},
                {String.format("%.2f%%", ((f_miniProjet + f_non_miniProjet +m_miniProjet + m_non_miniProjet) * 100.0 / (totalf + totalm))), "" + (f_miniProjet + f_non_miniProjet +m_miniProjet + m_non_miniProjet), String.format("%.2f%%", (f_miniProjet + f_non_miniProjet * 100.0 / (totalf + totalm))), "" + (f_miniProjet + f_non_miniProjet), String.format("%.2f%%", ((m_miniProjet + m_non_miniProjet) * 100.0 / (totalf + totalm))), "" + (m_miniProjet + m_non_miniProjet), "المجموع"},
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
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(personneDao.getPersonsByColumnNotEqualTo("miniProjet", 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
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


