package com.rusakovich.bsuir.client.controllers.bankAccounts;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.server.entity.AccountMember;
import com.rusakovich.bsuir.server.entity.BankAccount;
import com.rusakovich.bsuir.server.entity.Currency;
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

public class BankAccounts {

    @FXML
    private TableColumn<Long, String> idColumn;
    @FXML
    private TableColumn<BankAccount, String> selectionColumn;
    @FXML
    private TableColumn<BankAccount, String> nameColumn;
    @FXML
    private TableColumn<AccountMember, String> ownerColumn;
    @FXML
    private TableColumn<BankAccount, String> balanceColumn;
    @FXML
    private TableColumn<Currency, String> currencyColumn;
    @FXML
    private TableView<BankAccount> table;
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
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        selectionColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));

        updateTableContent();
    }

    @FXML
    public void addBankAccount() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/members/AddMember.fxml"));
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
    public void editBankAccount() {
//        try {
//            ArrayList<AccountMember> selectedMembers = getSelectedMembers();
//
//            if(selectedMembers.size() == 0){
//                message.setText("Для редактирования выделите запись из таблицы");
//                return;
//            }
//            if(selectedMembers.size() > 1){
//                message.setText("Функция 'Редактировать' активна только при одной выделенной записи");
//                return;
//            }
//            Stage stage = new Stage();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/members/EditMember.fxml"));
//            Parent root = loader.load();
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.setScene(new Scene(root));
//
//            com.rusakovich.bsuir.client.controllers.members.EditCurrency editCurrencyController = loader.getController();
//            editCurrencyController.setEditedMember(selectedMembers.get(0));
//
//            stage.showAndWait();
//
//            updateTableContent();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    public void deleteSelectedBankAccounts() {
//        ArrayList<AccountMember> selectedMembers = getSelectedMembers();
//        int selectedNumber = selectedMembers.size();
//
//        if(selectedNumber == 0) {
//            message.setText("Выберите записи для удаления");
//            return;
//        }
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("");
//        alert.setHeaderText("Удаление записей");
//        alert.setContentText("Вы уверены, что хотите удалить " +  selectedNumber + " записей?");
//
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.get() == ButtonType.OK){
//            deleteBtn.setText("Удаление...");
//            for(AccountMember member : selectedMembers) {
//                String query = "account_member?command=delete&id=" + member.getId();
//                Client.doRequest(query);
//            }
//            updateTableContent();
//            deleteBtn.setText("Удалить");
//        }
    }

    private ArrayList<BankAccount> getSelectedBankAccounts() {
        ArrayList<BankAccount> selectedBankAccounts = new ArrayList<>();
        ObservableList<BankAccount> bankAccounts = table.getItems();
        for(BankAccount bankAccount: bankAccounts) {
            if(bankAccount.getSelected().isSelected()) {
                selectedBankAccounts.add(bankAccount);
            }
        }
        return selectedBankAccounts;
    }

    private void updateTableContent() {
//        Long accountId = ApplicationContext.getInstance().getCurrentAccount().getId();
//        String query = "bank_account?command=getAllByAccountId&accountId=" + accountId;
//        Map<String, String> params = Client.doRequest(query);
//        if(params.get("status").equals("ok")) {
//            if(params.containsKey("data")) {
//                ArrayList<Map<String, String>> membersParams = Client.getResponseArray(params.get("data"));
//                ArrayList<BankAccount> bankAccounts = new ArrayList<>();
//
//                for(Map<String, String> memberParams : membersParams){
//                    BankAccount bankAccount = BankAccount.fromMap(memberParams);
//                    bankAccount.setSelected(new CheckBox());
//                    bankAccounts.add(bankAccount);
//                }
//                table.setItems(FXCollections.observableArrayList(bankAccounts));
//                ApplicationContext.getInstance().setBankAccounts(bankAccounts);
//            } else {
//                table.setPlaceholder(new Label("Добавьте счета. Для этого нажмите кнопку 'Добавить'."));
//            }
//        }
//        message.setText("");
    }

    @FXML
    private void selectAll() {
        ObservableList<BankAccount> bankAccounts = table.getItems();
        for(BankAccount bankAccount: bankAccounts) {
            bankAccount.getSelected().setSelected(selectAllCheckbox.isSelected());
        }
        table.setItems(bankAccounts);
    }
}
