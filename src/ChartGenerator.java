import java.io.File;
import java.awt.image.BufferedImage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class ChartGenerator {

    private static final String DB_URL = "jdbc:sqlite:expenses.db";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Expense Breakdown");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 500);

            ChartPanel chartPanel = createChartPanel();
            frame.add(chartPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            JButton exportCSVBtn = new JButton("Export CSV");
            JButton exportPNGBtn = new JButton("Export PNG");

            JLabel statusLabel = new JLabel("Ready.");
            statusLabel.setForeground(Color.BLUE);

            buttonPanel.add(exportCSVBtn);
            buttonPanel.add(exportPNGBtn);
            frame.add(buttonPanel, BorderLayout.NORTH);
            frame.add(statusLabel, BorderLayout.SOUTH);

            exportCSVBtn.addActionListener((ActionEvent e) -> {
                try {
                    exportToCSV();
                    statusLabel.setText("CSV exported successfully.");
                } catch (Exception ex) {
                    statusLabel.setText("CSV export failed: " + ex.getMessage());
                }
            });

            exportPNGBtn.addActionListener((ActionEvent e) -> {
                try {
                    exportChartAsPNG(chartPanel.getChart());
                    statusLabel.setText("PNG exported successfully.");
                } catch (Exception ex) {
                    statusLabel.setText("PNG export failed: " + ex.getMessage());
                }
            });

            frame.setVisible(true);
        });
    }

    private static ChartPanel createChartPanel() {
        DefaultPieDataset dataset = getExpenseData();
        JFreeChart chart = ChartFactory.createPieChart(
                "Expenses by Type", dataset, true, true, false);
        return new ChartPanel(chart);
    }

    private static DefaultPieDataset getExpenseData() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT type, SUM(amount) FROM expenses GROUP BY type")) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dataset.setValue(rs.getString(1), rs.getDouble(2));
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
        return dataset;
    }

    private static void exportToCSV() throws IOException, SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM expenses");
             FileWriter csvWriter = new FileWriter("expenses_export.csv")) {

            csvWriter.write("id,date,amount,type,category,month\n");
            while (rs.next()) {
                csvWriter.write(rs.getInt("id") + "," +
                        rs.getString("date") + "," +
                        rs.getDouble("amount") + "," +
                        rs.getString("type") + "," +
                        rs.getString("category") + "," +
                        rs.getString("month") + "\n");
            }
        }
    }

    private static void exportChartAsPNG(JFreeChart chart) throws IOException {
        LocalDate today = LocalDate.now();
        String fileName = "expense_chart_" + today + ".png";
        File file = new File(fileName);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setSize(600, 400);
        chartPanel.doLayout();

        BufferedImage image = chart.createBufferedImage(600, 400);
        javax.imageio.ImageIO.write(image, "png", file);
    }
}
