package com.rusakovich.bsuir.client.controllers.settings;

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

public class ChangePassword {
    @FXML
    private Button editBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField oldPasswordField;
    @FXML
    private TextField newPasswordField;
    @FXML
    private TextField newPasswordRepeatField;
    @FXML
    private Label error;

    @FXML
    private void edit(ActionEvent event) {
        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String newPasswordRepeat = this.newPasswordRepeatField.getText();
        Account currentAccount = ApplicationContext.getInstance().getCurrentAccount();
        if(oldPassword.equals("")) {
            error.setText("Введите старый пароль");
            return;
        }
        if(newPassword.equals("")) {
            error.setText("Введите новый пароль");
            return;
        }
        if(newPasswordRepeat.equals("")) {
            error.setText("Повторите новый пароль");
            return;
        }
        if(!currentAccount.getPassword().equals(oldPassword)) {
            error.setText("Старый пароль введён неверно");
            return;
        }
        if(!newPassword.equals(newPasswordRepeat)) {
            error.setText("Пароли не совпадают");
            return;
        }
        editBtn.setText("Обновление...");
        String query = "account?command=update&login=" + currentAccount.getLogin() +
                "&password=" + newPassword +
                "&roleId=" + currentAccount.getRoleId() +
                "&id=" + currentAccount.getId();
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
