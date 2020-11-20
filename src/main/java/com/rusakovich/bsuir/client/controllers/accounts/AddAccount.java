package com.rusakovich.bsuir.client.controllers.accounts;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.client.controllers.ApplicationPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Map;

public class AddAccount extends ApplicationPane {
    @FXML
    private Button addBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField repeatPasswordField;
    @FXML
    private CheckBox isAdmin;
    @FXML
    private Label error;

    @FXML
    private void add(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();
        String repeatedPassword = repeatPasswordField.getText();
        if(login.equals("")) {
            error.setText("Введите логин");
            return;
        }
        if(password.equals("")) {
            error.setText("Введите пароль");
            return;
        }
        if(repeatedPassword.equals("")) {
            error.setText("Повторите пароль");
            return;
        }
        if(!password.equals(repeatedPassword)) {
            error.setText("Пароли не совпадают");
            return;
        }
        addBtn.setText("Сохранение...");
        int roleId = isAdmin.isSelected() ? 1 : 0;
        String query = "account?command=register&login=" + login + "&password=" + password + "&roleId=" + roleId;
        Map<String, String> params = Client.doRequest(query);
        addBtn.setText("Добавить");
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
