package com.rusakovich.bsuir.client.controllers.bankAccounts;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.server.entity.AccountMember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Map;

public class EditBankAccount {
    @FXML
    private Button editBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField nameField;
    @FXML
    private Label error;
    private AccountMember editedMember;

    public void setEditedMember(AccountMember editedMember) {
        this.editedMember = editedMember;
        nameField.setText(editedMember.getName());
    }

    @FXML
    private void edit(ActionEvent event) {
        String name = nameField.getText();
        if(name.equals("")) {
            error.setText("Введите имя");
            return;
        }
        editBtn.setText("Обновление...");
        Long accountId = ApplicationContext.getInstance().getCurrentAccount().getId();
        String query = "account_member?command=update&id=" + editedMember.getId() +"&name=" + name + "&accountId=" + accountId;
        Map<String, String> params = Client.doRequest(query);

        editBtn.setText("Обновить");
        if ("ok".equals(params.get("status"))) {
            cancel(null);
        } else {
            error.setText(params.get("error"));
        }

    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
