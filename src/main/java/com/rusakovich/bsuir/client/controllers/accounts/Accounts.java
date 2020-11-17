package com.rusakovich.bsuir.client.controllers.accounts;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.server.entity.Account;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Accounts {

    @FXML
    private TableColumn<Account, String> idColumn;
    @FXML
    private TableColumn<Account, String> loginColumn;
    @FXML
    private TableColumn<Account, String> roleColumn;
    @FXML
    private TableColumn<Account, String> selectionColumn;
    @FXML
    private TableView<Account> table;
    @FXML
    private Label message;
    @FXML
    private Button deleteBtn;
    @FXML
    private CheckBox selectAllCheckbox;

    @FXML
    public void initialize() {

        table.setPlaceholder(new Label("Загрузка ..."));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        selectionColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        roleColumn.setCellValueFactory(cell -> new SimpleStringProperty(getRoleString(cell.getValue().getRoleId())));
        updateTableContent();
    }

    private String getRoleString(Integer roleId) {
        return roleId == 0 ? "Семейный аккаунт": "Администратор";
    }

    @FXML
    public void addAccount() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/accounts/AddAccount.fxml"));
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
    public void editAccount() {
        try {
            ArrayList<Account> selectedAccounts = getSelectedAccounts();

            if(selectedAccounts.size() == 0){
                message.setText("Для редактирования выделите запись из таблицы");
                return;
            }
            if(selectedAccounts.size() > 1){
                message.setText("Функция 'Редактировать' активна только при одной выделенной записи");
                return;
            }
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/accounts/EditAccount.fxml"));
            Parent root = loader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            EditAccount editMemberController = loader.getController();
            editMemberController.setEditedAccount(selectedAccounts.get(0));

            stage.showAndWait();

            updateTableContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteSelectedAccounts() {
        ArrayList<Account> selectedAccounts = getSelectedAccounts();
        int selectedNumber = selectedAccounts.size();

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
            for(Account account : selectedAccounts) {
                String query = "account?command=delete&id=" + account.getId();
                Client.doRequest(query);
            }
            updateTableContent();
            deleteBtn.setText("Удалить");
        }
    }

    private ArrayList<Account> getSelectedAccounts() {
        ArrayList<Account> selectedAccounts = new ArrayList<>();
        ObservableList<Account> accounts = table.getItems();
        for(Account account: accounts) {
            if(account.getSelected().isSelected()) {
                selectedAccounts.add(account);
            }
        }
        return selectedAccounts;
    }

    private void updateTableContent() {
        Long accountId = ApplicationContext.getInstance().getCurrentAccount().getId();
        String query = "account?command=getAll";
        Map<String, String> params = Client.doRequest(query);

        if(params.get("status").equals("ok")) {
            if(params.containsKey("data")) {
                ArrayList<Map<String, String>> accountsParams = Client.getResponseArray(params.get("data"));
                ArrayList<Account> accounts = new ArrayList<>();

                for(Map<String, String> accountParams : accountsParams){
                    Account account = Account.fromMap(accountParams);
                    account.setSelected(new CheckBox());
                    if(!account.getId().equals(accountId)) {
                        accounts.add(account);
                    }
                }
                table.setItems(FXCollections.observableArrayList(accounts));
                ApplicationContext.getInstance().setAccountsList(accounts);
            } else {
                table.setPlaceholder(new Label("Добавьте аккаунт. Для этого нажмите кнопку 'Добавить'."));
            }
        }
        message.setText("");
    }

    @FXML
    private void selectAll() {
        ObservableList<Account> accounts = table.getItems();
        for(Account account: accounts) {
            account.getSelected().setSelected(selectAllCheckbox.isSelected());
        }
        table.setItems(accounts);
    }
}
