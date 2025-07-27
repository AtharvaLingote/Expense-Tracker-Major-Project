import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:sqlite:../db/expenses.db";

    public static void initializeDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");

            Connection conn = DriverManager.getConnection(DB_URL);

            if (conn != null) {
                Statement stmt = conn.createStatement();

                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS categories (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL UNIQUE
                    );
                """);

                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS expenses (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        category_id INTEGER,
                        amount REAL NOT NULL,
                        description TEXT,
                        date TEXT NOT NULL,
                        FOREIGN KEY (category_id) REFERENCES categories(id)
                    );
                """);

                System.out.println("Database initialized successfully.");
            }
        } catch (Exception e) {
            System.out.println("Database initialization failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
    }
}