import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class ExportToCSV {

    public static void main(String[] args) {
        String dbPath = "expenses.db"; // Assuming it's in src
        String csvFile = "expenses.csv";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, amount, category, date, type, month FROM expenses");
             FileWriter writer = new FileWriter(csvFile)) {

            // Write CSV headers
            writer.write("ID,Amount,Category,Date,Type,Month\n");

            // Write data
            while (rs.next()) {
                writer.write(
                        rs.getInt("id") + "," +
                        rs.getDouble("amount") + "," +
                        rs.getString("category") + "," +
                        rs.getString("date") + "," +
                        rs.getString("type") + "," +
                        rs.getString("month") + "\n"
                );
            }

            System.out.println("✅ Exported to " + csvFile + " successfully!");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("File writing error: " + e.getMessage());
        }
    }
}
