package org.example.gui;

import org.example.dao.RepEnfantsDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tab13Frame extends JFrame {
    private static final RepEnfantsDaoImpl repEnfantsDao = new RepEnfantsDaoImpl();


    int nb_enf_diplome_sup;
    int nb_enf_diplome_sup_chom;
    int nb_enf_cert_Pro;
    int nb_enf_cert_Pro_Chom;
    int nb_enf_sans_diplome_qualifie;
    int nb_enf_sans_diplome_non_qualifie;
    int sommetotalfilles;
    int sommetotalgarcons;

    int total;

    public Tab13Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        sommetotalfilles = repEnfantsDao.sumfromRepEnfByColumn("total_filles", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        sommetotalgarcons = repEnfantsDao.sumfromRepEnfByColumn("total_garcons", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        nb_enf_diplome_sup = repEnfantsDao.sumfromRepEnfByColumn("nb_enf_diplome_sup", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        nb_enf_diplome_sup_chom = repEnfantsDao.sumfromRepEnfByColumn("nb_enf_diplome_sup_chom", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        nb_enf_cert_Pro = repEnfantsDao.sumfromRepEnfByColumn("nb_enf_cert_Pro", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        nb_enf_cert_Pro_Chom = repEnfantsDao.sumfromRepEnfByColumn("nb_enf_cert_Pro_Chom", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        nb_enf_sans_diplome_qualifie = repEnfantsDao.sumfromRepEnfByColumn("nb_enf_sans_diplome_qualifie", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        nb_enf_sans_diplome_non_qualifie = repEnfantsDao.sumfromRepEnfByColumn("nb_enf_sans_diplome_non_qualifie", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        total = nb_enf_diplome_sup + nb_enf_cert_Pro + +nb_enf_sans_diplome_qualifie + nb_enf_sans_diplome_non_qualifie;

        // Set the title of the JFrame
        setTitle("جدول 13 :الشباب من 18 فما فوق");

        // Set the initial size of the JFrame
        setSize(800, 550);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        System.out.println(nb_enf_diplome_sup);
        System.out.println(nb_enf_diplome_sup_chom);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"", "العدد", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", (((nb_enf_diplome_sup * 100.0 / total)) > 0) ? (nb_enf_diplome_sup * 100.0 / total) : 0), "" + nb_enf_diplome_sup, "العدد الجملي من أصحاب شهائد عليا"},
                {String.format("%.2f%%", (((nb_enf_diplome_sup_chom * 100.0 / total)) > 0) ? (nb_enf_diplome_sup_chom * 100.0 / total) : 0), "" + nb_enf_diplome_sup_chom, "عدد العاطلين من أصحاب شهائد عليا"},
                {String.format("%.2f%%", (((nb_enf_cert_Pro * 100.0 / total)) > 0) ? (nb_enf_cert_Pro * 100.0 / total) : 0), "" + nb_enf_cert_Pro, "العدد الجملي من أصحاب شهائد تأهيل مهني"},
                {String.format("%.2f%%", (((nb_enf_cert_Pro_Chom * 100.0 / total)) > 0) ? (nb_enf_cert_Pro_Chom * 100.0 / total) : 0), "" + nb_enf_cert_Pro_Chom, "عدد العاطلين من أصحاب شهائد تأهيل مهني"},
                {String.format("%.2f%%", (((nb_enf_sans_diplome_qualifie * 100.0 / total)) > 0) ? (nb_enf_sans_diplome_qualifie * 100.0 / total) : 0), "" + nb_enf_sans_diplome_qualifie, "عدد العاطلين أصحاب مهارات "},
                {String.format("%.2f%%", (((nb_enf_sans_diplome_non_qualifie * 100.0 / total)) > 0) ? (nb_enf_sans_diplome_non_qualifie * 100.0 / total) : 0), "" + nb_enf_sans_diplome_non_qualifie, "عدد العاطلين بدون مهارات"},
                {"--", "" + total, "المجموع"},
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
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(repEnfantsDao.getListfromRepEnfByColumnDiffZeor("nb_enf_diplome_sup", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 1:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(repEnfantsDao.getListfromRepEnfByColumnDiffZeor("nb_enf_diplome_sup_chom", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 2:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(repEnfantsDao.getListfromRepEnfByColumnDiffZeor("nb_enf_cert_Pro", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 3:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(repEnfantsDao.getListfromRepEnfByColumnDiffZeor("nb_enf_cert_Pro_Chom", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 4:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(repEnfantsDao.getListfromRepEnfByColumnDiffZeor("nb_enf_sans_diplome_qualifie", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
                        break;
                    case 5:
                        SwingUtilities.invokeLater(() -> new PersonTableInterface(repEnfantsDao.getListfromRepEnfByColumnDiffZeor("nb_enf_sans_diplome_non_qualifie", selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes)));
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


