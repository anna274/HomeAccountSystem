package com.rusakovich.bsuir.client.controllers.expenses;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.client.controllers.ApplicationPane;
import com.rusakovich.bsuir.client.controllers.bankAccounts.BankAccounts;
import com.rusakovich.bsuir.client.controllers.categories.Categories;
import com.rusakovich.bsuir.server.entity.BankAccount;
import com.rusakovich.bsuir.server.entity.Category;
import com.rusakovich.bsuir.server.entity.Expense;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Expenses extends ApplicationPane {

    @FXML
    private Button applyFilterBtn;
    @FXML
    private Button removeFilterBtn;
    @FXML
    private ComboBox<BankAccount> bankAccountsFilterList;
    @FXML
    private ComboBox<Category> categoriesFilterList;
    @FXML
    private TableColumn<Long, String> idColumn;
    @FXML
    private TableColumn<Expense, String> dateColumn;
    @FXML
    private TableColumn<Expense, String> bankAccountColumn;
    @FXML
    private TableColumn<Expense, String> categoryColumn;
    @FXML
    private TableColumn<Expense, String> sumColumn;
    @FXML
    private TableColumn<Expense, String> noteColumn;
    @FXML
    private TableColumn<Expense, String> selectionColumn;
    @FXML
    private TableView<Expense> table;
    @FXML
    private Label message;
    @FXML
    private Button deleteBtn;
    @FXML
    private CheckBox selectAllCheckbox;

    @FXML
    public void initialize() {
        ApplicationContext store = ApplicationContext.getInstance();

        if(store.getExpenseCategories() == null) {
            store.setExpenseCategories(Categories.getCategoriesFromDB("expense_category"));
        }

        if(store.getBankAccounts() == null) {
            store.setBankAccounts(BankAccounts.getBankAccountsFromDB());
        }

        table.setPlaceholder(new Label("Загрузка ..."));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        selectionColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        sumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));
        noteColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        cell.getValue().getNote() != null ? cell.getValue().getNote() : ""
                )
        );
        dateColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        cell.getValue().getDate().format(DateTimeFormatter.ofPattern("dd MM yyyy"))
                )
        );
        categoryColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        store.findExpenseCategoryById(cell.getValue().getCategoryId()).getName()
                )
        );
        bankAccountColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        store.findBankAccountById(cell.getValue().getBankAccountId()).getName()
                )
        );
        updateTableContent();
    }

    @FXML
    public void addExpense() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/expenses/AddExpense.fxml"));
            Parent root = loader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            updateTableContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editExpense() {
        try {
            ArrayList<Expense> selectedExpenses = getSelectedExpenses();

            if(selectedExpenses.size() == 0){
                message.setText("Для редактирования выделите запись из таблицы");
                return;
            }
            if(selectedExpenses.size() > 1){
                message.setText("Функция 'Редактировать' активна только при одной выделенной записи");
                return;
            }
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/expenses/EditExpense.fxml"));
            Parent root = loader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            EditExpense editExpenseController = loader.getController();
            editExpenseController.setEditedExpense(selectedExpenses.get(0));

            stage.showAndWait();

            updateTableContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteSelectedExpenses() {
        ArrayList<Expense> selectedExpenses = getSelectedExpenses();
        int selectedNumber = selectedExpenses.size();

        if(selectedNumber == 0) {
            message.setText("Выберите записи для удаления");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Удаление записей");
        alert.setContentText("Вы уверены, что хотите удалить " +  selectedNumber + " записей?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            deleteBtn.setText("Удаление...");
            for(Expense expense : selectedExpenses) {
                String query = "expense?command=delete&id=" + expense.getId();
                Client.doRequest(query);
            }
            updateTableContent();
            deleteBtn.setText("Удалить");
        }
    }

    private ArrayList<Expense> getSelectedExpenses() {
        ArrayList<Expense> selectedExpenses = new ArrayList<>();
        ObservableList<Expense> expenses = table.getItems();
        for(Expense expense: expenses) {
            if(expense.getSelected().isSelected()) {
                selectedExpenses.add(expense);
            }
        }
        return selectedExpenses;
    }

    private void updateTableContent() {
        ArrayList<Expense> expenses = getExpensesFromDB();
        ApplicationContext.getInstance().setExpenses(expenses);
        table.setItems(FXCollections.observableArrayList(expenses));
        if(expenses.size() != 0) {
            table.setPlaceholder(new Label("Добавьте записи. Для этого нажмите кнопку 'Добавить'."));
        } else {
            table.setPlaceholder(new Label(""));
        }
        message.setText("");
    }

    @FXML
    private void selectAll() {
        ObservableList<Expense> expenses = table.getItems();
        for(Expense expense: expenses) {
            expense.getSelected().setSelected(selectAllCheckbox.isSelected());
        }
        table.setItems(expenses);
    }

    public static ArrayList<Expense> getExpensesFromDB(){
        Long accountId = ApplicationContext.getInstance().getCurrentAccount().getId();
        String query = "expense?command=getAllByMemberAccountId&memberAccountId=" + accountId;
        Map<String, String> params = Client.doRequest(query);
        ArrayList<Expense> expenses = new ArrayList<>();
        if(params.get("status").equals("ok")) {
            if(params.containsKey("data")) {
                ArrayList<Map<String, String>> expensesParams = Client.getResponseArray(params.get("data"));

                for(Map<String, String> expenseParams : expensesParams){
                    Expense expense = Expense.fromMap(expenseParams);
                    expense.setSelected(new CheckBox());
                    expenses.add(expense);
                }
            }
        }
        return expenses;
    }

    public void applyFilter(ActionEvent actionEvent) {
    }

    public void removeFilter(ActionEvent actionEvent) {
    }
}
