package com.rusakovich.bsuir.client.controllers;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.server.entity.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Map;

public class Login {
    @FXML
    private Button signInBtn;
    @FXML
    private Button goToRegistrationBtn;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label error;

    @FXML
    private void signIn(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();
        if(login.equals("")) {
            error.setText("Введите логин");
            return;
        }
        if(password.equals("")) {
            error.setText("Введите пароль");
            return;
        }
        signInBtn.setText("Авторизация...");
        String query = "account?command=login&login=" + login + "&password=" + password;
        Map<String, String> params = Client.doRequest(query);
        if ("ok".equals(params.get("status"))) {
            Map<String, String> accountParams = Client.getResponseObject(params.get("data"));
            Account account = Account.fromMap(accountParams);
            ApplicationContext.getInstance().setCurrentAccount(account);
            Client.openScene("../views/Application.fxml");
        } else {
            error.setText(params.get("error"));
        }
        signInBtn.setText("Войти...");
    }

    @FXML
    private void goToRegistration(ActionEvent event) {
        Client.openScene("../views/Registration.fxml");
    }
}
