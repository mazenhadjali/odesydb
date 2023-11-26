package org.example.gui;

import org.example.ExcelFileReader;
import org.example.MySQLConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SwingJFrameExample extends JFrame {
    private static SwingJFrameExample instance;
    private JPanel mainPanel;
    private JButton selectFileButton;
    private JButton submitClearButton;
    private JButton dashboardButton;

    File selectedFile;

    private SwingJFrameExample() {
        setTitle("تصنيف المعطيات على مستوى الاسر");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new GridLayout(4, 1));
        selectFileButton = new JButton("sélèctionner le fichier  ");
        submitClearButton = new JButton("confirmer votre choix");
        dashboardButton = new JButton("Bienvenue");


        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Le fichier:", "xlsx");
                fileChooser.setFileFilter(filter);

                int returnVal = fileChooser.showOpenDialog(SwingJFrameExample.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    // Handle the selected file here
                    JOptionPane.showMessageDialog(SwingJFrameExample.this, "le fichier selectionné: " + selectedFile.getAbsolutePath());
                }
            }
        });

        submitClearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                MySQLConnection.dropTable("personne");
                MySQLConnection.dropTable("conjoint");
                MySQLConnection.dropTable("rep_enfants");
                MySQLConnection.dropTable("logement");
                MySQLConnection.dropTable("superficie");
                MySQLConnection.dropTable("superficielab");
                MySQLConnection.dropTable("animaux");

                MySQLConnection.createConjointTable();
                MySQLConnection.createRepEnfantsTable();
                MySQLConnection.createLogementTable();
                MySQLConnection.createSuperficieTable();
                MySQLConnection.createSuperficieLabTable();
                MySQLConnection.createAnimauxTable();
                MySQLConnection.createPersonneTable();


                if (selectedFile != null) {
                    List<String> red = ExcelFileReader.readLines(selectedFile);
                    JOptionPane.showMessageDialog(SwingJFrameExample.this, "la base donnée est bien injectée avec:" + red.size() + " redondances");
                    // JOptionPane.showMessageDialog(SwingJFrameExample.this, red.toString());

                } else {
                    JOptionPane.showMessageDialog(SwingJFrameExample.this, "Database cleared.");
                }


            }
        });

        dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SingletonJFrame1.getInstance();
                SingletonJFrame2.getInstance();
                SingletonJFrame3.getInstance();
                SingletonJFrame4.getInstance();
            }
        });

        mainPanel.add(new JLabel()); // Placeholder for spacing
        mainPanel.add(selectFileButton);
        mainPanel.add(submitClearButton);
        mainPanel.add(dashboardButton);

        add(mainPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null); // Center the JFrame on the screen
    }

    public static SwingJFrameExample getInstance() {
        if (instance == null) {
            instance = new SwingJFrameExample();
        }
        return instance;
    }
}
