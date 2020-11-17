package com.rusakovich.bsuir.client.controllers.accounts;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.server.entity.Account;
import com.rusakovich.bsuir.server.entity.AccountMember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Map;

public class EditAccount {
    @FXML
    private Button editBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField loginField;
    @FXML
    private CheckBox isAdmin;
    @FXML
    private Label error;
    private Account editedAccount;

    public void setEditedAccount(Account editedAccount) {
        this.editedAccount = editedAccount;
        loginField.setText(editedAccount.getLogin());
        isAdmin.setSelected(editedAccount.getRoleId() == 1);
    }

    @FXML
    private void edit(ActionEvent event) {
        String login = loginField.getText();
        if(login.equals("")) {
            error.setText("Введите логин");
            return;
        }
        int roleId = isAdmin.isSelected() ? 1 : 0;
        editBtn.setText("Обновление...");
        String query = "account?command=update" +
                "&id=" + editedAccount.getId() +
                "&login=" + login +
                "&password=" + editedAccount.getPassword() +
                "&roleId=" + roleId;
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
