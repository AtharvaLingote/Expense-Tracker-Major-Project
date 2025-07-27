import java.time.LocalDate;

public class TestAddExpense {
    public static void main(String[] args) {
        Expense e = new Expense(1, 250.0, "Groceries", LocalDate.now().toString());
        ExpenseDAO.addExpense(e);
    }
}
