package com.rusakovich.bsuir.client.controllers.reports;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.controllers.ApplicationPane;
import com.rusakovich.bsuir.client.controllers.bankAccounts.BankAccounts;
import com.rusakovich.bsuir.client.controllers.categories.Categories;
import com.rusakovich.bsuir.client.controllers.expenses.Expenses;
import com.rusakovich.bsuir.client.controllers.income.Incomes;
import com.rusakovich.bsuir.client.filters.expense.DateExpenseFilter;
import com.rusakovich.bsuir.client.filters.expense.ExpenseFilter;
import com.rusakovich.bsuir.client.filters.income.DateIncomeFilter;
import com.rusakovich.bsuir.client.filters.income.IncomeFilter;
import com.rusakovich.bsuir.server.entity.Expense;
import com.rusakovich.bsuir.server.entity.Income;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Reports extends ApplicationPane {
    public TableView<Income> iTable;
    public TableColumn<Income, String> iIdColumn;
    public TableColumn<Income, String> iBankAccountColumn;
    public TableColumn<Income, String> iCategoryColumn;
    public TableColumn<Income, String> iSumColumn;
    public TableView<Expense> eTable;
    public TableColumn<Expense, String> eIdColumn;
    public TableColumn<Expense, String> eBankAccountColumn;
    public TableColumn<Expense, String> eCategoryColumn;
    public TableColumn<Expense, String> eSumColumn;
    public DatePicker startDate;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    public Button generateBtn;
    public Label incomeSummary;
    public Label expenseSummary;
    public Label summary;
    public Label errorMessage;

    @FXML
    public void initialize() {
        ApplicationContext store = ApplicationContext.getInstance();

        if(store.getIncomeCategories() == null) {
            store.setIncomeCategories(Categories.getCategoriesFromDB("income_category"));
        }

        if(store.getExpenseCategories() == null) {
            store.setExpenseCategories(Categories.getCategoriesFromDB("expense_category"));
        }

        if(store.getIncomes() == null) {
            store.setIncomes(Incomes.getIncomesFromDB());
        }

        if(store.getExpenses() == null) {
            store.setExpenses(Expenses.getExpensesFromDB());
        }

        if(store.getBankAccounts() == null) {
            store.setBankAccounts(BankAccounts.getBankAccountsFromDB());
        }

        iIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        iSumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));
        eIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        eSumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));
        iCategoryColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        store.findIncomeCategoryById(cell.getValue().getCategoryId()).getName()
                )
        );
        iBankAccountColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        store.findBankAccountById(cell.getValue().getBankAccountId()).getName()
                )
        );

        eCategoryColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        store.findExpenseCategoryById(cell.getValue().getCategoryId()).getName()
                )
        );
        eBankAccountColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        store.findBankAccountById(cell.getValue().getBankAccountId()).getName()
                )
        );

        iTable.setPlaceholder(new Label("Сгенерируйте отчёт"));
        eTable.setPlaceholder(new Label("Сгенерируйте отчёт"));
    }

    public void generate() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        if(startDate == null || endDate == null) {
            errorMessage.setText("Выберите диапазон для отчёта");
            return;
        }

        ApplicationContext store = ApplicationContext.getInstance();

        DateExpenseFilter eDateFilter = new DateExpenseFilter(startDate, endDate);
        DateIncomeFilter iDateFilter = new DateIncomeFilter(startDate, endDate);

        ArrayList<Income> filteredIncomes = new IncomeFilter.FilterBuilder().addFilter(iDateFilter).build().filter(store.getIncomes());
        ArrayList<Expense> filteredExpenses = new ExpenseFilter.FilterBuilder().addFilter(eDateFilter).build().filter(store.getExpenses());

        Float incomesSum = Incomes.getIncomeSummary(filteredIncomes);
        Float expensesSum = Expenses.getExpenseSummary(filteredExpenses);
        float sum = incomesSum - expensesSum;

        iTable.setItems(FXCollections.observableArrayList(filteredIncomes));
        eTable.setItems(FXCollections.observableArrayList(filteredExpenses));

        incomeSummary.setText(incomesSum.toString());
        expenseSummary.setText(expensesSum.toString());

        summary.setText(Float.toString(sum));
    }

    public void showIncomeDiagByBankAccount() {
        showDiagram("income", "bankAccount");
    }

    public void showIncomeDiagByCategory() {
        showDiagram("income", "category");
    }

    public void showExpenseDiagByBankAccount() {
        showDiagram("expense", "bankAccount");
    }

    public void showExpenseDiagByCategory() {
        showDiagram("expense", "category");
    }

    private void showDiagram(String diagramType, String groupType){
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        if(startDate == null || endDate == null) {
            errorMessage.setText("Диаграмма может быть построена только по сгенерированному отчёту");
            return;
        }
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/reports/ShowDiagram.fxml"));
        try {
            Parent root = loader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            ShowDiagram showDiagramController = loader.getController();
            showDiagramController.setData(diagramType, groupType, startDate, endDate);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportReport() {
        System.out.println("export");
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        if(startDate == null || endDate == null) {
            errorMessage.setText("Экспорт доступен только после генерации отчёта");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(iTable.getScene().getWindow());

        if(file != null) {
            saveFile(file);
        }
    }

    private void saveFile(File file) {
        try {
            PrintWriter write = new PrintWriter(file);
            write.println(getExportString());
            write.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("Экспорт");
            alert.setContentText("Экспорт прошёд успешно!");
            alert.showAndWait();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            errorMessage.setText("Произошла ошибка при экспортировании");
        }

    }

    private String getExportString() {
        ApplicationContext store = ApplicationContext.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        StringBuilder str = new StringBuilder("Отчёт о семейном бюджете на период с " + startDatePicker.getValue().format(formatter) +
                " по " + endDatePicker.getValue().format(formatter) + "\n\n");
        str.append("Доходы:\n");

        ObservableList<Income> incomes = iTable.getItems();
        ObservableList<Expense> expenses = eTable.getItems();

        Float incomesSum = Incomes.getIncomeSummary(new ArrayList<>(incomes));
        Float expensesSum = Expenses.getExpenseSummary(new ArrayList<>(expenses));
        float sum = incomesSum - expensesSum;


        for(int i = 0; i < incomes.size(); i++) {
            Income income = incomes.get(i);
            String category = store.findIncomeCategoryById(income.getCategoryId()).getName();
            String bankAccount = store.findBankAccountById(income.getBankAccountId()).getName();
            str.append(i + 1).append(". ").append(income.getDate().format(formatter)).append(" ").append(category).append(" ").append(income.getSum()).append(" ").append("  ").append(bankAccount).append("\n");
        }
        str.append("Итого(доходы): ").append(incomesSum).append("\n\n");

        str.append("Расходы:\n");
        for(int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);
            String category = store.findExpenseCategoryById(expense.getCategoryId()).getName();
            String bankAccount = store.findBankAccountById(expense.getBankAccountId()).getName();
            str.append(i + 1).append(". ").append(expense.getDate().format(formatter)).append(" ").append(category).append(" ").append(expense.getSum()).append(" ").append("  ").append(bankAccount).append("\n");
        }
        str.append("Итого(расходы): ").append(expensesSum).append("\n\n");
        str.append("----------------------------------------------------------\n\n");
        str.append("Бюджет семьи составляет: ").append(sum).append("\n");

        return str.toString();
    }
}
