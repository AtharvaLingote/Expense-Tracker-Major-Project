import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.time.LocalDate;
import java.util.List;

public class ExpenseTrackerApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Expense Tracker");

        TextField categoryField = new TextField();
        categoryField.setPromptText("Category ID (e.g., 1)");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        TextField descField = new TextField();
        descField.setPromptText("Description");

        DatePicker datePicker = new DatePicker(LocalDate.now());

        Button addBtn = new Button("Add Expense");
        Button showBtn = new Button("Show All Expenses");

        TableView<Expense> table = new TableView<>();
        TableColumn<Expense, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());

        TableColumn<Expense, Integer> catCol = new TableColumn<>("Category ID");
        catCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getCategoryId()).asObject());

        TableColumn<Expense, Double> amtCol = new TableColumn<>("Amount");
        amtCol.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getAmount()).asObject());

        TableColumn<Expense, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescription()));

        TableColumn<Expense, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDate()));

        table.getColumns().addAll(idCol, catCol, amtCol, descCol, dateCol);

        addBtn.setOnAction(e -> {
            try {
                int catId = Integer.parseInt(categoryField.getText());
                double amount = Double.parseDouble(amountField.getText());
                String desc = descField.getText();
                String date = datePicker.getValue().toString();

                Expense expense = new Expense(catId, amount, desc, date);
                ExpenseDAO.addExpense(expense);

                categoryField.clear();
                amountField.clear();
                descField.clear();
                datePicker.setValue(LocalDate.now());
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input!");
                alert.show();
            }
        });

        showBtn.setOnAction(e -> {
            table.getItems().clear();
            List<Expense> expenses = ExpenseDAO.getAllExpenses();
            table.getItems().addAll(expenses);
        });

        VBox inputBox = new VBox(10,
            new Label("Category ID:"), categoryField,
            new Label("Amount:"), amountField,
            new Label("Description:"), descField,
            new Label("Date:"), datePicker,
            addBtn, showBtn
        );

        inputBox.setPadding(new Insets(10));

        HBox root = new HBox(20, inputBox, table);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 850, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
