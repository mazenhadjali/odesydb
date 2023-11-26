package org.example.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class PersonTableInterface extends JFrame {

    private JTable table;

    public PersonTableInterface(List<Map<String, String>> data) {
        setTitle("جدول الأسماء");
        setSize(400, 850);

        // Define what happens when you close the JFrame
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Set the component orientation for the entire JFrame
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("بطاقة التعريف الوطنية");
        tableModel.addColumn("الأسماء");

        for (Map<String, String> entry : data) {
            tableModel.addRow(new Object[]{entry.get("name"), entry.get("cin")});
        }

        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);

        // Center-align cell data for all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

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
        setVisible(true);
    }

    public static void main(String[] args) {
        List<Map<String, String>> data = List.of(
                Map.of("name", "علي محمد", "cin", "123"),
                Map.of("name", "ليلى أحمد", "cin", "456"),
                Map.of("name", "محمد حسن", "cin", "789")
        );
        SwingUtilities.invokeLater(() -> new PersonTableInterface(data));
    }
}
