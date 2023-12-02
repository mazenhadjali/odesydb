package org.example.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PrintableFrame extends JFrame {

    // Method to save a JTable as an image
    public void saveTableAsImage(JTable table) {
        // Get the size of the table
        Dimension tableSize = table.getSize();

        // Create a BufferedImage with the size of the table, including the header
        BufferedImage image = new BufferedImage(tableSize.width, tableSize.height + table.getTableHeader().getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();

        // Draw the table header onto the image
        table.getTableHeader().paint(g);

        // Move the graphics context down to the height of the header
        g.translate(0, table.getTableHeader().getHeight());

        // Paint the table onto the image
        table.print(g);

        try {
            // Let the user choose the file to save
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("حفظ كصورة");
            fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Image (*.png)", "png"));
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();

                // Ensure the file has a .png extension
                if (!filePath.toLowerCase().endsWith(".png")) {
                    fileToSave = new File(filePath + ".png");
                }

                // Save the image as a PNG file
                ImageIO.write(image, "png", fileToSave);
                JOptionPane.showMessageDialog(this, "تم حفظ الصورة بنجاح.", "نجاح", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving image: " + ex.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
