public class Expense {
    private int id;
    private int categoryId;
    private double amount;
    private String description;
    private String date; 

    public Expense(int categoryId, double amount, String description, String date) {
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Expense(int id, int categoryId, double amount, String description, String date) {
        this(categoryId, amount, description, date);
        this.id = id;
    }


    public int getId() { return id; }

    public int getCategoryId() { return categoryId; }

    public double getAmount() { return amount; }

    public String getDescription() { return description; }

    public String getDate() { return date; }

    public void setId(int id) { this.id = id; }

    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public void setAmount(double amount) { this.amount = amount; }

    public void setDescription(String description) { this.description = description; }

    public void setDate(String date) { this.date = date; }
}
