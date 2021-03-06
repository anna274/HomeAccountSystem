package com.rusakovich.bsuir.client.controllers;

import com.rusakovich.bsuir.client.app.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Map;

public class Registration {
    @FXML
    private Button signUpBtn;
    @FXML
    private Button goToLoginBtn;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private Label error;

    @FXML
    private void signUp(ActionEvent event) {
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
        signUpBtn.setText("Создание аккаунта...");
        String query = "account?command=register&login=" + login + "&password=" + password;
        Map<String, String> params = Client.doRequest(query);
        if ("ok".equals(params.get("status"))) {
            Client.openScene("../views/Login.fxml");
        } else {
            error.setText(params.get("error"));
        }
        signUpBtn.setText("Создать аккаунт");
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        Client.openScene("../views/Login.fxml");
    }
}
