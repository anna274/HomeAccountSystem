package com.rusakovich.bsuir.client.controllers;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Application {
    @FXML
    private Button membersBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Pane mainPane;

    @FXML
    public void initialize() {
        switchMainPane("../views/Home.fxml");
    }

    @FXML
    private void logout(ActionEvent event) {
        ApplicationContext.clearContext();
        Client.openScene("../views/Login.fxml");
    }

    @FXML
    private void goToMembers(ActionEvent event) {
        switchMainPane("../views/members/Members.fxml");
    }

    private void switchMainPane(String url) {
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource(url));
            mainPane.getChildren().add(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
