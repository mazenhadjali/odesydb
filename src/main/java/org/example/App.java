package org.example;

import org.example.dao.PersonneDaoImpl;
import org.example.gui.SwingJFrameExample;

import javax.swing.*;


/**
 * Hello world!
 */
public class App {
    private static final PersonneDaoImpl personneDao = new PersonneDaoImpl();


    public static void main(String[] args) {
        System.out.println("Hello World!");
        MySQLConnection databaseConnection = MySQLConnection.getInstance();






        SwingUtilities.invokeLater(() -> {
            SwingJFrameExample Settingsframe = SwingJFrameExample.getInstance();
            Settingsframe.setVisible(true);
        });

    }
}
