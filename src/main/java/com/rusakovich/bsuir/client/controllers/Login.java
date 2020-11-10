package com.rusakovich.bsuir.client.controllers;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.server.entity.Account;
import com.rusakovich.bsuir.server.entity.AccountRole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private TextField passwordField;
    @FXML
    private Label error;

    @FXML
    private void signIn(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();
        if(login.equals("")) {
            error.setText("Login is required");
            return;
        }
        if(password.equals("")) {
            error.setText("Password is required");
            return;
        }
        String query = "account?command=login&login=" + login + "&password=" + password;
        Map<String, String> params = Client.doRequest(query);
        if ("ok".equals(params.get("status"))) {
            Account account = new Account(null, params.get("login"), null, 0);
            ApplicationContext.getInstance().setCurrentAccount(account);
            Client.openScene("../views/Home.fxml");
        } else {
            error.setText(params.get("error"));
        }
    }

    @FXML
    private void goToRegistration(ActionEvent event) {
        Client.openScene("../views/Registration.fxml");
    }
}
