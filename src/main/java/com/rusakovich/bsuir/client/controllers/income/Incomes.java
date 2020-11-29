package com.rusakovich.bsuir.client.controllers.income;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.client.controllers.ApplicationPane;
import com.rusakovich.bsuir.client.controllers.bankAccounts.BankAccounts;
import com.rusakovich.bsuir.client.controllers.categories.Categories;
import com.rusakovich.bsuir.server.entity.BankAccount;
import com.rusakovich.bsuir.server.entity.Category;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Incomes extends ApplicationPane {

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
    private TableColumn<Income, String> dateColumn;
    @FXML
    private TableColumn<Income, String> bankAccountColumn;
    @FXML
    private TableColumn<Income, String> categoryColumn;
    @FXML
    private TableColumn<Income, String> sumColumn;
    @FXML
    private TableColumn<Income, String> noteColumn;
    @FXML
    private TableColumn<Income, String> selectionColumn;
    @FXML
    private TableView<Income> table;
    @FXML
    private Label message;
    @FXML
    private Button deleteBtn;
    @FXML
    private CheckBox selectAllCheckbox;

    @FXML
    public void initialize() {
        ApplicationContext store = ApplicationContext.getInstance();

        if(store.getIncomeCategories() == null) {
            store.setIncomeCategories(Categories.getCategoriesFromDB("income_category"));
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
                        store.findIncomeCategoryById(cell.getValue().getCategoryId()).getName()
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
    public void addIncome() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/income/AddIncome.fxml"));
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
    public void editIncome() {
        try {
            ArrayList<Income> selectedIncomes = getSelectedIncomes();

            if(selectedIncomes.size() == 0){
                message.setText("Для редактирования выделите запись из таблицы");
                return;
            }
            if(selectedIncomes.size() > 1){
                message.setText("Функция 'Редактировать' активна только при одной выделенной записи");
                return;
            }
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/income/EditIncome.fxml"));
            Parent root = loader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            EditIncome editMemberController = loader.getController();
            editMemberController.setEditedIncome(selectedIncomes.get(0));

            stage.showAndWait();

            updateTableContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteSelectedIncomes() {
        ArrayList<Income> selectedIncomes = getSelectedIncomes();
        int selectedNumber = selectedIncomes.size();

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
            for(Income income : selectedIncomes) {
                String query = "income?command=delete&id=" + income.getId();
                Client.doRequest(query);
            }
            updateTableContent();
            deleteBtn.setText("Удалить");
        }
    }

    private ArrayList<Income> getSelectedIncomes() {
        ArrayList<Income> selectedIncomes = new ArrayList<>();
        ObservableList<Income> incomes = table.getItems();
        for(Income income: incomes) {
            if(income.getSelected().isSelected()) {
                selectedIncomes.add(income);
            }
        }
        return selectedIncomes;
    }

    private void updateTableContent() {
        ArrayList<Income> incomes = getIncomesFromDB();
        ApplicationContext.getInstance().setIncomes(incomes);
        table.setItems(FXCollections.observableArrayList(incomes));
        if(incomes.size() != 0) {
            table.setPlaceholder(new Label("Добавьте пользователей в аккаунт. Для этого нажмите кнопку 'Добавить'."));
        } else {
            table.setPlaceholder(new Label(""));
        }
        message.setText("");
    }

    @FXML
    private void selectAll() {
        ObservableList<Income> incomes = table.getItems();
        for(Income income: incomes) {
            income.getSelected().setSelected(selectAllCheckbox.isSelected());
        }
        table.setItems(incomes);
    }

    public static ArrayList<Income> getIncomesFromDB(){
        Long accountId = ApplicationContext.getInstance().getCurrentAccount().getId();
        String query = "income?command=getAllByMemberAccountId&memberAccountId=" + accountId;
        Map<String, String> params = Client.doRequest(query);
        ArrayList<Income> members = new ArrayList<>();
        if(params.get("status").equals("ok")) {
            if(params.containsKey("data")) {
                ArrayList<Map<String, String>> incomesParams = Client.getResponseArray(params.get("data"));

                for(Map<String, String> incomeParams : incomesParams){
                    Income income = Income.fromMap(incomeParams);
                    income.setSelected(new CheckBox());
                    members.add(income);
                }
            }
        }
        return members;
    }

    public void applyFilter(ActionEvent actionEvent) {
    }

    public void removeFilter(ActionEvent actionEvent) {
    }
}