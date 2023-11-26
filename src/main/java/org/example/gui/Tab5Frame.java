package org.example.gui;

import org.example.dao.PersonneDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class Tab5Frame extends JFrame {
    private static final PersonneDaoImpl personneDao = new PersonneDaoImpl();
    long totalf, totalm;

    int me, mne;
    int fe, fne;

    int presenceM;
    int presenceF;


    public Tab5Frame(String selectedGouvernorat, String selectedDelegation, String selectedSecteur, String selectedComplexeRes) {

        me = personneDao.countPersonsByExploitation(2, 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        mne = personneDao.countPersonsByExploitation(2, 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        fe = personneDao.countPersonsByExploitation(1, 1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        fne = personneDao.countPersonsByExploitation(1, 2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);


        totalm = personneDao.countPersonneMale(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        totalf = personneDao.countPersonneFemale(selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);

        presenceM = personneDao.countPresenceFromPersons(2, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        presenceF = personneDao.countPresenceFromPersons(1, selectedGouvernorat, selectedDelegation, selectedSecteur, selectedComplexeRes);
        // Set the title of the JFrame
        setTitle(" جدول 5 : له مستغلة (رجال و نساء رؤساء العائلات)");

        // Set the initial size of the JFrame
        setSize(800, 250);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Create a table with 3 rows and 3 columns
        String[] columnNames = {"", "عدد الرجال", "نسبة الرجال", "عدد النساء", "نسبة النساء", "مجموع", "النسبة"};
        String[][] data = {
                {String.format("%.2f%%", ((fe + me) * 100.0 / (totalf + totalm))), "" + (fe + me), String.format("%.2f%%", (fe * 100.0 / (totalf + totalm))), "" + fe, String.format("%.2f%%", (me * 100.0 / (totalf + totalm))), "" + me, "له مستغلة"},
                {String.format("%.2f%%", ((fne + mne) * 100.0 / (totalf + totalm))), "" + (fne + mne), String.format("%.2f%%", (fne * 100.0 / (totalf + totalm))), "" + fne, String.format("%.2f%%", (mne * 100.0 / (totalf + totalm))), "" + mne, "ليس له مستغلة"},
                {String.format("%.2f%%", ((fe + fne + me + mne) * 100.0 / (totalf + totalm))), "" + (fe + fne + me + mne) , String.format("%.2f%%", ((fe + fne) * 100.0 / (totalf + totalm))), "" + (fe + fne), String.format("%.2f%%", ((me + mne) * 100.0 / (totalf + totalm))), "" + (me + mne), "المجموع"},
                {"--","--", String.format("%.2f", ((presenceF) * 1.0 / (me + fe))), "--" , String.format("%.2f", (presenceM * 1.0 / (me + fe))),  "--" , " معدل الحضور"},
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
    }

}


