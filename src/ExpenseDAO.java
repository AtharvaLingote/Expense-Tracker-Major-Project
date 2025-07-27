import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
    private static final String DB_URL = "jdbc:sqlite:../db/expenses.db";

    public static void addExpense(Expense expense) {
        String sql = "INSERT INTO expenses (category_id, amount, description, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, expense.getCategoryId());
            pstmt.setDouble(2, expense.getAmount());
            pstmt.setString(3, expense.getDescription());
            pstmt.setString(4, expense.getDate());
            pstmt.executeUpdate();
            System.out.println("Expense added.");
        } catch (SQLException e) {
            System.out.println("Failed to add expense: " + e.getMessage());
        }
    }

    public static List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Expense exp = new Expense(
                    rs.getInt("id"),
                    rs.getInt("category_id"),
                    rs.getDouble("amount"),
                    rs.getString("description"),
                    rs.getString("date")
                );
                expenses.add(exp);
            }

        } catch (SQLException e) {
            System.out.println("Failed to fetch expenses: " + e.getMessage());
        }

        return expenses;
    }
}
