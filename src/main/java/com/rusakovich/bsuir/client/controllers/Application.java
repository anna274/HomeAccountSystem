package com.rusakovich.bsuir.client.controllers;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Application {
    @FXML
    private Pane mainPane;
    @FXML
    private Pane menuPane;

    @FXML
    public void initialize() {
        Integer roleId = ApplicationContext.getInstance().getCurrentAccount().getRoleId();
        if(roleId == 0) {
            switchMenuPane("../views/menus/UserMenu.fxml");
        } else {
            switchMenuPane("../views/menus/AdminMenu.fxml");
        }
        switchMainPane("../views/Home.fxml");
    }

    public void switchMenuPane(String url) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            Pane menu = loader.load();
            menuPane.getChildren().add(menu);
            ApplicationPane paneController = loader.getController();
            paneController.setParentController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchMainPane(String url) {
        try {
            if(mainPane.getChildren().size() != 0) {
                mainPane.getChildren().remove(mainPane.getChildren().get(0));
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            Pane newPane = loader.load();
            mainPane.getChildren().add(newPane);
            ApplicationPane paneController = loader.getController();
            paneController.setParentController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        ApplicationContext.clearContext();
        Client.openScene("../views/Login.fxml");
    }
}
