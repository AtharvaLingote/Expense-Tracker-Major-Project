import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class FixExpensesTable {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:expenses.db";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            String dropTable = "DROP TABLE IF EXISTS expenses;";
            String createTable = "CREATE TABLE IF NOT EXISTS expenses (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "date TEXT NOT NULL," +
                    "amount REAL NOT NULL," +
                    "category TEXT NOT NULL," +
                    "type TEXT NOT NULL," +
                    "month TEXT NOT NULL" +
                    ");";

            stmt.executeUpdate(dropTable);
            stmt.executeUpdate(createTable);

            System.out.println("✔ Table recreated with correct schema.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
