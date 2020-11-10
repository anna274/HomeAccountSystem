package com.rusakovich.bsuir.client.controllers;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.client.views.FrameHandler;
import com.rusakovich.bsuir.server.entity.Account;
import com.rusakovich.bsuir.server.entity.AccountRole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private TextField passwordField;
    @FXML
    private TextField repeatPasswordField;
    @FXML
    private Label error;

    @FXML
    private void signUp(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();
        String repeatedPassword = repeatPasswordField.getText();
        if(login.equals("")) {
            error.setText("Login is required");
            return;
        }
        if(password.equals("")) {
            error.setText("Password is required");
            return;
        }
        if(repeatedPassword.equals("")) {
            error.setText("Please, repeat your password");
            return;
        }
        if(!password.equals(repeatedPassword)) {
            error.setText("Passwords should match");
            return;
        }
        String query = "account?command=register&login=" + login + "&password=" + password;
        Map<String, String> params = Client.doRequest(query);
        if ("ok".equals(params.get("status"))) {
            Account account = new Account(null, params.get("login"), null, 0);
            ApplicationContext.getInstance().setCurrentAccount(account);
            Client.openScene("../views/Home.fxml");
        }
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        Client.openScene("../views/Login.fxml");
    }
}