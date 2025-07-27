import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SampleDataInserter {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:expenses.db";

        String sql = "INSERT INTO expenses (amount, category, date, type, month) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Sample expense entries
            insertExpense(pstmt, 1500.0, "Food", "2025-07-01", "Credit", "July");
            insertExpense(pstmt, 2500.0, "Transport", "2025-07-02", "Cash", "July");
            insertExpense(pstmt, 3000.0, "Utilities", "2025-07-03", "UPI", "July");
            insertExpense(pstmt, 1800.0, "Entertainment", "2025-07-04", "Debit", "July");

            System.out.println("✅ Sample expenses inserted successfully.");
        } catch (Exception e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
    }

    private static void insertExpense(PreparedStatement pstmt, double amount, String category, String date, String type, String month) throws Exception {
        pstmt.setDouble(1, amount);
        pstmt.setString(2, category);
        pstmt.setString(3, date);
        pstmt.setString(4, type);
        pstmt.setString(5, month);
        pstmt.executeUpdate();
    }
}
