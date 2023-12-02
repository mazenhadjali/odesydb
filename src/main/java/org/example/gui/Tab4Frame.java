package org.example.gui;

import org.example.Utils.PrintableFrame;
import org.example.dao.PersonneDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tab4Frame extends PrintableFrame {
    private static final PersonneDaoImpl personneDao = new PersonneDaoImpl();

    int mm, md, mv, mc;
    int fm, fd, fv, fc;

    long totalf, totalm;


    public Tab4Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        mm = personneDao.countPersonsByEtatSociale(2, 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        md = personneDao.countPersonsByEtatSociale(2, 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        mv = personneDao.countPersonsByEtatSociale(2, 3, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        mc = personneDao.countPersonsByEtatSociale(2, 4, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        fm = personneDao.countPersonsByEtatSociale(1, 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        fd = personneDao.countPersonsByEtatSociale(1, 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        fv = personneDao.countPersonsByEtatSociale(1, 3, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        fc = personneDao.countPersonsByEtatSociale(1, 4, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        totalm = personneDao.countPersonneMale(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        totalf = personneDao.countPersonneFemale(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        // Set the title of the JFrame
        setTitle("جدول 4 :الحالة الاجتماعية  (رجال و نساء رؤساء العائلات)");

        // Set the initial size of the JFrame
        setSize(800, 350);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"الحالة الاجتماعية", "عدد الرجال", "نسبة الرجال", "عدد النساء", "نسبة النساء", "مجموع", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", ((fm + mm) * 100.0 / (totalf + totalm))), (fm + mm) + "", String.format("%.2f%%", (fm * 100.0 / (totalf + totalm))), fm + "", String.format("%.2f%%", (mm * 100.0 / (totalf + totalm))), "" + mm, "متزوج"},
                {String.format("%.2f%%", ((fd + md) * 100.0 / (totalf + totalm))), (fd + md) + "", String.format("%.2f%%", (fd * 100.0 / (totalf + totalm))), fd + "", String.format("%.2f%%", (md * 100.0 / (totalf + totalm))), "" + md, " مطلق"},
                {String.format("%.2f%%", ((fv + mv) * 100.0 / (totalf + totalm))), (fv + mv) + "", String.format("%.2f%%", (fv * 100.0 / (totalf + totalm))), fv + "", String.format("%.2f%%", (mv * 100.0 / (totalf + totalm))), "" + mv, "أرمل"},
                {String.format("%.2f%%", ((fc + mc) * 100.0 / (totalf + totalm))), (fc + mc) + "", String.format("%.2f%%", (fc * 100.0 / (totalf + totalm))), fc + "", String.format("%.2f%%", (mc * 100.0 / (totalf + totalm))), "" + mc, "اعزب"},
                {String.format("%.2f%%", ((fm + fd + fv + fc + mc + mv + md + mm) * 100.0 / (totalf + totalm))), "" + (fm + fd + fv + fc + mc + mv + md + mm), String.format("%.2f%%", ((fm + fd + fv + fc) * 100.0 / (totalf + totalm))), "" + (fm + fd + fv + fc), String.format("%.2f%%", ((mc + mv + md + mm) * 100.0 / (totalf + totalm))), "" + (mc + mv + md + mm), "المجموع"},
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
        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Create a "File" menu
        JMenu fileMenu = new JMenu("ملف");
        menuBar.add(fileMenu);

        // Create a "Print" menu item
        JMenuItem printMenuItem = new JMenuItem("طباعة");
        fileMenu.add(printMenuItem);

        // Add an ActionListener to the "Print" menu item
        printMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the print method to print the frame content
                saveTableAsImage(table);
            }
        });
    }

}


