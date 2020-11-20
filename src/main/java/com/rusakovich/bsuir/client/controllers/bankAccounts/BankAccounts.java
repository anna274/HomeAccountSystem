package com.rusakovich.bsuir.client.controllers.bankAccounts;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.client.controllers.ApplicationPane;
import com.rusakovich.bsuir.client.controllers.currencies.Currencies;
import com.rusakovich.bsuir.client.controllers.members.Members;
import com.rusakovich.bsuir.server.entity.BankAccount;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class BankAccounts extends ApplicationPane {

    @FXML
    private TableColumn<Long, String> idColumn;
    @FXML
    private TableColumn<BankAccount, String> selectionColumn;
    @FXML
    private TableColumn<BankAccount, String> nameColumn;
    @FXML
    private TableColumn<BankAccount, String> ownerColumn;
    @FXML
    private TableColumn<BankAccount, String> balanceColumn;
    @FXML
    private TableColumn<BankAccount, String> currencyColumn;
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
        ApplicationContext store = ApplicationContext.getInstance();

        if(store.getCurrencies() == null) {
            store.setCurrencies(Currencies.getCurrenciesFromDB());
        }

        if(store.getMembersList() == null) {
            store.setMembersList(Members.getMembersFromDB());
        }

        table.setPlaceholder(new Label("Загрузка ..."));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        selectionColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));

        currencyColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                    store.findCurrencyById(cell.getValue().getCurrencyId()).getName()
                )
        );
        ownerColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        store.findMemberById(cell.getValue().getMemberId()).getName()
                )
        );

        updateTableContent();
    }

    private void showMembersModal() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle("");
        alert.setHeaderText("Нет информации о членах семьи. Добаление счетов заблокировано");
        alert.setContentText("Счёт должен быть привязан к члену семьи, но записей о членах семьи нет. Для устранения этой проблемы, перейдите во вкладку 'Участники' " +
                "и добавьте информацию о членах семьи. После вы сможете добавить банковский счёт");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            parentController.switchMainPane("../views/members/Members.fxml");
        }
    }

    @FXML
    public void addBankAccount() {
        if(ApplicationContext.getInstance().getMembersList().size() ==0) {
            showMembersModal();
            return;
        }
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/bankAccounts/AddBankAccount.fxml"));
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
        try {
            ArrayList<BankAccount> selectedBankAccounts = getSelectedBankAccounts();

            if(selectedBankAccounts.size() == 0){
                message.setText("Для редактирования выделите запись из таблицы");
                return;
            }
            if(selectedBankAccounts.size() > 1){
                message.setText("Функция 'Редактировать' активна только при одной выделенной записи");
                return;
            }

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/bankAccounts/EditBankAccount.fxml"));
            Parent root = loader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            EditBankAccount editBankAccountController = loader.getController();
            editBankAccountController.setEditedBankAccount(selectedBankAccounts.get(0));
            stage.showAndWait();

            updateTableContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
//        ArrayList<BankAccount> bankAccounts = getBankAccountsFromDB();
//        ApplicationContext.getInstance().setBankAccounts(bankAccounts);
        ArrayList<BankAccount> bankAccounts = ApplicationContext.getInstance().getBankAccounts();
        for(BankAccount ba: bankAccounts) {
            ba.setSelected(new CheckBox());
        }
        if(bankAccounts.size() != 0) {
            table.setItems(FXCollections.observableArrayList(bankAccounts));
        } else {
            table.setPlaceholder(new Label("Добавьте информацию о счетах. Для этого нажмите кнопку 'Добавить'."));
        }
        message.setText("");
    }

    public static ArrayList<BankAccount> getBankAccountsFromDB(){
        Long accountId = ApplicationContext.getInstance().getCurrentAccount().getId();
        String query = "bank_account?command=getAllByAccountId&accountId=" + accountId;
        Map<String, String> params = Client.doRequest(query);
        ArrayList<BankAccount> bankAccounts = new ArrayList<>();

        if(params.get("status").equals("ok")) {
            if(params.containsKey("data")) {
                ArrayList<Map<String, String>> bankAccountsParams = Client.getResponseArray(params.get("data"));

                for(Map<String, String> bankAccountParams : bankAccountsParams){
                    BankAccount bankAccount = BankAccount.fromMap(bankAccountParams);
                    bankAccount.setSelected(new CheckBox());
                    bankAccounts.add(bankAccount);
                }
            }
        }
        return bankAccounts;
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
