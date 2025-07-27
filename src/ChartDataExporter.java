import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ChartDataExporter {

    private static final String DB_URL = "jdbc:sqlite:expenses.db";

    public static void exportToCSV(String month) {
        String csvFile = "expense_chart_" + month + ".csv";
        String sql = "SELECT type, SUM(amount) as total FROM expenses WHERE strftime('%m', date) = ? GROUP BY type";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             FileWriter writer = new FileWriter(csvFile)) {

            pstmt.setString(1, month);
            ResultSet rs = pstmt.executeQuery();

            writer.append("Type,Total\n");

            while (rs.next()) {
                writer.append(rs.getString("type")).append(",");
                writer.append(String.valueOf(rs.getDouble("total"))).append("\n");
            }

            writer.flush();
            System.out.println("✅ CSV file exported: " + csvFile);

        } catch (SQLException | IOException e) {
            System.err.println("Error exporting to CSV: " + e.getMessage());
        }
    }

    // For direct testing
    public static void main(String[] args) {
        exportToCSV("07");  // Example: July
    }
}
