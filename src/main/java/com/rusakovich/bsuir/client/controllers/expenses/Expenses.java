package com.rusakovich.bsuir.client.controllers.expenses;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.client.controllers.ApplicationPane;
import com.rusakovich.bsuir.client.controllers.bankAccounts.BankAccounts;
import com.rusakovich.bsuir.client.controllers.categories.Categories;
import com.rusakovich.bsuir.client.filters.expense.BankAccountExpenseFilter;
import com.rusakovich.bsuir.client.filters.expense.CategoryExpenseFilter;
import com.rusakovich.bsuir.client.filters.expense.DateExpenseFilter;
import com.rusakovich.bsuir.client.filters.expense.ExpenseFilter;
import com.rusakovich.bsuir.server.entity.BankAccount;
import com.rusakovich.bsuir.server.entity.Category;
import com.rusakovich.bsuir.server.entity.Expense;
import com.rusakovich.bsuir.server.entity.Income;
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
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Expenses extends ApplicationPane {

    @FXML
    private TableView<Expense> summaryTable;
    @FXML
    private TableColumn<Expense, String> summaryColumn;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
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
    private TableView<Expense> table;
    @FXML
    private Label message;
    @FXML
    private Button deleteBtn;
    @FXML
    private CheckBox selectAllCheckbox;

    @FXML
    public void initialize() {
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ApplicationContext store = ApplicationContext.getInstance();

        if(store.getExpenseCategories() == null) {
            store.setExpenseCategories(Categories.getCategoriesFromDB("expense_category"));
        }

        if(store.getBankAccounts() == null) {
            store.setBankAccounts(BankAccounts.getBankAccountsFromDB());
        }

        table.setPlaceholder(new Label("Загрузка ..."));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        sumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));
        summaryColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));
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
        bankAccountsFilterList.setConverter(new StringConverter<>() {
            @Override
            public String toString(BankAccount bankAccount) {
                if(bankAccount == null) {
                    return "";
                }
                return bankAccount.getName();
            }

            @Override
            public BankAccount fromString(final String string) {
                BankAccount bankAccount = new BankAccount();
                bankAccount.setName(string);
                return bankAccount;
            }
        });
        categoriesFilterList.setConverter(new StringConverter<>() {
            @Override
            public String toString(Category category) {
                if(category == null) {
                    return "";
                }
                return category.getName();
            }

            @Override
            public Category fromString(final String string) {
                Category category = new Category();
                category.setName(string);
                return category;
            }
        });
        bankAccountsFilterList.setItems(FXCollections.observableArrayList(store.getBankAccounts()));
        categoriesFilterList.setItems(FXCollections.observableArrayList(store.getExpenseCategories()));
        updateTableContent();
    }

    @FXML
    public void addExpense() {
        if(ApplicationContext.getInstance().getBankAccounts().size() ==0) {
            showBankAccountsModal();
            return;
        }
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
            ObservableList<Expense> selectedExpenses = table.getSelectionModel().getSelectedItems();

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
        ObservableList<Expense> selectedExpenses = table.getSelectionModel().getSelectedItems();
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
        updateSummaryTable(expenses);
    }

    @FXML
    private void selectAll() {
        if(selectAllCheckbox.isSelected()) {
            table.getSelectionModel().selectAll();
        } else {
            table.getSelectionModel().clearSelection();
        }
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
                    expenses.add(Expense.fromMap(expenseParams));
                }
            }
        }
        return expenses;
    }

    public static Float getExpenseSummary(ArrayList<Expense> expenses) {
        Float sumSum = 0.0F;
        for(Expense expense: expenses) {
            sumSum += expense.getSum();
        }
        return sumSum;
    }

    public void applyFilter(ActionEvent actionEvent) {
        LocalDate startDateValue = startDate.getValue();
        LocalDate endDateValue = endDate.getValue();
        Category category = categoriesFilterList.getValue();
        BankAccount bankAccount = bankAccountsFilterList.getValue();

        ExpenseFilter.FilterBuilder filter = new ExpenseFilter.FilterBuilder();

        if(startDateValue != null && endDateValue != null) {
            DateExpenseFilter dateFilter = new DateExpenseFilter(startDateValue, endDateValue);
            filter.addFilter(dateFilter);
        }

        if(category != null) {
            CategoryExpenseFilter categoryFilter = new CategoryExpenseFilter(category.getId());
            filter.addFilter(categoryFilter);
        }

        if(bankAccount != null) {
            BankAccountExpenseFilter bankAccountFilter = new BankAccountExpenseFilter(bankAccount.getId());
            filter.addFilter(bankAccountFilter);
        }

        ArrayList<Expense> e = ApplicationContext.getInstance().getExpenses();

        ArrayList<Expense> filteredExpenses = filter.build().filter(e);
        table.setItems(FXCollections.observableArrayList(filteredExpenses));
        updateSummaryTable(filteredExpenses);
    }

    public void removeFilter(ActionEvent actionEvent) {
        resetStartDate(null);
        resetEndDate(null);
        categoriesFilterList.setValue(null);
        bankAccountsFilterList.setValue(null);
        updateTableContent();
    }

    public void resetStartDate(ActionEvent actionEvent) {
        startDate.setValue(null);
    }

    public void resetEndDate(ActionEvent actionEvent) {
        endDate.setValue(null);
    }

    private void updateSummaryTable(ArrayList<Expense> expenses) {
        Expense summaryExpense = new Expense();
        summaryExpense.setSum(getExpenseSummary(expenses));
        summaryTable.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        summaryTable.getItems().add(summaryExpense);
    }
    private void showBankAccountsModal() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle("");
        alert.setHeaderText("Нет информации о счетах семьи. Добаление расходов заблокировано");
        alert.setContentText("Запись о расходе должна быть привязна к счёту семьи, но записей о счетах нет. Для устранения этой проблемы, перейдите во вкладку 'Счета' " +
                "и добавьте информацию о счёте. После вы сможете добавить запись о расходе");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            parentController.switchMainPane("../views/bankAccounts/BankAccounts.fxml");
        }
    }
}
